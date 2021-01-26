package com.istekno.coffeebreakapp.main.orderhistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.databinding.ItemOrderHistoryBinding
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class OrderHistoryRecyclerViewAdapter(private val listOrderHistory: ArrayList<OrderHistoryModel>,
                                      private val onListOrderHistoryClickListener: OnListOrderHistoryClickListener)
    : RecyclerView.Adapter<OrderHistoryRecyclerViewAdapter.OrderHistoryHolder>() {

    fun addList(list: List<OrderHistoryModel>) {
        listOrderHistory.clear()
        listOrderHistory.addAll(list)
        notifyDataSetChanged()
    }

    class OrderHistoryHolder(val binding: ItemOrderHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = listOrderHistory.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryHolder {
        return OrderHistoryHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_order_history, parent, false))
    }

    override fun onBindViewHolder(holder: OrderHistoryHolder, position: Int) {
        val item = listOrderHistory[position]
        val date = item.orderUpdated!!.split("T")[0]
        val totalPrice = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(item.totalPrice?.toDouble()).replace("Rp".toRegex(),"IDR ")

        holder.binding.tvProductPrice.text = totalPrice
        holder.binding.tvDate.text = date

        holder.itemView.setOnClickListener {
            onListOrderHistoryClickListener.onOrderHistoryItemClicked(position)
        }

    }

    interface OnListOrderHistoryClickListener {
        fun onOrderHistoryItemClicked(position: Int)
    }
}