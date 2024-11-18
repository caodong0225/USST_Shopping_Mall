package com.caodong0225.shopping.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class OrderInfo (
    val id: Int,
    val orderNo: String,
    val status: Int,
    val totalPrice: Double,
    val createTime: String, // 接受原始字符串
    val orderDetailInfoVOList: List<GoodsOrdered>
)