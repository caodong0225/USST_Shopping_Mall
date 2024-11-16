package com.caodong0225.shopping.model

import java.io.Serializable

data class UsersRequest(
    // 用户名
    val username: String,

    // 密码
    val password: String
) : Serializable