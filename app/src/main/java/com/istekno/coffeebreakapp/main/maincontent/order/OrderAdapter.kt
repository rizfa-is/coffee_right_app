package com.istekno.coffeebreakapp.main.maincontent.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.databinding.ItemOrderBinding
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class OrderAdapter(
    private val listOrder: ArrayList<OrderResponse.Data>,
    private val onListOrderClickListener: OnListOrderClickListenerr
) : RecyclerView.Adapter<OrderAdapter.ListViewHolder>() {

    fun setData(list: List<OrderResponse.Data>) {
        listOrder.clear()
        listOrder.addAll(list)
        notifyDataSetChanged()
    }

    class ListViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_order,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = listOrder[position]
        val date = item.orderUpdated.split("T")[0]
        val totalPrice =
            NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(item.totalPrice.toDouble())
                .replace("Rp".toRegex(), "IDR ")

        holder.binding.tvTotalPrice.text = totalPrice
        holder.binding.tvUpdated.text = date

        holder.itemView.setOnClickListener {
            onListOrderClickListener.onOrderItemClicked(position)
        }
    }

    override fun getItemCount(): Int = listOrder.size

    interface OnListOrderClickListenerr {
        fun onOrderItemClicked(position: Int)
    }
}