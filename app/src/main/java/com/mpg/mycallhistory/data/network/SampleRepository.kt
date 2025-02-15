package com.mpg.mycallhistory.data.network

class SampleRepository(private val apiService: ApiService) {

    suspend fun getSampleData(): List<SampleData> {
        return apiService.getSampleData()
    }
}
