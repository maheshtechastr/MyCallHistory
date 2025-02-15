package com.mpg.mycallhistory.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mpg.mycallhistory.data.network.SampleRepository
import com.mpg.mycallhistory.ui.home.HomeViewModel

class BaseViewModelFactory(private val repository: SampleRepository) : ViewModelProvider.Factory {

      override fun <T : ViewModel> create(modelClass: Class<T>): T {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
