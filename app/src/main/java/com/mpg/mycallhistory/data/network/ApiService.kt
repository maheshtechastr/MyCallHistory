package com.mpg.mycallhistory.data.network

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET

interface ApiService {
    @GET("todos/")
    suspend fun getSampleData(): List<SampleData>
}

data class SampleData(
    val id: Int,
    @SerializedName("title")
    val name: String,
    val value: String
)
