package com.example.gatitbogi

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gatitbogi.databinding.ActivitySearchBinding
import com.google.gson.Gson


class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding
    lateinit var searchDB: RecruitDatabase
    lateinit var result: Intent
    private var searchDatas = ArrayList<Search>()

    private var gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Activity 시작 시 EditText에 포커스하여 키보드 올리기
        binding.searchEt.requestFocus()
        result = Intent(this, SearchResultActivity::class.java)

        searchDB = RecruitDatabase.getInstance(this)!!

        binding.searchEt.imeOptions = EditorInfo.IME_ACTION_SEARCH

        onSearch()

        onClickListener()
    }

    override fun onStart() {
        super.onStart()
        initRV()
    }

    // SearchView 이외 부분 클릭 시 포커스 해제되도록 재정의
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        var view = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText && !view.javaClass.name.startsWith(
                "android.webkit."
            )
        ) {
            var scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x = ev.rawX + view.getLeft() - scrcoords[0]
            val y = ev.rawY + view.getTop() - scrcoords[1]
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) {
                hideKeyBoard()
                binding.searchEt.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun onClickListener(){
        binding.searchBackIv.setOnClickListener {
            finish()
        }

        binding.searchDeleteEtIv.setOnClickListener {
            binding.searchEt.text.clear()
            binding.searchDeleteEtIv.visibility = View.GONE
        }

        binding.searchRecentDeleteTv.setOnClickListener {
            searchDatas.clear()
            searchDB.searchDao().allDelete()
        }
    }

    private fun onSearch(){
        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }
            override fun afterTextChanged(editable: Editable) {
                if(editable.isNotEmpty()) {
                    binding.searchDeleteEtIv.visibility = View.VISIBLE
                }
                else {
                    binding.searchDeleteEtIv.visibility = View.GONE
                }
            }
        })

        binding.searchEt.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if(p1 == EditorInfo.IME_ACTION_SEARCH) {
                    if(p0!!.text.isNotEmpty()){
                        result.putExtra("Search_word", p0.text.toString())
                        startActivity(result)
                    }
                    else {
                        result.putExtra("Search_word", p0.hint.toString())
                        startActivity(result)
                    }

                    searchDB.searchDao().insert(getSearch())
                    return true
                }
                return false
            }
        })
    }

    private fun initRV() {
        searchDatas.addAll(searchDB!!.searchDao().getSearch() as ArrayList<Search>)
        val searchRecentRVAdapter = SearchRecentRVAdapter(searchDatas)

        binding.searchRecentRv.adapter = searchRecentRVAdapter
        binding.searchRecentRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        searchRecentRVAdapter.setMyItemClickListener(object: SearchRecentRVAdapter.MyItemClickListener{
            override fun onItemClick(search: Search) {
                result.putExtra("Search_word", search.terms)
                startActivity(result)
            }

            override fun onRemoveSearch(position: Int) {
                searchDatas.removeAt(position)
            }
        })
    }

    // 키보드 숨기기
    private fun hideKeyBoard(){
        var inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun getSearch(): Search {
        var terms = binding.searchEt.text.toString().ifEmpty { binding.searchEt.hint.toString() }

        return Search(terms)
    }
}
