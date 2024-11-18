package com.caodong0225.shopping.api

import com.caodong0225.shopping.model.ApiResponse
import com.caodong0225.shopping.model.CreateOrderRequest
import com.caodong0225.shopping.model.OrderInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface OrderService {
    @POST("order/create")
    suspend fun createOrder(
        @Header("Authorization") token: String,
        @Body orderInfo: List<CreateOrderRequest>
    ): ApiResponse<String>

    @GET("order/list")
    suspend fun getOrderList(
        @Header("Authorization") token: String
    ): ApiResponse<List<OrderInfo>>
}