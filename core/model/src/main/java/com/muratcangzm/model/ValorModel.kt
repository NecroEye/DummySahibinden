package com.muratcangzm.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ValorModel(
    @Expose
    @SerializedName("TipoVeiculo")
    val vehicleType: Int,
    @Expose
    @SerializedName("Valor")
    val value: String,
    @Expose
    @SerializedName("Marca")
    val brand: String,
    @Expose
    @SerializedName("Modelo")
    val model:String,
    @Expose
    @SerializedName("AnoModelo")
    val modelDate:Int,
    @Expose
    @SerializedName("Combustivel")
    val fuelType:String,
    @Expose
    @SerializedName("CodingoFipe")
    val fipeCode:String,
    @Expose
    @SerializedName("MesReferencia")
    val referenceName: String,
    @Expose
    @SerializedName("SiglaCombustivel")
    val fuelCharType:String
)
