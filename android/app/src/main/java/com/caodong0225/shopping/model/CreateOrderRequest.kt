package com.caodong0225.shopping.model

data class CreateOrderRequest (
    /** 商品ID */
    val goodsId: Int,

    /** 商品数量 */
    val number: Int
)