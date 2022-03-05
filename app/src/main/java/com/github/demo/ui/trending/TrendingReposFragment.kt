package com.github.demo.ui.trending

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.demo.R
import com.github.demo.base.BaseFragment
import com.github.demo.base.Result
import com.github.demo.extensions.makeException
import com.github.demo.model.Repo
import com.github.demo.viewmodel.DataViewModel
import kotlinx.android.synthetic.main.fragment_trending_repos.*

class TrendingReposFragment : BaseFragment() {

    override fun getContentLayoutResId(): Int = R.layout.fragment_trending_repos

    override fun populateUI(rootView: View, savedInstanceState: Bundle?) {
        val dataViewModel = ViewModelProvider(this)[DataViewModel::class.java]


        val adData = RepositoryItemAdapter()
        val manager = LinearLayoutManager(mBaseContext)
        rvRepos.apply {
            layoutManager = manager
            adapter = adData
        }

        if (dataViewModel.mSaveData.isEmpty()) {
            dataViewModel.getTrendingRepositories("language", dataViewModel.pageIndex, 50).apply {
                pbData.visibility = View.VISIBLE
            }
        }else {
            adData.addData(dataViewModel.mSaveData)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adData.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adData.filter.filter(newText)
                return false
            }
        })

        rvRepos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (searchView.query.isNullOrEmpty()) {
                    if (manager.findLastCompletelyVisibleItemPosition() == adData.itemCount - 1) {
                        dataViewModel.getTrendingRepositories("language", dataViewModel.pageIndex, 50).apply {
                            pbDataBottom.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })

        dataViewModel.mData.observe(this) {
            pbData.visibility = View.GONE
            pbDataBottom.visibility = View.GONE
            when (it) {
                is Result.Success -> {
                    dataViewModel.pageIndex = it.data.nextPage ?: 1
                    it.data.items.let { list ->
                        if (list.isNotEmpty()) {
                            adData.addData(list as ArrayList<Repo>)
                            dataViewModel.mSaveData.addAll(list)
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