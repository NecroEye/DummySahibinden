package com.muratcangzm.dummysahibinden.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratcangzm.dummysahibinden.repositories.FipeRepository
import com.muratcangzm.dummysahibinden.viewmodels.core.BaseViewModel
import com.muratcangzm.model.ModelosModel
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
class ExtendedViewModel
@Inject
constructor(private val repository: FipeRepository) : BaseViewModel() {

    // Fetching vehicle according to its brand code
    private var mutableBrandCodeData = MutableStateFlow<ModelosModel?>(null)
    val brandCodeData: StateFlow<ModelosModel?> get() = mutableBrandCodeData.asStateFlow()

    //brand code method
    fun fetchVehicleByBrandCode(type: VehicleType, codigo: Int) {

        mutableBrandCodeData.fetchDataFromResponse {
            repository.getCarModelsByBrandCode(type, codigo)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}