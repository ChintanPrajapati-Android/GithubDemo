package com.github.demo.base

import com.github.demo.data.datamanager.DataManager

sealed class Result<out T : Any> {
    class Success<out T : Any>(val data: T, code : Int) : Result<T>()
    class Error(
        val exception: Exception,
        val message: String = exception.localizedMessage!!,
        val code: Int = DataManager.TRY_AGAIN
    ) :
        Result<Nothing>()
}