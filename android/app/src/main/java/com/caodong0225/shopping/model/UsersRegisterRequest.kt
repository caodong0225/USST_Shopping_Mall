package com.caodong0225.shopping.model

import java.io.Serializable

data class UsersRegisterRequest(
    // 用户名
    val username: String,

    // 昵称
    val nickname: String,

    // 密码
    val password: String
) : Serializable