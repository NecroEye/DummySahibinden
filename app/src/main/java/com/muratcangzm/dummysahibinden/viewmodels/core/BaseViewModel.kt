package com.muratcangzm.dummysahibinden.viewmodels.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratcangzm.network.DataResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

open class BaseViewModel : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, thorwable ->
        Timber.tag("BaseViewModel Error").d(thorwable.message.toString())
    }

    protected fun <T> MutableStateFlow<T>.fetchDataFromResponse(
        dataProvider: suspend () -> Flow<DataResponse<T>>
    ) {
        viewModelScope.launch(exceptionHandler) {
            try {

                dataProvider().collect { response ->
                    when (response.status) {
                        DataResponse.Status.SUCCESS -> this@fetchDataFromResponse.value =
                            response.data!!
                        DataResponse.Status.LOADING -> Timber.tag("BaseViewModel").d("Loading data")
                        DataResponse.Status.ERROR -> Timber.tag("BaseViewModel Error")
                            .e(response.message)
                    }
                }

            } catch (e: Exception) {
                Timber.tag("BaseViewModel Error").e(e)
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}
