package com.example.gatitbogi

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

        return binding.root
    }
}