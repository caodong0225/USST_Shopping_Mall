package com.caodong0225.shopping.model

import java.time.LocalDateTime

data class OrderInfo (
    val id: Int,
    val orderNo: String,
    val status: Int,
    val totalPrice: Double,
    val createTime: LocalDateTime,
    val orderDetailInfoVOList: List<GoodsOrdered>
)