package com.tanovait.sunnyapp.api

import com.tanovait.sunnyapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ComunityHeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
                .newBuilder()
                .addHeader("x-rapidapi-key", BuildConfig.APP_KEY)
                .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                .build()
        return chain.proceed(request)
    }
}

//class OpenWeatherHeaderInterceptor : Interceptor {
//    @Throws(IOException::class)
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request: Request = chain.request()
//                .newBuilder()
//                .addHeader("x-rapidapi-key", BuildConfig.APP_KEY)
//                .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
//                .build()
//        return chain.proceed(request)
//    }
//}