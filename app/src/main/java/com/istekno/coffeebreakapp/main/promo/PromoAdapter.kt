package com.istekno.coffeebreakapp.main.promo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.databinding.ItemPromoBinding
import com.istekno.coffeebreakapp.main.maincontent.homepage.HomeResponse

class PromoAdapter(): RecyclerView.Adapter<PromoAdapter.ListViewHolder>() {

    companion object {
        const val img = "http://184.72.105.243:3000/images/"
    }

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listFavorite = mutableListOf<HomeResponse.DataProduct>()

    fun setData(list: List<HomeResponse.DataProduct>) {
        listFavorite.clear()
        listFavorite.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnItemClicked(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(productModel: HomeResponse.DataProduct)
    }

    inner class ListViewHolder(val binding: ItemPromoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(productModel: HomeResponse.DataProduct) {
            binding.model = productModel

            Glide.with(itemView.context).load(img + productModel.productImage)
                .placeholder(R.drawable.img_placeholder_product).into(binding.ivProduct)

            binding.root.setOnClickListener { onItemClickCallback.onItemClicked(listFavorite[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_promo, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFavorite[position])
        val item = listFavorite[position]
        val promoPrice =  item.productPrice.toInt() - (item.productPrice.toInt() * 0.1)
        holder.binding.tvPromoPrice.text = promoPrice.toInt().toString()
    }

    override fun getItemCount(): Int = listFavorite.size
}