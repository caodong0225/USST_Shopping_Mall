package com.caodong0225.shopping.ui.home

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

        // 确保在数据加载完成后再初始化 RecyclerView
        CoroutineScope(Dispatchers.IO).launch {
            val goodsList = goodsRepository.fetchGoodsList()
            if (goodsList != null) {
                // 确保在主线程更新 UI
                launch(Dispatchers.Main) {
                    goodsAdapter = GoodsAdapter(goodsList)
                    binding.recyclerView.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = goodsAdapter
                    }
                }
            }
        }

        // 设置支付按钮点击事件
        binding.payButton.setOnClickListener {
            createOrder()
        }

        return root
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
            Toast.makeText(context, response?.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}