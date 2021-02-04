package com.istekno.coffeebreakapp.main.maincontent.order.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.databinding.ItemDetailOrderBinding
import com.istekno.coffeebreakapp.databinding.ItemPaymentBinding
import com.istekno.coffeebreakapp.main.detailproduct.DetailProductActivity
import com.istekno.coffeebreakapp.main.maincontent.order.OrderResponse
import com.istekno.coffeebreakapp.main.maincontent.orderhistory.OrderHistoryResponse

class DetailOrderRecyclerViewAdapter(private val listOrder: ArrayList<OrderResponse.Product>) :
    RecyclerView.Adapter<DetailOrderRecyclerViewAdapter.DetailOrderHistoryHolder>() {

    fun addList(list: List<OrderResponse.Product>) {
        listOrder.clear()
        listOrder.addAll(list)
        notifyDataSetChanged()
    }


    class DetailOrderHistoryHolder(val binding: ItemDetailOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = listOrder.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailOrderHistoryHolder {
        return DetailOrderHistoryHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_detail_order,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailOrderHistoryHolder, position: Int) {
        val item = listOrder[position]

        holder.binding.tvProductName.text = item.productName
        holder.binding.tvAmountProduct.text = item.orderAmount
        holder.binding.tvProductPrice.text = item.orderPrice.toString()

        Glide.with(holder.binding.root).load(DetailProductActivity.img + item.productImage)
            .placeholder(R.drawable.error)
            .error(R.drawable.error).into(holder.binding.ivProduct)

    }
}