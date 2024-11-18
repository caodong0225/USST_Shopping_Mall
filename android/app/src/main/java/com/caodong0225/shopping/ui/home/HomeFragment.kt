package com.caodong0225.shopping.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.caodong0225.shopping.Settings
import com.caodong0225.shopping.databinding.FragmentHomeBinding
import com.caodong0225.shopping.model.CreateOrderRequest
import com.caodong0225.shopping.repository.GoodsRepository
import com.caodong0225.shopping.repository.OrderRepository
import com.caodong0225.shopping.ui.payment.PaymentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var goodsAdapter: GoodsAdapter
    private val goodsRepository = GoodsRepository()
    private val orderRepository = OrderRepository()
    private lateinit var settings: Settings

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // 初始化 Settings
        settings = Settings(requireContext())

        // 初始化 RecyclerView 和下拉刷新
        initRecyclerView()
        initSwipeRefresh()
        loadGoodsData()

        // 设置支付按钮点击事件
        binding.payButton.setOnClickListener {
            createOrder()
        }

        return root
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        goodsAdapter = GoodsAdapter(emptyList(), binding.totalAmount) // 初始化为空列表
        binding.recyclerView.adapter = goodsAdapter
    }

    private fun initSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            loadGoodsData() // 调用重新加载数据的逻辑
        }
    }

    private fun loadGoodsData() {
        CoroutineScope(Dispatchers.IO).launch {
            val goodsList = goodsRepository.fetchGoodsList()
            if (goodsList != null) {
                launch(Dispatchers.Main) {
                    goodsAdapter = GoodsAdapter(goodsList, binding.totalAmount)
                    binding.recyclerView.adapter = goodsAdapter
                    binding.swipeRefreshLayout.isRefreshing = false // 停止刷新动画
                }
            } else {
                launch(Dispatchers.Main) {
                    Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show()
                    binding.swipeRefreshLayout.isRefreshing = false // 停止刷新动画
                }
            }
        }
    }

    private fun createOrder() {
        val token = settings.token // 替换为实际的 token
        val selectedGoods = goodsAdapter.getSelectedGoods()
        if(selectedGoods.isEmpty()) {
            Toast.makeText(context, "请选择商品", Toast.LENGTH_SHORT).show()
            return
        }
        if (token.isNullOrEmpty()) {
            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show()
            return
        }
        // 构建订单请求数据
        val orderRequests = selectedGoods.map { (goodsId, goodsCount) ->
            CreateOrderRequest(goodsId, goodsCount)
        }
        CoroutineScope(Dispatchers.IO).launch {
            val response = orderRepository.createOrder(token, orderRequests)
            // 创建支付请求成功后跳转到支付页面
            launch(Dispatchers.Main) {
                if (response?.code == 200) {
                    // 假设支付页面需要 URL 和其他信息
                    val paymentUrl = response.data // 从响应中获取支付 URL

                    // 使用 Intent 跳转到支付页面
                    val intent = Intent(context, PaymentActivity::class.java).apply {
                        putExtra("PAYMENT_URL", paymentUrl) // 将支付 URL 传递给支付页面
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(context, response?.message ?: "创建订单失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}