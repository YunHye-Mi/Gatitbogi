package com.example.gatitbogi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gatitbogi.databinding.ItemRecruitBinding

class UploadRecruitRVAdapter(private val recruitList: ArrayList<Recruit>): RecyclerView.Adapter<UploadRecruitRVAdapter.ViewHolder>(){
    interface MyItemClickListener{
        fun onItemClick(recruit: Recruit)
    }

    private lateinit var myItemClickListner: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListner = itemClickListener
    }

    fun addRecruit(recruit: Recruit){
        recruitList.add(recruit)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadRecruitRVAdapter.ViewHolder {
        var binding: ItemRecruitBinding = ItemRecruitBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = recruitList.size

    override fun onBindViewHolder(holder: UploadRecruitRVAdapter.ViewHolder, position: Int) {
        holder.bind(recruitList[position])
        holder.binding.itemParticipateChatting.setOnClickListener { myItemClickListner.onItemClick(recruitList[position]) }
    }

    inner class ViewHolder(val binding: ItemRecruitBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(recruit: Recruit){
            binding.itemRestaurantTv.text = recruit.restaurant
            binding.itemRecruitTitleTv.text = recruit.title
            binding.itemDateTv.text = "날짜: " + recruit.date
            binding.itemTimeTv.text = "시간: " + recruit.time + " 희망"
            binding.itemPersonnelTv.text = "인원: " + recruit.person + "인"

            if(recruit.gender == 0 && recruit.other) {
                binding.itemGenderIv.setImageResource(R.drawable.ic_outline_man_24)
                binding.itemGenderTv.text = "남성만"
            }
            else if(recruit.gender == 1 && recruit.other){
                binding.itemGenderIv.setImageResource(R.drawable.ic_baseline_woman_24)
                binding.itemGenderTv.text = "여성만"
            }
            else {
                binding.itemGenderLl.visibility = View.GONE
            }

            binding.itemParticipateChatting.text = "채팅방 참여 " + recruit.person + "/" + recruit.participate
        }
    }
}
