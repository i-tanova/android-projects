package com.example.test.networking

import com.example.test.UserDetails
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface API {

    @GET("/users/{id}")
    suspend fun userDetails(@Path("id") id: Int): UserDetails

    @POST
    suspend fun createUserDetails(@Path("name") name: String)
}