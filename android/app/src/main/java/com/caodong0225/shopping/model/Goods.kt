package com.caodong0225.shopping.model

import java.io.Serializable

data class Goods (
    /** 主键ID */
    val id: Int,

    /** 商品名称 */
    val name: String,

    /** 商品状态 */
    val status: Int,

    /** 商品价格 */
    val price: Double,

    /** 商品图标 */
    val logo: String,

    /** 商品库存 */
    val stock: Int
) : Serializable