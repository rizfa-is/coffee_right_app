package com.istekno.coffeebreakapp.main.maincontent.homepage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.databinding.ItemHomeFavoriteBinding
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class HomeFavoriteAdapter: RecyclerView.Adapter<HomeFavoriteAdapter.ListViewHolder>() {

    companion object {
        const val img = "http://184.72.105.243:3000/images/"
    }

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listFavorite = mutableListOf<GetProductResponse.DataProduct>()

    fun setData(list: List<GetProductResponse.DataProduct>) {
        listFavorite.clear()
        listFavorite.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnItemClicked(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(productModel: GetProductResponse.DataProduct)
    }

    inner class ListViewHolder(val binding: ItemHomeFavoriteBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(productModel: GetProductResponse.DataProduct) {
            binding.model = productModel

            Glide.with(itemView.context).load(img + productModel.productImage)
                .placeholder(R.drawable.img_placeholder_product).into(binding.ivProduct)

            binding.root.setOnClickListener { onItemClickCallback.onItemClicked(listFavorite[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_home_favorite, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFavorite[position])
        val item = listFavorite[position]
        val formatter = DecimalFormat("#,###")
        val price = formatter.format(item.productPrice.toDouble())

        holder.binding.tvProductPrice.text = "IDR $price"
    }

    override fun getItemCount(): Int = listFavorite.size
}