package com.example.gatitbogi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gatitbogi.databinding.ActivitySearchResultBinding

class SearchResultActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchResultBackIv.setOnClickListener {
            finish()
        }

        binding.searchResultTv.setOnClickListener {
            var intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }
}