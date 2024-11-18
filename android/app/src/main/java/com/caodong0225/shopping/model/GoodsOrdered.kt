package com.caodong0225.shopping.model

data class GoodsOrdered (
    /** 主键ID */
    val id: Int,

    /** 商品名称 */
    val goodsName: String,

    /** 商品单价 */
    val price: Double,

    /** 购买数量 */
    val number: Int,

    /** 总金额 */
    val totalPrice: Double
)