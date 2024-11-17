package com.caodong0225.shopping.ui.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caodong0225.shopping.databinding.GoodsItemBinding
import com.caodong0225.shopping.model.GoodsInfo

class GoodsAdapter(private val goodsList: List<GoodsInfo>) : RecyclerView.Adapter<GoodsAdapter.GoodsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        val binding = GoodsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GoodsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        val goods = goodsList[position]
        holder.bind(goods)
    }

    override fun getItemCount(): Int {
        return goodsList.size
    }

    inner class GoodsViewHolder(private val binding: GoodsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(goods: GoodsInfo) {
            binding.goodsTitle.text = goods.goods.name
            binding.goodsText.text = goods.goods.price.toString()
            binding.goodsImage.setImageURI(Uri.parse(goods.goods.logo))
            // Bind other properties to your views (like goods description, image, etc.)
        }
    }
}