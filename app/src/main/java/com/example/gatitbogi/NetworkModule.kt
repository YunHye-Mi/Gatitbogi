package com.example.gatitbogi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://node-express-env.eba-earmsfg2.ap-northeast-2.elasticbeanstalk.com/" // '/'를 포함해서 적으면 interface 부분에서는 제외하고 적어야 함.

fun getRetrofit(): Retrofit {
    val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    return retrofit
}