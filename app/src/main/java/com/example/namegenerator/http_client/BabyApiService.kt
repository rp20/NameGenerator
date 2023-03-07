package com.example.namegenerator.http_client

import com.example.namegenerator.models.NamePopularity
import retrofit2.Call
import retrofit2.http.GET

interface BabyApiService {
    @GET(Constants.BABY_URL)
    fun fetchBabyInfo(): Call<List<NamePopularity>>
}