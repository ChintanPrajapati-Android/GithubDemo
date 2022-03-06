package com.github.demo.ui.trending

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.demo.R
import com.github.demo.base.BaseFragment
import com.github.demo.base.Result
import com.github.demo.extensions.addFragment
import com.github.demo.extensions.makeException
import com.github.demo.extra.Constants
import com.github.demo.extra.FragmentTag
import com.github.demo.ui.trending.adapter.RepositoryItemAdapter
import com.github.demo.viewmodel.DataViewModel
import kotlinx.android.synthetic.main.fragment_trending_repos.*
import java.text.SimpleDateFormat
import java.util.*

class TrendingReposFragment : BaseFragment() {

    private var dataViewModel: DataViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataViewModel = activity?.let { ViewModelProvider(it) }!![DataViewModel::class.java]
        dataViewModel?.item?.let {
            Log.e("fragment", "fragment")
            val bundle = Bundle()
            bundle.putSerializable(Constants.DATA_ITEM, it)
            mBaseContext.addFragment(
                FragmentTag.TAG_DETAIL.toString(),
                DetailFragment(),
                bundle
            )
        }
    }

    override fun getContentLayoutResId(): Int = R.layout.fragment_trending_repos

    @SuppressLint("SimpleDateFormat")
    override fun populateUI(rootView: View, savedInstanceState: Bundle?) {

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)

        val query =
            "created:>".plus(SimpleDateFormat("yyyy-MM-dd").format(calendar.timeInMillis))

        val adData = RepositoryItemAdapter()
        val manager = LinearLayoutManager(mBaseContext)
        rvRepos.apply {
            layoutManager = manager
            adapter = adData
        }

        adData.onItemClick = { item ->
            dataViewModel?.item = item
            val bundle = Bundle()
            bundle.putSerializable(Constants.DATA_ITEM, item)
            mBaseContext.addFragment(FragmentTag.TAG_DETAIL.toString(), DetailFragment(), bundle)
        }

        if (dataViewModel?.htSaveData?.isEmpty == true) {
            dataViewModel?.getTrendingRepositories(query, dataViewModel?.pageIndex ?: 1, 50)
                .apply {
                    pbData.visibility = View.VISIBLE
                }
        } else {
            dataViewModel?.htSaveData?.let { adData.addData(it) }
            dataViewModel?.mListState?.let {
                manager.onRestoreInstanceState(it)
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                dataViewModel?.mListState = manager.onSaveInstanceState()
                dataViewModel?.search = query
                adData.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                dataViewModel?.mListState = manager.onSaveInstanceState()
                dataViewModel?.search = newText
                adData.filter.filter(newText)
                return false
            }
        })
        dataViewModel?.search?.let {
            searchView.setQuery(it, true)
        }



        rvRepos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                dataViewModel?.mListState = manager.onSaveInstanceState()
                if (searchView.query.isNullOrEmpty()) {
                    if (manager.findLastVisibleItemPosition() == adData.itemCount - 1) {
                        dataViewModel?.getTrendingRepositories(
                            query,
                            dataViewModel?.pageIndex ?: 1,
                            50
                        ).apply {
                            pbDataBottom.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })

        dataViewModel?.mData?.observe(this) {
            pbData.visibility = View.GONE
            pbDataBottom.visibility = View.GONE
            when (it) {
                is Result.Success -> {
                    dataViewModel?.pageIndex = it.data.nextPage ?: 1
                    it.data.items.let { list ->
                        if (list.isNotEmpty()) {
                            list.forEach { repo ->
                                dataViewModel?.htSaveData?.set(repo.id, repo)
                            }
                            dataViewModel?.htSaveData?.let { it1 -> adData.addData(it1) }
                        }
                    }
                }
                is Result.Error -> {
                    mBaseContext.makeException(it.code, it.message, mainView)
                }
            }
        }
    }
}