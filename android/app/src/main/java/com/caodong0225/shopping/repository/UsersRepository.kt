package com.caodong0225.shopping.repository

import com.caodong0225.shopping.client.RetrofitClient
import com.caodong0225.shopping.model.ApiResponse
import com.caodong0225.shopping.model.UsersRegisterRequest
import com.caodong0225.shopping.model.UsersRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRepository {
    suspend fun fetchJwtToken(userInfo: UsersRequest): ApiResponse<String>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.usersService.usersLogin(userInfo)
                // 这里可以记录响应的信息
                response
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun registerUser(userInfo: UsersRegisterRequest): ApiResponse<String>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.usersService.usersRegister(userInfo)
                // 这里可以记录响应的信息
                response
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}