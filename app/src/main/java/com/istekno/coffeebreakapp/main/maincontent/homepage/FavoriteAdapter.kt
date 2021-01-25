package com.istekno.coffeebreakapp.main.maincontent.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.databinding.ItemFavoriteBinding

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listFavorite = mutableListOf<FavoriteModel>()

    fun setData(list: List<FavoriteModel>) {
        listFavorite.clear()
        listFavorite.addAll(list)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(favoriteModel: FavoriteModel)
    }

    inner class FavoriteViewHolder(val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteModel: FavoriteModel) {
            binding.model = favoriteModel

            Glide.with(itemView.context).load(favoriteModel.prImage)
                .placeholder(R.drawable.img_placeholder_product).into(binding.ivProduct)

            binding.root.setOnClickListener { onItemClickCallback.onItemClicked(listFavorite[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_favorite, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int = listFavorite.size
}