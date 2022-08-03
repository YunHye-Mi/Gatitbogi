package com.example.gatitbogi

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gatitbogi.databinding.ActivityUploadRecruitBinding
import java.text.SimpleDateFormat
import java.util.*


class UploadRecruitActivity : AppCompatActivity() {
    lateinit var binding: ActivityUploadRecruitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadRecruitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(binding.uploadRecruitTitleEt.text.toString().isNotEmpty()){
            binding.uploadRecruitTitleIv.visibility = View.VISIBLE
            binding.uploadRecruitTitleEmptyIv.visibility = View.GONE
        }

        else if(binding.uploadRecruitTitleEt.text.toString().isEmpty()) {
            binding.uploadRecruitTitleEmptyIv.visibility = View.VISIBLE
            binding.uploadRecruitTitleIv.visibility = View.GONE
        }

        if(binding.uploadRecruitTitleEt.text.toString().isNotEmpty() && binding.uploadRecruitDateTv.text.toString().isNotEmpty()
            && binding.uploadRecruitTimeTv.text.toString().isNotEmpty() && binding.uploadRecruitPersonTv.text.toString().isNotEmpty()){
            binding.uploadRecruitActiveBtnTv.visibility = View.VISIBLE
            binding.uploadRecruitInactiveBtnTv.visibility = View.GONE

            binding.uploadRecruitActiveBtnTv.setOnClickListener {
                finish()
            }
        }


        onClickListener()

    }
    //Clicklistener
    private fun onClickListener(){
        binding.uploadRecruitSelectDateIv.setOnClickListener {
            getDate(binding.uploadRecruitSelectDateTv,this)
        }

        binding.uploadRecruitSelectTimeIv.setOnClickListener {
            getTime(binding.uploadRecruitSelectTimeTv,this)
        }

        binding.uploadRecruitSelectPersonIv.setOnClickListener {
            getPerson(binding.uploadRecruitSelectPersonTv, this)
        }

        binding.uploadToggleOff.setOnClickListener {
            binding.uploadToggleOff.visibility = View.GONE
            binding.uploadToggleOn.visibility = View.VISIBLE
        }

        binding.uploadToggleOn.setOnClickListener {
            binding.uploadToggleOn.visibility = View.GONE
            binding.uploadToggleOff.visibility = View.VISIBLE
        }

        binding.uploadRecruitBackIv.setOnClickListener {
            finish()
        }
    }

    //Dialog
    private fun getPerson(textView: TextView, context: Context) {

    }

    //DatePickerDialog
    private fun getDate(textView: TextView, context: Context){
        val cal = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, day)

            textView.text = SimpleDateFormat("yyyy년 M월 dd일").format(cal.timeInMillis)
            },cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.show()
    }

    //TimePickerDialog
    private fun getTime(textView: TextView, context: Context){
        val cal = Calendar.getInstance()

        val timeSetListener = OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            textView.text = SimpleDateFormat("aa hh:mm").format(cal.time)
        }
        TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
    }
}