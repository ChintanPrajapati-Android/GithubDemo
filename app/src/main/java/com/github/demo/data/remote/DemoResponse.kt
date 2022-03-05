package com.github.demo.data.remote

import com.google.gson.annotations.SerializedName

data class AssociatedDrug(
    @SerializedName("dose")
    val dose: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("strength")
    val strength: String = "",
    var problems: String = ""
)


