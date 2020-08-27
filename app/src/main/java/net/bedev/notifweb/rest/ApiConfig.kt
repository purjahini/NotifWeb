package net.bedev.notifweb.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    fun getApiService(): ApiService {
        val retrofit = Retrofit.Builder()
//            .baseUrl("https://posyanduver1.000webhostapp.com/backend-generator/api/")
            .baseUrl("http://posyandu.bimar.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)

    }
}