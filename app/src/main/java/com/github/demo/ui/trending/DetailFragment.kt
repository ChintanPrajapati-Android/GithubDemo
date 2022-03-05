package com.github.demo.ui.trending

import android.os.Bundle
import android.view.View
import androidx.transition.TransitionInflater
import com.github.demo.R
import com.github.demo.base.BaseFragment

class DetailFragment : BaseFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(android.R.transition.slide_right)
        exitTransition = inflater.inflateTransition(android.R.transition.fade)
    }

    override fun getContentLayoutResId(): Int = R.layout.fragment_detail

    override fun populateUI(rootView: View, savedInstanceState: Bundle?) {
    }
}