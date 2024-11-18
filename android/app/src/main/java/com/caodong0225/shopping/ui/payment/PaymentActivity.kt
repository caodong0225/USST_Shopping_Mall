package com.caodong0225.shopping.ui.payment

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.caodong0225.shopping.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 使用 ViewBinding 来绑定布局
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 获取传递过来的支付 URL
        val paymentUrl = intent.getStringExtra("PAYMENT_URL")

        // 判断 URL 是否为空并进行处理
        if (!paymentUrl.isNullOrEmpty()) {
            setupWebView(paymentUrl)
        } else {
            // 如果没有 URL，提示用户并结束活动
            finish()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(content: String) {
        // 获取绑定的 WebView
        val webView: WebView = binding.webView

        // 启用 JavaScript
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        // 设置 WebViewClient，保证加载页面时不会跳转到外部浏览器
        webView.webViewClient = WebViewClient()

        // 判断是 URL 还是 HTML 代码
        if (content.startsWith("http://") || content.startsWith("https://")) {
            // 如果是 URL，加载 URL
            webView.loadUrl(content)
        } else {
            // 如果是 HTML，加载 HTML 内容
            webView.loadDataWithBaseURL(
                null,
                content,
                "text/html",
                "UTF-8",
                null
            )
        }
    }
}