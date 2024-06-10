package com.muratcangzm.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AnosModel(
    @Expose
    @SerializedName("codigo")
    val code:String,
    @Expose
    @SerializedName("nome")
    val name:String,
)