package com.example.gatitbogi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gatitbogi.databinding.ItemRestaurantResultBinding

class SearchResultRVAdapter(private val restaurantList: ArrayList<Restaurant>): RecyclerView.Adapter<SearchResultRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(restaurant: Restaurant)
    }

    private lateinit var myItemClickListner: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListner = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: ItemRestaurantResultBinding = ItemRestaurantResultBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(restaurantList[position])
        holder.binding.itemRestaurantIv.setOnClickListener { myItemClickListner.onItemClick(restaurantList[position]) }
    }

    override fun getItemCount(): Int = restaurantList.size

    inner class ViewHolder(val binding: ItemRestaurantResultBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: Restaurant){
            binding.itemRestaurantNameTv.text = restaurant.name
        }
    }
}