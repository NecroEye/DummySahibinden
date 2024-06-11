package com.muratcangzm.network.service

import com.muratcangzm.Constants
import com.muratcangzm.model.AnosModel
import com.muratcangzm.model.CarMarcasModel
import com.muratcangzm.model.ModelosModel
import com.muratcangzm.model.ValorModel
import com.muratcangzm.network.DataResponse
import com.muratcangzm.network.mapper.VehicleType
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FipeAPI {

    @GET("${Constants.BASE_URL}{carros}/marcas")
    suspend fun getVehiclesByType(
        @Path("carros")
        type: VehicleType
    ): Response<List<CarMarcasModel>>


    @GET("${Constants.BASE_URL}{carros}/marcas/{marca}/modelos")
    suspend fun getCarModelsByBrandCode(
        @Path("carros")
        type: VehicleType,
        @Path("marca")
        marca: Int  // getVehicleByType'in codigosu
    ): Response<ModelosModel>


    @GET("${Constants.BASE_URL}{carros}/marcas/{marca}/modelos/{codigo}/anos")
    suspend fun getVehicleYearInformation(
        @Path("carros")
        type: VehicleType,
        @Path("marca")
        marca: Int,  // getVehicleByType'in codigo'su
        @Path("codigo")
        codigo: Int // getCarModelsByBrandCode'un codigo'su
    ): Response<List<AnosModel>>


    @GET("${Constants.BASE_URL}{carros}/marcas/{marca}/modelos/{codigo}/anos/{date}")
    suspend fun getDetailsOfVehicle(
        @Path("carros")
        type: VehicleType,
        @Path("marca")
        marca: Int, // getVehicleByType'in codigo'su
        @Path("codigo")
        codigo: Int,  // getCarModelsByBrandCode'un codigo'su
        @Path("date")
        date: String   // getVehicleYearInformation'ın codigo'su
    ): Response<ValorModel>


}