package com.caodong0225.shopping.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.caodong0225.shopping.Settings
import com.caodong0225.shopping.databinding.FragmentGalleryBinding
import com.caodong0225.shopping.repository.OrderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private lateinit var ordersAdapter: OrdersAdapter
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
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)
        // 初始化 Settings
        settings = Settings(requireContext())
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // 初始化 RecyclerView 和下拉刷新
        initRecyclerView()
        initSwipeRefresh()
        loadOrdersData()

        return root
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        ordersAdapter = OrdersAdapter(emptyList())
        binding.recyclerView.adapter = ordersAdapter
    }

    private fun initSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            loadOrdersData()
        }
    }

    private fun loadOrdersData() {
        if(settings.token == null) {
            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show()
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            val ordersList = orderRepository.getOrderList(settings.token!!)
            if (ordersList != null) {
                launch(Dispatchers.Main) {
                    ordersAdapter.updateOrders(ordersList)
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            } else {
                launch(Dispatchers.Main) {
                    Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show()
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}