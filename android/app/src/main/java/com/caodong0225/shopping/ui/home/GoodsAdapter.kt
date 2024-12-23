package com.caodong0225.shopping.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caodong0225.shopping.databinding.GoodsItemBinding
import com.caodong0225.shopping.model.GoodsInfo
import com.squareup.picasso.Picasso
import io.noties.markwon.Markwon

class GoodsAdapter(
    private val goodsList: List<GoodsInfo>,
    private val totalAmountView: TextView // 直接传递总金额的 TextView
) : RecyclerView.Adapter<GoodsAdapter.GoodsViewHolder>() {

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

    // 用于存储用户选择的商品数量
    private val selectedGoods = mutableMapOf<Int, Int>()
    private var goodsPrice = 0.0 // 商品总价格

    fun getSelectedGoods(): Map<Int, Int> = selectedGoods

    inner class GoodsViewHolder(private val binding: GoodsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(goods: GoodsInfo) {
            binding.goodsTitle.text = goods.goods.name
            binding.goodsText.text = goods.goods.price.toString()
            binding.goodsTitle.contentDescription = goods.goods.id.toString()
            val description = "**单价**: ${goods.goods.price} 元  \n**剩余**：${goods.number}  \n**已售**：${goods.sold}"
            // 使用 Markwon 渲染 Markdown 格式的文本
            val markwon = Markwon.create(binding.root.context)
            markwon.setMarkdown(binding.goodsText, description)  // 假设你的商品有 `description` 字段
            // 使用 Picasso 加载网络图片
            Picasso.get()
                .load(goods.goods.logo) // 图片 URL
                .into(binding.goodsImage) // 加载到 ImageView
            // Bind other properties to your views (like goods description, image, etc.)
            // 初始化商品数量
            var goodsNum = 0 // 假设你有一个 `number` 表示数量

            // 显示初始值
            binding.goodsNum.text = goodsNum.toString()
            updateGoodsNumVisibility(goodsNum)

            // 增加数量按钮
            binding.goodsAdd.setOnClickListener {
                if (goodsNum < goods.number) {
                    goodsNum++
                    goodsPrice += goods.goods.price
                    selectedGoods[goods.goods.id] = goodsNum
                    binding.goodsNum.text = goodsNum.toString()
                    updateGoodsNumVisibility(goodsNum)
                }
            }

            // 减少数量按钮
            binding.goodsMinus.setOnClickListener {
                if (goodsNum > 0) {
                    goodsNum--
                    goodsPrice -= goods.goods.price
                    if (goodsNum == 0) {
                        selectedGoods.remove(goods.goods.id)
                    } else {
                        selectedGoods[goods.goods.id] = goodsNum
                    }
                    updateGoodsNumVisibility(goodsNum)
                }
            }
        }
        private fun updateGoodsNumVisibility(goodsNum: Int) {
            // 如果数量为 0，隐藏数量显示；否则显示
            binding.goodsNum.text = if (goodsNum == 0) "" else goodsNum.toString()
            // 保留两位小数
            totalAmountView.text = String.format("%.2f", goodsPrice)
        }
    }

}