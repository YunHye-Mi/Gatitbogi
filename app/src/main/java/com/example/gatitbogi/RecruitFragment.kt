package com.example.gatitbogi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gatitbogi.databinding.FragmentRecruitBinding
import com.example.gatitbogi.databinding.ItemRecruitBinding
import java.util.ArrayList

class RecruitFragment : Fragment() {

    lateinit var binding: FragmentRecruitBinding
    lateinit var recruitDB: RecruitDatabase

    var recruitDatas = ArrayList<Recruit>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecruitBinding.inflate(inflater, container, false)

        recruitDB = RecruitDatabase.getInstance(this.requireContext())!!

        onClickListener()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRV()
    }

    //구해요에서 목록을 눌렀을 경우 채팅화면으로 넘어가도록 구현
    // 이미 참여한 채팅방임을 알리는 문구 표시

    private fun initRV() {
        recruitDatas.addAll(recruitDB!!.recruitDao().getRecruit() as ArrayList<Recruit>)

        // 데이터가 있는 경우와 없는 경우
        if (recruitDatas.size == 0) {
            binding.recruitLine.visibility = View.GONE
            binding.recruitChattingRv.visibility = View.GONE
            binding.recruitNoneUploadRecruitLl.visibility = View.VISIBLE
            binding.recruitNoneTv.visibility = View.VISIBLE
        } else {
            binding.recruitLine.visibility = View.VISIBLE
            binding.recruitChattingRv.visibility = View.VISIBLE
            binding.recruitNoneUploadRecruitLl.visibility = View.GONE
            binding.recruitNoneTv.visibility = View.GONE
        }

        val recruitRVAdapter = UploadRecruitRVAdapter(recruitDatas)

        binding.recruitChattingRv.adapter = recruitRVAdapter
        binding.recruitChattingRv.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        recruitRVAdapter.setMyItemClickListener(object: UploadRecruitRVAdapter.MyItemClickListener {
            override fun onItemClick(recruit: Recruit) {
                recruitRVAdapter.ViewHolder(ItemRecruitBinding.inflate(layoutInflater)).binding.itemParticipateChatting.text = "이미 참여한 방입니다"
                if(recruit.person.toInt() > recruit.participate)
                    recruitDB.recruitDao().participate(++recruit.participate, recruit.title)
                Log.d("Participate", recruit.participate.toString())
            }
        })

    }

    fun onClickListener(){
        binding.recruitNoneUploadRecruitLl.setOnClickListener {
            val intent = Intent(this.context, UploadRecruitActivity::class.java)
            startActivity(intent)
            // (context as MainActivity).binding.mainBnv.selectedItemId = R.id.homeFragment
        }
    }
}