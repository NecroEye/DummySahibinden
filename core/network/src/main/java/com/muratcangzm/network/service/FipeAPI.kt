package com.muratcangzm.network.service

import com.muratcangzm.Constants
import com.muratcangzm.model.CarMarcasModel
import com.muratcangzm.model.ModelosModel
import com.muratcangzm.network.DataResponse
import com.muratcangzm.network.mapper.VehicleType
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FipeAPI {


    @GET("${Constants.BASE_URL}/{carros}/marcas")
    suspend fun getVehiclesByType(
        @Path("carros")
        type:VehicleType
    ): Response<List<CarMarcasModel>>



    @GET("${Constants.BASE_URL}/carros/marcas/{marca}/modelos")
    suspend fun getCarModelsByBrandCode(
        @Path("marcas")
        marca:Int
    ) : Response<ModelosModel>

}