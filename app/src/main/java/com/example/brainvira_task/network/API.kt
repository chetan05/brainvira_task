package com.example.brainvira_task.network


import com.example.brainvira_task.Util.API_CONNECT_TIMEOUT_IN_SECONDS
import com.example.brainvira_task.Util.API_READ_TIMEOUT_IN_SECONDS
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object API {
     var BASE_URL = "https://api.exchangeratesapi.io/"

    lateinit var retrofit: Retrofit
    lateinit var apiServices: APIService

    init {
        initRetrofit()
    }


    private fun initRetrofit() {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClint = OkHttpClient.Builder()

        httpClint.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
            request.addHeader("Accept", "application/json")
            val requestBuilder = request.build()
            chain.proceed(requestBuilder)
        }

        httpClint.connectTimeout(API_CONNECT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        httpClint.readTimeout(API_READ_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        httpClint.addInterceptor(logging)
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClint.build())
            .build()
        apiServices = retrofit.create(APIService::class.java)
    }

}