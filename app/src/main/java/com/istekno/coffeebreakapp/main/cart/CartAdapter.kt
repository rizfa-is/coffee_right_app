package com.istekno.coffeebreakapp.main.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.istekno.coffeebreakapp.R
import com.istekno.coffeebreakapp.databinding.ItemCartBinding
import java.text.DecimalFormat

class CartAdapter(private var listCart : ArrayList<CartResponse.DataCart>) : RecyclerView.Adapter<CartAdapter.ListCartViewHolder>() {

    companion object {
        const val img = "http://184.72.105.243:3000/images/"
    }

    private lateinit var onPlusItemCartClickCallback: OnPlusItemCartClickCallBack
    private lateinit var onMinusItemCartClickCallback : OnMinusCartClickCallBack
//    private var listCart = mutableListOf<CartResponse.DataCart>()

    fun setData(list: List<CartResponse.DataCart>) {
        listCart.clear()
        listCart.addAll(list)
        notifyDataSetChanged()
    }

    fun plusItemCartClicked(onItemCartClickCallback: OnPlusItemCartClickCallBack) {
        this.onPlusItemCartClickCallback = onItemCartClickCallback
    }

    fun minusItemCartClicked(onMinusItemCartClickCallback: OnMinusCartClickCallBack) {
        this.onMinusItemCartClickCallback = onMinusItemCartClickCallback
    }

    interface OnPlusItemCartClickCallBack {
        fun onPlusItemCartClicked(cartModel: CartResponse.DataCart)
    }

    interface OnMinusCartClickCallBack {
        fun minusItemCartClicked(cartModel: CartResponse.DataCart)
    }

    inner class ListCartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartModel: CartResponse.DataCart) {
            val formatter = DecimalFormat("#,###")

            binding.model = cartModel
            binding.tvProductPrice.text = formatter.format(cartModel.orderPrice.toDouble())

            Glide.with(itemView.context)
                .load(img + cartModel.productImage)
                .placeholder(R.drawable.img_placeholder_product)
                .into(binding.ivProduct)

            binding.viewPlus.setOnClickListener {
                onPlusItemCartClickCallback.onPlusItemCartClicked(listCart[adapterPosition])
                notifyItemChanged(adapterPosition)

            }
            binding.viewMinus.setOnClickListener {
                onMinusItemCartClickCallback.minusItemCartClicked((listCart[adapterPosition]))
                notifyItemChanged(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCartViewHolder {
        return ListCartViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_cart,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListCartViewHolder, position: Int) {
        holder.bind(listCart[position])
    }

    override fun getItemCount(): Int = listCart.size

}