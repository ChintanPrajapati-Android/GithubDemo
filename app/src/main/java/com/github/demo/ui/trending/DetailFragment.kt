package com.github.demo.ui.trending

import android.os.Bundle
import android.view.View
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.github.demo.R
import com.github.demo.base.BaseFragment
import com.github.demo.extensions.popBackStack
import com.github.demo.extra.Constants
import com.github.demo.extra.DateUtils
import com.github.demo.model.Repo
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : BaseFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(android.R.transition.slide_right)
        exitTransition = inflater.inflateTransition(android.R.transition.fade)
    }

    override fun getContentLayoutResId(): Int = R.layout.fragment_detail

    override fun populateUI(rootView: View, savedInstanceState: Bundle?) {

        toolbar.setNavigationOnClickListener {
            mBaseContext.popBackStack()
        }

        val item = arguments?.getSerializable(Constants.DATA_ITEM) as Repo?
        item?.let {
            Glide.with(this)
                .load(item.owner.avatar_url)
                .circleCrop()
                .error(android.R.drawable.stat_notify_error)
                .into(ivPicture)

            tvStar.text = it.stars.toString()
            tvFork.text = it.forks.toString()
            tvWatch.text = it.watchers.toString()
            tvName.text = item.owner.login.plus("( " + item.name).plus(" )")
            tvLanguage.text = item.language
            tvCreatedDate.text = DateUtils.formatDate(item.createDate)
            tvLastUpdateOn.text = DateUtils.formatDate(item.updateDate)
            tvDescription.text = item.description
            tvOpenIssues.text = item.openIssues.toString()
            tvLink.text = item.url
        }
    }
}