package com.example.gatitbogi

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("api/auth/register")
    fun signUp(@Body user: User): Call<AuthResponse>

    @POST("api/auth/login")
    fun signIn(@Body user: User): Call<AuthResponse>

    @GET("api/users/me")
    fun getUser(@Body user: User)
}