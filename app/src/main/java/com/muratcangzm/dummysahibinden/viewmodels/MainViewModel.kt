package com.muratcangzm.dummysahibinden.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratcangzm.dummysahibinden.repositories.FipeRepository
import com.muratcangzm.model.CarMarcasModel
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

    private var mutableVehicleData = MutableStateFlow<List<CarMarcasModel>?>(emptyList())
    val vehicleData: StateFlow<List<CarMarcasModel>?> get() = mutableVehicleData.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("viewModel Error", throwable.message.toString())
    }

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