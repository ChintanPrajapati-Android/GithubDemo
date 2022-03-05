package com.github.demo.data.remote

import com.github.demo.base.Result
import com.github.demo.data.datamanager.DataManager
import com.google.gson.Gson
import retrofit2.Response
import java.net.UnknownHostException

class ApiResponseHandle<T : Any> {
    fun responseHttp(response: Response<T>): Result<T> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Result.Success(it, response.code())
            } ?: kotlin.run {
                return Result.Error(
                    IllegalStateException(response.message()),
                    response.message(),
                    code = response.code()
                )
            }
        } else {
            response.errorBody()?.let { errorBody ->
                val item = Gson().fromJson(errorBody.string(), ApiResponse::class.java)
                return if (item.code == 0) {
                    Result.Error(
                        IllegalStateException(response.message()),
                        item.message.ifEmpty { response.message() },
                        code = response.code()
                    )
                } else {
                    Result.Error(
                        IllegalStateException(item.message),
                        item.message,
                        code = item.code
                    )
                }
            } ?: kotlin.run {
                return Result.Error(IllegalStateException(response.message()))
            }
        }
    }

    fun internetServerError(e: Exception): Result<T>? {
        return if (e is UnknownHostException)
            Result.Error(IllegalStateException(""), "", code = DataManager.NO_INTERNET)
        else
            Result.Error(e)
    }
}