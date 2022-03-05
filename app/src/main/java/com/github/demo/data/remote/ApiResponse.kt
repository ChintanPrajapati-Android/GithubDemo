package com.github.demo.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiResponse<T> {

    @Expose
    @SerializedName(value = "code")
    var code: Int = 0

    @Expose
    @SerializedName(value = "message")
    var message: String = ""

    @Expose
    @SerializedName(value = "data")
    var data: T? = null

}