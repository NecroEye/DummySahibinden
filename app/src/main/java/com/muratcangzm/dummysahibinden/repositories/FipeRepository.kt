package com.muratcangzm.dummysahibinden.repositories

import android.util.Log
import com.muratcangzm.dummysahibinden.utils.IODispatcher
import com.muratcangzm.model.AnosModel
import com.muratcangzm.model.CarMarcasModel
import com.muratcangzm.model.ModelosModel
import com.muratcangzm.model.ValorModel
import com.muratcangzm.network.DataResponse
import com.muratcangzm.network.mapper.VehicleType
import com.muratcangzm.network.service.FipeAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FipeRepository
@Inject
constructor(
    private val fipeAPI: FipeAPI,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {


    suspend fun getVehicleListByType(type: VehicleType): Flow<DataResponse<List<CarMarcasModel>>> =
        flow {
            try {

                val response = fipeAPI.getVehiclesByType(type)

                if (response.isSuccessful)
                    emit(DataResponse.success(response.body())) ?: emit(DataResponse.error("No data found"))
                else
                    emit(DataResponse.error("An error occurred"))


            } catch (e: Exception) {
                emit(DataResponse.error(e.message.toString()))
            }

        }.flowOn(ioDispatcher)


    suspend fun getCarModelsByBrandCode(type: VehicleType ,code: Int): Flow<DataResponse<ModelosModel>> = flow {
        try {

            val response = fipeAPI.getCarModelsByBrandCode(type ,code)

            if (response.isSuccessful)
                emit(DataResponse.success(response.body())) ?: emit(DataResponse.error("No data found"))
            else
                emit(DataResponse.error("An error occurred"))


        } catch (e: Exception) {
            emit(DataResponse.error(e.message.toString()))
        }
    }.flowOn(ioDispatcher)


    suspend fun getVehicleYearInformation(type: VehicleType, code:Int, codigo:Int): Flow<DataResponse<List<AnosModel>>> = flow{

        try {

            val response = fipeAPI.getVehicleYearInformation(type = type, marca = code, codigo = codigo )

            if(response.isSuccessful)
             emit(DataResponse.success(response.body())) ?: emit(DataResponse.error("No data found"))
            else
                emit(DataResponse.error("An error occurred"))


        }catch (e:Exception){
            emit(DataResponse.error(e.message.toString()))
        }

    }.flowOn(ioDispatcher)


    suspend fun getVehicleDetails(type: VehicleType, code:Int, codigo:Int, date:String): Flow<DataResponse<ValorModel>> = flow {

        try{

            val response = fipeAPI.getDetailsOfVehicle(type, code, codigo, date)

            if(response.isSuccessful)
                emit(DataResponse.success(response.body())) ?: emit(DataResponse.error("No data found"))
            else
                emit(DataResponse.error("An error occurred"))
        }
        catch (e:Exception){
            emit(DataResponse.error(e.message.toString()))
        }

    }.flowOn(ioDispatcher)

}



