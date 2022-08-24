package com.example.gatitbogi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gatitbogi.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    lateinit var restaurantDB: RecruitDatabase
    private var restaurantDatas = ArrayList<Restaurant>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        restaurantDB = RecruitDatabase.getInstance(this.requireContext())!!

        restaurantDatas.apply{
            add(
                Restaurant(R.drawable.ic_alarm_home,"카지츠","일식", 0F,
                    "모둠꼬치 6종, 다양한 사케,...", "...", 500, 12, 2)
            )

            add(
                Restaurant(R.drawable.ic_alarm_home,"산마루돌구이","기타", 4.37F,
                    "산낙지돌구이(중), 산낙지볶음, 연포탕(중), 탕탕이,...", "...", 500, 28, 1)
            )
        }

        onClickListener()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRV()
    }

    // 각 뷰를 클릭 했을 때 실행하는 기능을 모아놓은 함수
    fun onClickListener(){
        binding.searchTv.setOnClickListener {
            val intent = Intent(this.context, SearchActivity::class.java)
            startActivity(intent)
        }

        binding.searchCategoryKoreanCv.setOnClickListener {
            val intent = Intent(this.context, SearchResultActivity::class.java)
            intent.putExtra("Search_word", "한식")
            startActivity(intent)
        }

        binding.searchCategoryAsianCv.setOnClickListener {
            val intent = Intent(this.context, SearchResultActivity::class.java)
            intent.putExtra("Search_word", "아시안음식")
            startActivity(intent)
        }

        binding.searchCategoryWesternCv.setOnClickListener {
            val intent = Intent(this.context, SearchResultActivity::class.java)
            intent.putExtra("Search_word", "양식")
            startActivity(intent)
        }

        binding.searchCategoryJapaneseCv.setOnClickListener {
            val intent = Intent(this.context, SearchResultActivity::class.java)
            intent.putExtra("Search_word", "일식")
            startActivity(intent)
        }

        binding.searchCategoryChineseCv.setOnClickListener {
            val intent = Intent(this.context, SearchResultActivity::class.java)
            intent.putExtra("Search_word", "중식")
            startActivity(intent)
        }

        binding.searchCategoryFlourCv.setOnClickListener {
            val intent = Intent(this.context, SearchResultActivity::class.java)
            intent.putExtra("Search_word", "분식")
            startActivity(intent)
        }

        binding.searchCategoryCafeCv.setOnClickListener {
            val intent = Intent(this.context, SearchResultActivity::class.java)
            intent.putExtra("Search_word", "카페")
            startActivity(intent)
        }

        binding.searchCategoryBuffetCv.setOnClickListener {
            val intent = Intent(this.context, SearchResultActivity::class.java)
            intent.putExtra("Search_word", "뷔페")
            startActivity(intent)
        }

        binding.searchCategoryOtherCv.setOnClickListener {
            val intent = Intent(this.context, SearchResultActivity::class.java)
            intent.putExtra("Search_word", "기타")
            startActivity(intent)
        }
    }

    private fun initRV(){
        //restaurantDatas.addAll(restaurantDB!!.restaurantDao().getRestaurant() as ArrayList<Restaurant>)
        val restaurantRVAdapter = RestaurantResultRVAdapter(restaurantDatas)

        binding.searchLunchRv.adapter = restaurantRVAdapter
        binding.searchLunchRv.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        restaurantRVAdapter.setMyItemClickListener(object: RestaurantResultRVAdapter.MyItemClickListener{
            override fun onItemClick(restaurant: Restaurant) {
//                var intent = Intent(context, )
//                startActivity(intent)
            }
        })
    }
}