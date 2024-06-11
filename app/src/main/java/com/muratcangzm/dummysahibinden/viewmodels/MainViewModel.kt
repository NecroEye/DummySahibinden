package com.muratcangzm.dummysahibinden.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratcangzm.dummysahibinden.repositories.FipeRepository
import com.muratcangzm.model.AnosModel
import com.muratcangzm.model.CarMarcasModel
import com.muratcangzm.model.ModelosModel
import com.muratcangzm.model.ValorModel
import com.muratcangzm.network.mapper.VehicleType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(private val repository: FipeRepository) : ViewModel() {

    //Fetching ordinary vehicle data
    private var mutableVehicleData = MutableStateFlow<List<CarMarcasModel>?>(emptyList())
    val vehicleData: StateFlow<List<CarMarcasModel>?> get() = mutableVehicleData.asStateFlow()


    // Fetching vehicle according to its brand code
    private var mutableBrandCodeData = MutableStateFlow<ModelosModel?>(null)
    val brandCodeData: StateFlow<ModelosModel?> get() = mutableBrandCodeData.asStateFlow()

    // Fetching vehicle acc0rding to its year
    private var mutableVehicleYearListData = MutableStateFlow<List<AnosModel>?>(emptyList())
    val vehicleYearListData: StateFlow<List<AnosModel>?> get() = mutableVehicleYearListData.asStateFlow()


    //Fetching vehicle all details
    private var mutableVehicleDetails = MutableStateFlow<ValorModel?>(null)
    val vehicleDetails: StateFlow<ValorModel?> get() = mutableVehicleDetails.asStateFlow()


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("viewModel Error", throwable.message.toString())
    }
    //Detail method - not working right now
    fun fetchVehicleDetails(type: VehicleType, marca:Int, codigo: Int, date:String){

        viewModelScope.launch(exceptionHandler) {
            repository.getVehicleDetails(type, marca, codigo, date).collect{ result ->

                mutableVehicleDetails.value = result.data

            }
        }
    }

    //year method
    fun fetchVehicleYearList(type: VehicleType, marca:Int, codigo:Int){

        viewModelScope.launch(exceptionHandler) {
            repository.getVehicleYearInformation(type, marca, codigo).collect{ result ->

                mutableVehicleYearListData.value = result.data

            }
        }
    }

    //brand code method
    fun fetchVehicleByBrandCode(type: VehicleType, codigo:Int){

        viewModelScope.launch(exceptionHandler) {
            repository.getCarModelsByBrandCode(type = type, code =  codigo).collect{ result ->

                mutableBrandCodeData.value = result.data

            }
        }
    }

    //ordinary method
    fun fetchVehicleByType(type: VehicleType) {

        viewModelScope.launch(exceptionHandler) {
            repository.getVehicleListByType(type).collect { result ->
                mutableVehicleData.value = result.data
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }


}