package com.github.demo.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(@LayoutRes val contentLayoutId: Int) :
    AppCompatActivity(contentLayoutId) {

    private lateinit var mBaseContext: AppCompatActivity
    private var mToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBaseContext = this
    }

    fun makeToast(message: String?) {
        message?.let {
            if (mToast != null)
                mToast?.cancel()

            mToast = Toast.makeText(mBaseContext, it, Toast.LENGTH_SHORT)
            mToast?.show()
        }
    }
}