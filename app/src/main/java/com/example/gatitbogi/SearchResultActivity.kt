package com.example.gatitbogi

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gatitbogi.databinding.ActivitySearchResultBinding
import kotlin.collections.ArrayList


class SearchResultActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchResultBinding
    lateinit var resultDB: RecruitDatabase

    private var resultDatas = ArrayList<Restaurant>()

    val array = arrayOf("인기순", "거리순")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent = getIntent()

        resultDatas.apply{
            add(
                Restaurant(R.drawable.ic_alarm_home,"풍성집","한식", 4.06F,
                    "숙성한우 등심(국내산.전남)180g, 칼로써는 차돌박이(국내산)180g,...", "...", 500, 24, 2)
            )

            add(
                Restaurant(R.drawable.ic_alarm_home,"도야집","한식", 4.76F,
                    "도야집 시그니처 세트, 목살, 뼈오겹살, 삼겹살, 가브리껍살,...", "...", 500, 28, 3)
            )
        }

        resultDB = RecruitDatabase.getInstance(this)!!


        binding.searchResultBackIv.setOnClickListener {
            finish()
        }

        binding.searchResultTv.setOnClickListener {
            finish()

            var search = Intent(this, SearchActivity::class.java)
            startActivity(search)
        }

        binding.searchResultAlignLl.setOnClickListener {
            pickAlign(binding.searchResultAlignWayTv, this)
        }

        binding.searchResultAlignLl.setOnClickListener {
            pickAlign(binding.searchResultAlignWayTv, this)
        }
    }

    override fun onStart() {
        super.onStart()
        initRV()
    }

    private fun initRV(){
        //resultDatas.addAll(resultDB.restaurantDao().alignPopular())

        binding.searchResultTv.text = intent.getStringExtra("Search_word").toString()
        binding.searchResultCountTv.text = intent.getStringExtra("Search_word").toString() + " 검색 결과 ("+ resultDatas.size.toString() + "건)"

        val restaurantResultRVAdapter = RestaurantResultRVAdapter(resultDatas)

        binding.searchResultRv.adapter = restaurantResultRVAdapter
        binding.searchResultRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        restaurantResultRVAdapter.setMyItemClickListener(object: RestaurantResultRVAdapter.MyItemClickListener{
            override fun onItemClick(restaurant: Restaurant) {

            }

        })
    }

    private fun pickAlign(textView: TextView, context: Context) {
        AlertDialog.Builder(context)
            .setItems(array, object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    val current = array[p1]
                    textView.text = current
                    if(current == array[0]){
                        resultDatas.addAll(resultDB.restaurantDao().alignPopular())
                    }
                    else{
                        resultDatas.addAll(resultDB.restaurantDao().alignDistance())
                    }
                }
            }).show()
        Log.d("Align_way", textView.text.toString())
    }


}