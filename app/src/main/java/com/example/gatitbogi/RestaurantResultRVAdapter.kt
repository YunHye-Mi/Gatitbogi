package com.example.gatitbogi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gatitbogi.databinding.ItemRestaurantRecruitBinding

class RestaurantResultRVAdapter(private val restaurantList: ArrayList<Restaurant>): RecyclerView.Adapter<RestaurantResultRVAdapter.ViewHolder>(){
    interface MyItemClickListener{
        fun onItemClick(restaurant: Restaurant)
    }

    private lateinit var myItemClickListner: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListner = itemClickListener
    }

    fun addRecruit(restaurant: Restaurant){
        restaurantList.add(restaurant)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantResultRVAdapter.ViewHolder {
        var binding: ItemRestaurantRecruitBinding = ItemRestaurantRecruitBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = restaurantList.size

    override fun onBindViewHolder(holder: RestaurantResultRVAdapter.ViewHolder, position: Int) {
        holder.bind(restaurantList[position])
        holder.binding.itemRestaurantIv.setOnClickListener { myItemClickListner.onItemClick(restaurantList[position]) }
    }

    inner class ViewHolder(val binding: ItemRestaurantRecruitBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: Restaurant){
            binding.itemRestaurantNameTv.text = restaurant.name
            if(restaurant.rate == 0F){
                binding.itemRestaurantRateTv.visibility = View.GONE
                binding.itemRestaurantRateIv.visibility = View.GONE
            }
            else {
                binding.itemRestaurantRateIv.visibility = View.VISIBLE
                binding.itemRestaurantRateTv.visibility = View.VISIBLE
                binding.itemRestaurantRateTv.text = restaurant.rate.toString()
            }
            binding.itemDistanceTv.text = restaurant.distance.toString() + "M 이내"
            binding.itemSeatNumberTv.text = restaurant.seat.toString() + "인석"
            binding.itemRestaurantMenuTv.text = "대표메뉴: " + restaurant.menu
            binding.itemRestaurantOtherTv.text = "기타설명: " + restaurant.info
            binding.itemRestaurantIv.setImageResource(restaurant.restaurantImg)
            binding.itemRestaurantRecruitTv.text = restaurant.recruit.toString() + "건"
        }
    }
}
