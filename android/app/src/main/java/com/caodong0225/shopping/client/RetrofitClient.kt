package com.caodong0225.shopping.client

import com.caodong0225.shopping.Settings
import com.caodong0225.shopping.api.UsersService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val instance: UsersService by lazy {
        Retrofit.Builder()
            .baseUrl(Settings.defaultUrl)
            .addConverterFactory(GsonConverterFactory.create()) // 用于解析 JSON
            .build()
            .create(UsersService::class.java)
    }

}