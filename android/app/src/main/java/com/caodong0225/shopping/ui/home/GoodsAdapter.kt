package com.caodong0225.shopping.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caodong0225.shopping.databinding.GoodsItemBinding
import com.caodong0225.shopping.model.GoodsInfo
import com.squareup.picasso.Picasso
import io.noties.markwon.Markwon

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
            val description = "**单价**: ${goods.goods.price} 元  \n**剩余**：${goods.number}  \n**已售**：${goods.sold}"
            // 使用 Markwon 渲染 Markdown 格式的文本
            val markwon = Markwon.create(binding.root.context)
            markwon.setMarkdown(binding.goodsText, description)  // 假设你的商品有 `description` 字段
            // 使用 Picasso 加载网络图片
            Picasso.get()
                .load(goods.goods.logo) // 图片 URL
                .into(binding.goodsImage) // 加载到 ImageView
            // Bind other properties to your views (like goods description, image, etc.)
        }
    }
}