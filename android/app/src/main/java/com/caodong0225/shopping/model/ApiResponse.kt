package com.caodong0225.shopping.model

// 定义一个泛型类来表示响应结构
data class ApiResponse<T>(
    val code: Int,
    val message: String,
    val data: T? // 泛型类型，允许为 null
)