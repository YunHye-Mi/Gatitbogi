package com.example.gatitbogi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gatitbogi.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding

    //private var restaurantDatas = ArrayList<Restarant>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        onClickListener()

        //val restaurantRVAdapter =

        return binding.root
    }

    fun onClickListener(){
        binding.searchIv.setOnClickListener {
            val intent = Intent(this.context, SearchActivity::class.java)
            startActivity(intent)
        }
    }
}