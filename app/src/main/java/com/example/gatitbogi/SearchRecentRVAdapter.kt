package com.example.gatitbogi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gatitbogi.databinding.ItemRecentSearchBinding

class SearchRecentRVAdapter(private val searchList: ArrayList<Search>): RecyclerView.Adapter<SearchRecentRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(search: Search)
        fun onRemoveSearch(position: Int)
    }

    private lateinit var myItemClickListner: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListner = itemClickListener
    }

    fun addSearch(search: Search){
        searchList.add(search)
        notifyDataSetChanged()
    }

    fun removeSearch(position: Int){
        searchList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: ItemRecentSearchBinding = ItemRecentSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(searchList[position])
        holder.binding.itemRecentTv.setOnClickListener { myItemClickListner.onItemClick(searchList[position]) }
        holder.binding.itemRecentDeleteIv.setOnClickListener { myItemClickListner.onRemoveSearch(position) }
    }

    override fun getItemCount(): Int = searchList.size

    inner class ViewHolder(val binding: ItemRecentSearchBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(search: Search){
            binding.itemRecentTv.text = search.terms
        }
    }
}