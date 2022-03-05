package com.github.demo.ui.trending

import android.os.Bundle
import com.github.demo.R
import com.github.demo.base.BaseActivity
import com.github.demo.base.BaseFragment
import com.github.demo.extra.FragmentTag

class TrendingActivity : BaseActivity(R.layout.activity_trending) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragment(FragmentTag.TAG_REPOSITORY.toString(), TrendingReposFragment())
    }


    fun replaceWithBackFragment(tag: String, fragment: BaseFragment, bundle: Bundle? = null) {
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                fragment,
                tag
            )
            .addToBackStack(null)
            .commit()
    }


    fun replaceFragment(tag: String, fragment: BaseFragment, bundle: Bundle? = null) {
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                fragment,
                tag
            )
            .commit()
    }

    fun addFragment(tag: String, fragment: BaseFragment, bundle: Bundle? = null) {
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .add(
                R.id.container,
                fragment,
                tag
            )
            .addToBackStack(null)
            .commit()
    }
}