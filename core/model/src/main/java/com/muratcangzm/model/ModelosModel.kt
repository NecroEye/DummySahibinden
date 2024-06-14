package com.muratcangzm.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ModelosModel(
    @Expose
    @SerializedName("anos")
    val years: List<Years>,
    @Expose
    @SerializedName("modelos")
    val models: List<Models>
){

    data class Years(
        @Expose
        @SerializedName("codigo")
        val code:String,
        @Expose
        @SerializedName("nome")
        val name:String
     )

    data class Models(
        @Expose
        @SerializedName("codigo")
        val code:Int,
        @Expose
        @SerializedName("nome")
        val name:String,
    )

}
