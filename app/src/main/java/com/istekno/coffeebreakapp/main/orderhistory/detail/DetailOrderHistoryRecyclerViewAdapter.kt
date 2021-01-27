package com.istekno.coffeebreakapp.main.orderhistory.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.databinding.ItemPaymentBinding

class DetailOrderHistoryRecyclerViewAdapter(private val listOrder: ArrayList<DetailOrderHistoryModel>):
    RecyclerView.Adapter<DetailOrderHistoryRecyclerViewAdapter.DetailOrderHistoryHolder>() {

    fun addList(list: List<DetailOrderHistoryModel>) {
        listOrder.clear()
        listOrder.addAll(list)
        notifyDataSetChanged()
    }


    class DetailOrderHistoryHolder(val binding: ItemPaymentBinding): RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = listOrder.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailOrderHistoryHolder {
        return DetailOrderHistoryHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_payment, parent, false))
    }

    override fun onBindViewHolder(holder: DetailOrderHistoryHolder, position: Int) {
        val item = listOrder[position]

        holder.binding.tvProductName.text = item.productName
        holder.binding.tvAmountProduct.text = item.orderAmount.toString()
        holder.binding.tvPriceProduct.text = item.orderPrice.toString()

    }
}