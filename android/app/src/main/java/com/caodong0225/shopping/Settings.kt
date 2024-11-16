package com.caodong0225.shopping

import android.content.Context
import android.content.SharedPreferences

internal class Settings(context: Context) {
    private val sharedPreferences: SharedPreferences
    companion object {
        // 全局静态属性
        var defaultUrl: String = "http://localhost:9527" // 换成本地运行的主机的IP地址
    }
    init {
        sharedPreferences = context.getSharedPreferences("shopping", Context.MODE_PRIVATE)
    }
    var token: String?
        get() = sharedPreferences.getString("token", null)
        set(value) = sharedPreferences.edit().putString("token", value).apply()

    var nickname: String?
        get() = sharedPreferences.getString("nickname", null)
        set(value) = sharedPreferences.edit().putString("nickname", value).apply()

    fun clear() {
        token = null
        nickname = null
    }
}