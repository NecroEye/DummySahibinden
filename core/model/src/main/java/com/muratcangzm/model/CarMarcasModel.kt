package com.muratcangzm.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CarMarcasModel(
    @Expose
    @SerializedName("codigo")
    val id: Int,
    @Expose
    @SerializedName("nome")
    val name: String,

)