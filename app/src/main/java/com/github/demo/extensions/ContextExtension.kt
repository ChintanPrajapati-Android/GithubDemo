package com.github.demo.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.github.demo.R
import com.github.demo.base.BaseActivity
import com.github.demo.base.BaseFragment
import com.github.demo.data.datamanager.DataManager
import com.github.demo.ui.trending.TrendingActivity
import com.google.android.material.snackbar.Snackbar


@SuppressLint("StaticFieldLeak")
var mainSnackView: View? = null
var mSnack: Snackbar? = null

fun Context.makeException(responseCode: Int, message: String?, view: View) {
    when (responseCode) {
        DataManager.SESSION_EXPIRE -> {
            makeSnake(message, view)
        }
        DataManager.TRY_AGAIN,
        DataManager.NOT_FOUND -> {
            makeSnake(message, view)
        }
        DataManager.INVALID_REQUEST -> {
            if (message.isNullOrEmpty()) {
                makeSnake(this.getString(R.string.invalid_request), view)
            } else {
                makeSnake(message, view)
            }
        }
        DataManager.NO_INTERNET -> {
            makeSnake(this.getString(R.string.offline), view, true)
        }
        else -> {
            makeSnake(this.getString(R.string.something_went_wrong), view)
        }
    }
}

fun Context.makeSnake(message: String?, view: View?, isOffline: Boolean = false) {
    try {
        mainSnackView = view
        view?.let {
            message?.let {
                if (mSnack != null) {
                    mSnack?.dismiss()
                }
                view.let { it1 ->
                    Snackbar.make(
                        it1,
                        it,
                        if (isOffline) Snackbar.LENGTH_LONG else Snackbar.LENGTH_LONG
                    )
                }.also { mSnack = it }
                val sbView: View? = mSnack?.view
                sbView?.setBackgroundResource(R.drawable.bg_snackbar)
                mSnack?.show()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


fun Context.hideKeyboard(view: View) {
    val imm: InputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun BaseActivity.openScreen(tag: String, fragment: BaseFragment, bundle : Bundle?= null) {
    (this as TrendingActivity).replaceWithBackFragment(tag, fragment, bundle)
}

fun BaseActivity.replaceFragment(tag: String, fragment: BaseFragment, bundle : Bundle?= null) {
    (this as TrendingActivity).replaceFragment(tag, fragment, bundle)
}

fun BaseActivity.addFragment(tag: String, fragment: BaseFragment, bundle : Bundle?= null) {
    (this as TrendingActivity).addFragment(tag, fragment, bundle)
}

fun BaseActivity.popBackStack() {
    (this as TrendingActivity).supportFragmentManager.popBackStack()
}


