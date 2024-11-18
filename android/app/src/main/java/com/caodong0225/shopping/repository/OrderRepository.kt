package com.caodong0225.shopping.repository

import com.caodong0225.shopping.client.RetrofitClient
import com.caodong0225.shopping.model.ApiResponse
import com.caodong0225.shopping.model.CreateOrderRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrderRepository {
    suspend fun createOrder(token: String, orderInfo: List<CreateOrderRequest>): ApiResponse<String>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.orderService.createOrder(token, orderInfo)
                // 这里可以记录响应的信息
                response
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}