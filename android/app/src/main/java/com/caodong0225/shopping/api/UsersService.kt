package com.caodong0225.shopping.api

import com.caodong0225.shopping.model.ApiResponse
import com.caodong0225.shopping.model.UsersRegisterRequest
import com.caodong0225.shopping.model.UsersRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface UsersService {
    @POST("user/login")
    suspend fun usersLogin(
        @Body userInfo: UsersRequest
    ): ApiResponse<String>

    @POST("user/register")
    suspend fun usersRegister(
        @Body userInfo: UsersRegisterRequest
    ): ApiResponse<String>
}