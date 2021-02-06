package com.istekno.coffeebreakapp.main.maincontent.orderhistory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.databinding.ItemOrderHistoryBinding
import com.istekno.coffeebreakapp.main.maincontent.order.OrderAdapter
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class OrderHistoryRecyclerViewAdapter(
    private val listOrderHistory: ArrayList<OrderHistoryResponse.Data>,
    private val onListOrderHistoryClickListener: OnListOrderHistoryClickListener,
    private val role: Int
) : RecyclerView.Adapter<OrderHistoryRecyclerViewAdapter.OrderHistoryHolder>() {

    fun addList(list: List<OrderHistoryResponse.Data>) {
        listOrderHistory.clear()
        listOrderHistory.addAll(list)
        notifyDataSetChanged()
    }

    class OrderHistoryHolder(val binding: ItemOrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = listOrderHistory.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryHolder {
        return OrderHistoryHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_order_history,
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderHistoryHolder, position: Int) {
        val item = listOrderHistory[position]
        val date = item.orderUpdated.split("T")[0]
        val formatter = DecimalFormat("#,###")
        val price = formatter.format(item.totalPrice.toDouble())

        holder.binding.tvProductName.text = item.productOrder[0].productName
        holder.binding.tvTotalPriceOrder.text = "IDR $price"
        holder.binding.tvUpdated.text = dateFormatter(date)
        holder.binding.tvStatus.text = listOrderHistory[position].orderDetailStatus

        if (role != 0) {
            holder.binding.tvTotalPriceOrder.text = item.customerName
        }

        if (item.productOrder.size > 1) {
            holder.binding.tvAmountAnotherProduct.text = "+ ${(item.productOrder.size) - 1}"
        } else {
            holder.binding.tvAmountAnotherProduct.text = ""
        }

        Glide.with(holder.binding.root).load(OrderAdapter.img + item.productOrder[0].productImage)
            .placeholder(R.drawable.ic_bag).into(holder.binding.ivProduct)

        holder.itemView.setOnClickListener {
            onListOrderHistoryClickListener.onOrderHistoryItemClicked(position)
        }

    }

    interface OnListOrderHistoryClickListener {
        fun onOrderHistoryItemClicked(position: Int)
    }

    @SuppressLint("SimpleDateFormat", "NewApi")
    private fun dateFormatter(date: String): String {
        val myDate = LocalDate.parse(date)
        val formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
        val dateFormatted = myDate.format(formatter)

        return dateFormatted.replace("-".toRegex(), " ")
    }
}