package com.caodong0225.shopping.model

data class GoodsInfo(
    val goods: Goods,

    val sold: Int
) {
    val number: Int = goods.stock - sold
}