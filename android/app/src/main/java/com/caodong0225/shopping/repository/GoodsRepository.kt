package com.caodong0225.shopping.repository

import com.caodong0225.shopping.client.RetrofitClient
import com.caodong0225.shopping.model.GoodsInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GoodsRepository {
    suspend fun fetchGoodsList(): List<GoodsInfo>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.goodsService.getGoodsList()
                // 这里可以记录响应的信息
                response.data
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}