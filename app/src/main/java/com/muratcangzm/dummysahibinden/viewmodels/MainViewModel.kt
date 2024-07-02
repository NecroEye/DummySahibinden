package com.muratcangzm.dummysahibinden.viewmodels

import com.muratcangzm.dummysahibinden.repositories.FipeRepository
import com.muratcangzm.dummysahibinden.viewmodels.core.BaseViewModel
import com.muratcangzm.model.AnosModel
import com.muratcangzm.model.CarMarcasModel
import com.muratcangzm.network.mapper.VehicleType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(private val repository: FipeRepository) : BaseViewModel() {

    //Fetching ordinary vehicle data
    private var mutableVehicleData = MutableStateFlow<List<CarMarcasModel>?>(emptyList())
    val vehicleData: StateFlow<List<CarMarcasModel>?> get() = mutableVehicleData.asStateFlow()

    // Fetching vehicle acc0rding to its year
    private var mutableVehicleYearListData = MutableStateFlow<List<AnosModel>?>(emptyList())
    val vehicleYearListData: StateFlow<List<AnosModel>?> get() = mutableVehicleYearListData.asStateFlow()


    //year method
    fun fetchVehicleYearList(type: VehicleType, marca: Int, codigo: Int) {

        mutableVehicleYearListData.fetchDataFromResponse {
            repository.getVehicleYearInformation(type, marca, codigo)
        }

    }

    //ordinary method
    fun fetchVehicleByType(type: VehicleType) {

        mutableVehicleData.fetchDataFromResponse {
            repository.getVehicleListByType(type)
        }

    }


}