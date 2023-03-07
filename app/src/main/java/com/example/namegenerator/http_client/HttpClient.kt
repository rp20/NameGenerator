package com.example.namegenerator.http_client

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpClient {
    private lateinit var apiService: BabyApiService

    fun getBabyApiService(): BabyApiService {

        // Initialize ApiService if not initialized yet
        if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            apiService = retrofit.create(BabyApiService::class.java)
        }

        return apiService
    }

}