package com.github.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.demo.base.BaseViewModel
import com.github.demo.base.Result
import com.github.demo.data.remote.ApiResponseHandle
import com.github.demo.model.Repo
import com.github.demo.model.RepositoryModel
import kotlinx.coroutines.launch


class DataViewModel : BaseViewModel() {
    var mData = MutableLiveData<Result<RepositoryModel>>()
    var mSaveData = ArrayList<Repo>()
    var pageIndex = 1
    fun getTrendingRepositories(query: String, nextPage: Int, itemCount: Int) {
        viewModelScope.launch {
            try {
                val response = getRemote().getTrendingRepositories(query, nextPage, itemCount)
                mData.value = ApiResponseHandle<RepositoryModel>().responseHttp(response)
            } catch (e: Exception) {
                mData.value = ApiResponseHandle<RepositoryModel>().internetServerError(e)
            }
        }
    }
}