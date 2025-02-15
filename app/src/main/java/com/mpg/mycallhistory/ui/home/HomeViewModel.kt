package com.mpg.mycallhistory.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.mpg.mycallhistory.data.network.SampleData
import com.mpg.mycallhistory.data.network.SampleRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: SampleRepository) : ViewModel() {

    //    private val _text = MutableLiveData<String>().apply {
//        value = "This is home Fragment"
//    }
//    private val _contacts = MutableLiveData<List<ListItem>>().apply {
//        value = List(20) { index ->
//            ListItem(name = "Sample Contact $index", image = "abc.png", phoneNumber = "123-456-789${index % 10}")
//        }
//    }
//    val text: LiveData<String> = _text
    private val _sampleData = MutableLiveData<List<SampleData>>()
    private val sampleData: LiveData<List<SampleData>> get() = _sampleData

    init {
        fetchSampleData()
    }

    val list: LiveData<List<ListItem>> = sampleData.map { item ->
        item.map {
            println("Name ==>"+it.name)
            println("Id ==>"+it.id)
            ListItem(name = it.name, image = "abc.png", phoneNumber = "123-456-789${it.id % 10}")
        }
    }

    private fun fetchSampleData() {
        viewModelScope.launch {
            try {
                val data = repository.getSampleData()
                _sampleData.value = data
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}