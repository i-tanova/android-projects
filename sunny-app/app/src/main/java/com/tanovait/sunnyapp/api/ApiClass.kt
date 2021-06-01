package com.tanovait.sunnyapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


internal object APIClient {

    val BASE_URL = "https://community-open-weather-map.p.rapidapi.com"
    val urlHourly = "http://api.openweathermap.org"
    ///data/2.5/forecast?q=Sofia&appid=a9f07b3e58aa83b04d6299075004c1ce

    val comunityClient: Retrofit?
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(ComunityHeaderInterceptor()).build()
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            return retrofit
        }

    val openMapClient: Retrofit?
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val retrofit = Retrofit.Builder()
                    .baseUrl(urlHourly)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            return retrofit
        }
}