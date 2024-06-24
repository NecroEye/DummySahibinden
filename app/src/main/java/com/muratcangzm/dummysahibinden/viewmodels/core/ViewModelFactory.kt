package com.muratcangzm.dummysahibinden.viewmodels.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.muratcangzm.dummysahibinden.repositories.FipeRepository
import com.muratcangzm.dummysahibinden.viewmodels.ExtendedViewModel
import com.muratcangzm.dummysahibinden.viewmodels.MainViewModel
import javax.inject.Inject

class ViewModelFactory @Inject
constructor(private val repository: FipeRepository)
    : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ExtendedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExtendedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}