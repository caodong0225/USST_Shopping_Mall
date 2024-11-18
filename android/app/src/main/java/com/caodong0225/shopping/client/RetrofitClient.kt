package com.caodong0225.shopping.client

import com.caodong0225.shopping.Settings
import com.caodong0225.shopping.api.UsersService
import com.caodong0225.shopping.api.GoodsService
import com.caodong0225.shopping.api.OrderService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val usersService: UsersService by lazy {
        Retrofit.Builder()
            .baseUrl(Settings.defaultUrl)
            .addConverterFactory(GsonConverterFactory.create()) // 用于解析 JSON
            .build()
            .create(UsersService::class.java)
    }

    val goodsService: GoodsService by lazy {
        Retrofit.Builder()
            .baseUrl(Settings.defaultUrl)
            .addConverterFactory(GsonConverterFactory.create()) // 用于解析 JSON
            .build()
            .create(GoodsService::class.java)
    }

    val orderService: OrderService by lazy {
        Retrofit.Builder()
            .baseUrl(Settings.defaultUrl)
            .addConverterFactory(GsonConverterFactory.create()) // 用于解析 JSON
            .build()
            .create(OrderService::class.java)
    }
}