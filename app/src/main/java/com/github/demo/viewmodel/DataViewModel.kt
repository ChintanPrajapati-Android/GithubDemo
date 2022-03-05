package com.github.demo.viewmodel

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.demo.base.BaseViewModel
import com.github.demo.base.Result
import com.github.demo.data.remote.ApiResponseHandle
import com.github.demo.model.Repo
import com.github.demo.model.RepositoryModel
import kotlinx.coroutines.launch
import java.util.*


class DataViewModel : BaseViewModel() {
    var mListState: Parcelable? = null
    var mData = MutableLiveData<Result<RepositoryModel>>()
    var htSaveData = Hashtable<Long, Repo>()
    var pageIndex = 1
    var search: String? = null
    var item : Repo ?= null
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