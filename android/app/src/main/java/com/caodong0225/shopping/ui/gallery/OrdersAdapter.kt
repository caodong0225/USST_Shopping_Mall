package com.caodong0225.shopping.ui.gallery

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.caodong0225.shopping.R
import com.caodong0225.shopping.databinding.OrdersItemBinding
import com.caodong0225.shopping.model.OrderInfo
import io.noties.markwon.Markwon
import java.time.format.DateTimeFormatter

class OrdersAdapter(
    private var ordersList: List<OrderInfo>,
    private val orderClickListener: OnOrderClickListener
) : RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {

    interface OnOrderClickListener {
        fun onOrderClick(order: OrderInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val binding = OrdersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrdersViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val order = ordersList[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateOrders(newOrders: List<OrderInfo>) {
        ordersList = newOrders
        notifyDataSetChanged()
    }

    inner class OrdersViewHolder(private val binding: OrdersItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        fun bind(order: OrderInfo) {
            // 设置点击事件
            binding.root.setOnClickListener {
                orderClickListener.onOrderClick(order)
            }
            val orderTitle = StringBuilder()
            orderTitle.append("订单号：${order.orderNo}  ")
            when (order.status) {
                1 -> orderTitle.append("（点我付款）")
                2 -> orderTitle.append("（待接单）")
                3 -> orderTitle.append("（已接单）")
                4 -> orderTitle.append("（派送中）")
                5 -> orderTitle.append("（已完成）")
                6 -> orderTitle.append("（已取消）")
                7 -> orderTitle.append("（已退款）")
            }
            // 绑定订单流水号
            binding.ordersNo.text = orderTitle.toString()
            binding.ordersImage.setImageResource(R.drawable.item)

            // 绑定订单总消费金额
            binding.ordersNum.text = "总消费：${order.totalPrice} 元"

            // 使用 Markdown 格式生成订单详情
            val orderDescription = StringBuilder()
            orderDescription.append("**订单详情**\n\n")
            order.orderDetailInfoVOList.forEachIndexed { _, goodsOrdered ->
                orderDescription.append("**${goodsOrdered.goodsName}**\n")
                orderDescription.append("- 单价：${goodsOrdered.price} 元\n")
                orderDescription.append("- 数量：${goodsOrdered.number}\n")
                orderDescription.append("- 小计：${goodsOrdered.totalPrice} 元\n\n")
            }
            orderDescription.append("\n**订单创建时间**：")
            val formattedTime = order.createTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            orderDescription.append("${formattedTime}\n\n")

            // 使用 Markwon 渲染 Markdown 格式的订单详情
            val markwon = Markwon.create(binding.root.context)
            markwon.setMarkdown(binding.ordersText, orderDescription.toString().trim())
        }
    }
}