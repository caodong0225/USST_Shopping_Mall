package com.caodong0225.shopping.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.caodong0225.shopping.databinding.FragmentHomeBinding
import com.caodong0225.shopping.repository.GoodsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var goodsAdapter: GoodsAdapter
    private val goodsRepository = GoodsRepository()

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

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}