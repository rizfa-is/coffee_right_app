package com.istekno.coffeebreakapp.main.maincontent.order

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.Placeholder
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.databinding.ItemOrderBinding
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class OrderAdapter(
    private val listOrder: ArrayList<OrderResponse.Data>,
    private val onListOrderClickListener: OnListOrderClickListenerr
) : RecyclerView.Adapter<OrderAdapter.ListViewHolder>() {

    companion object {
        const val img = "http://184.72.105.243:3000/images/"
    }

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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = listOrder[position]
        val date = item.orderUpdated.split("T")[0]
        val formatter = DecimalFormat("#,###")
        val price = formatter.format(item.totalPrice.toDouble())

        holder.binding.tvProductName.text = item.productOrder[0].productName
        holder.binding.tvTotalPriceOrder.text = "IDR $price"
        holder.binding.tvUpdated.text = dateFormatter(date)
        holder.binding.tvStatus.text = listOrder[position].orderDetailStatus

        if (item.productOrder.size > 1) {
            holder.binding.tvAmountAnotherProduct.text = "+ ${(item.productOrder.size) - 1}"
        } else {
            holder.binding.tvAmountAnotherProduct.text = ""
        }

        Glide.with(holder.binding.root).load(img + item.productOrder[0].productImage)
            .placeholder(R.drawable.ic_bag).into(holder.binding.ivProduct)

        holder.itemView.setOnClickListener {
            onListOrderClickListener.onOrderItemClicked(position)
        }
    }

    override fun getItemCount(): Int = listOrder.size

    interface OnListOrderClickListenerr {
        fun onOrderItemClicked(position: Int)
    }

    @SuppressLint("SimpleDateFormat", "NewApi")
    private fun dateFormatter(date: String): String {
        val myDate = LocalDate.parse(date)
        val formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
        val dateFormatted = myDate.format(formatter)

        return dateFormatted.replace("-".toRegex(), " ")
    }
}