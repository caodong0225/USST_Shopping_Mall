package com.caodong0225.shopping.api

import com.caodong0225.shopping.model.ApiResponse
import com.caodong0225.shopping.model.GoodsInfo
import retrofit2.http.GET

interface GoodsService {
    @GET("goods/list")
    suspend fun getGoodsList(): ApiResponse<List<GoodsInfo>>
}