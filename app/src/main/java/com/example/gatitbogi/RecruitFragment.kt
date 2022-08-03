package com.example.gatitbogi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gatitbogi.databinding.FragmentRecruitBinding

class RecruitFragment : Fragment() {

    lateinit var binding: FragmentRecruitBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecruitBinding.inflate(inflater, container, false)

        onClickListener()

        return binding.root
    }
    //데이터 없을 경우와 있을 경우 화면 달라지도록 구현
    //구해요에서 목록을 눌렀을 경우 채팅화면으로 넘어가도록 구현

    fun onClickListener(){
        binding.recruitNoneUploadRecruitLl.setOnClickListener {
            val intent = Intent(this.context, UploadRecruitActivity::class.java)
            startActivity(intent)
        }

        binding.recruitUploadTv.setOnClickListener {
            val intent = Intent(this.context, UploadRecruitActivity::class.java)
            startActivity(intent)
        }
    }
}