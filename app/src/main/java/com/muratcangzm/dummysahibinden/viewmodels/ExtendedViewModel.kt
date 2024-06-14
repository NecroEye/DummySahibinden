package com.muratcangzm.dummysahibinden.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratcangzm.dummysahibinden.repositories.FipeRepository
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
constructor(private val repository: FipeRepository): ViewModel() {


    // Fetching vehicle according to its brand code
    private var mutableBrandCodeData = MutableStateFlow<ModelosModel?>(null)
    val brandCodeData: StateFlow<ModelosModel?> get() = mutableBrandCodeData.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.message
    }


    //brand code method
    fun fetchVehicleByBrandCode(type: VehicleType, codigo:Int){

        viewModelScope.launch(exceptionHandler) {
            repository.getCarModelsByBrandCode(type = type, code =  codigo).collect{ result ->

                mutableBrandCodeData.value = result.data

            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}