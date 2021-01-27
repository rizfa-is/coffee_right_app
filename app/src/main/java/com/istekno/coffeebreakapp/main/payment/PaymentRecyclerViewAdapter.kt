package com.istekno.coffeebreakapp.main.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.databinding.ItemPaymentBinding
import com.istekno.coffeebreakapp.main.orderhistory.detail.DetailOrderHistoryRecyclerViewAdapter

class PaymentRecyclerViewAdapter(private val listCart: ArrayList<PaymentModel>): RecyclerView.Adapter<PaymentRecyclerViewAdapter.ListCartHolder>() {

    fun addList(list: List<PaymentModel>) {
        listCart.clear()
        listCart.addAll(list)
        notifyDataSetChanged()
    }

    class ListCartHolder(val binding: ItemPaymentBinding): RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = listCart.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCartHolder {
        return ListCartHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_payment,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListCartHolder, position: Int) {
        val item = listCart[position]

        holder.binding.tvProductName.text = item.productName
        holder.binding.tvAmountProduct.text = item.amount.toString()
        holder.binding.tvPriceProduct.text = item.price.toString()
    }
}