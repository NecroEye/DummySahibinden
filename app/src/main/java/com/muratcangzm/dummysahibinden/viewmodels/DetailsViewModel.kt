package com.muratcangzm.dummysahibinden.viewmodels

import com.muratcangzm.dummysahibinden.repositories.FipeRepository
import com.muratcangzm.dummysahibinden.viewmodels.core.BaseViewModel
import com.muratcangzm.model.ValorModel
import com.muratcangzm.network.mapper.VehicleType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel
@Inject
constructor(private val repository: FipeRepository) : BaseViewModel() {


    //Fetching vehicle all details
    private var mutableVehicleDetails = MutableStateFlow<ValorModel?>(null)
    val vehicleDetails: StateFlow<ValorModel?> get() = mutableVehicleDetails.asStateFlow()


    fun fetchVehicleDetails(type: VehicleType, marca: Int, codigo: Int, date: String) {

        mutableVehicleDetails.fetchDataFromResponse {
            repository.getVehicleDetails(type, marca, codigo, date)
        }
    }

}