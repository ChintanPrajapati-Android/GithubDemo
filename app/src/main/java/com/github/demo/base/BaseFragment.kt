package com.github.demo.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    lateinit var mBaseContext: BaseActivity

    @LayoutRes
    protected abstract fun getContentLayoutResId(): Int

    protected abstract fun populateUI(rootView: View, savedInstanceState: Bundle?)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mBaseContext = context as BaseActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getContentLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateUI(view, savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> mBaseContext.supportFragmentManager.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun setTitle(title: String) {
        mBaseContext.supportActionBar?.title = title
    }
}