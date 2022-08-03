package com.example.gatitbogi

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
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
            DatePicker(binding.uploadRecruitSelectDateTv,this)
        }

        binding.uploadRecruitSelectTimeIv.setOnClickListener {
            TimePicker(binding.uploadRecruitSelectTimeTv,this)
        }

        binding.uploadRecruitSelectPersonIv.setOnClickListener {
            PersonnelPicker(binding.uploadRecruitSelectPersonTv, this)
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
    //PersonPickerDialog
    private fun PersonnelPicker(textView: TextView, context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val view: View = this.layoutInflater.inflate(R.layout.dialog_personnel, null)
        builder.setView(view)

        val picker = view.findViewById<View>(R.id.dialog_person_picker) as NumberPicker
        picker.minValue = 2
        picker.maxValue = 10
        picker.wrapSelectorWheel = false

        builder.setPositiveButton(
            R.string.ok,
            DialogInterface.OnClickListener { dialog, id ->
                textView.text = picker.value.toString() + "인"
            })
            .setNegativeButton(
                R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->
                })
        builder.create().show()
    }

    //DatePickerDialog
    private fun DatePicker(textView: TextView, context: Context){
        val cal = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, day)

            textView.text = SimpleDateFormat("yyyy년 M월 d일").format(cal.timeInMillis)
        },cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.show()


    }

    //TimePickerDialog
    private fun TimePicker(textView: TextView, context: Context){
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val view: View = this.layoutInflater.inflate(R.layout.dialog_time, null)
        builder.setView(view)

        val hour = view.findViewById<View>(R.id.dialog_hour_picker) as NumberPicker
        val min = view.findViewById<View>(R.id.dialog_minute_picker) as NumberPicker
        val am_pm = view.findViewById<View>(R.id.dialog_am_pm_picker) as NumberPicker

        hour.minValue = 1
        hour.maxValue = 12
        hour.wrapSelectorWheel = true

        min.minValue = 0
        min.maxValue = 5
        min.displayedValues = arrayOf("00", "10", "20", "30", "40", "50")
        min.wrapSelectorWheel = true

        am_pm.minValue = 0
        am_pm.maxValue = 1
        am_pm.displayedValues = arrayOf("오전", "오후")
        am_pm.wrapSelectorWheel = false

        builder.setPositiveButton(
            R.string.ok,
            DialogInterface.OnClickListener { dialog, id ->
                when(am_pm.value){
                    0 -> {
                        when(min.value){
                            0 -> textView.text = "오전 " + hour.value.toString() + "시"
                            1 -> textView.text = "오전 " + hour.value.toString() + "시 " + "10분"
                            2 -> textView.text = "오전 " + hour.value.toString() + "시 " + "20분"
                            3 -> textView.text = "오전 " + hour.value.toString() + "시 " + "30분"
                            4 -> textView.text = "오전 " + hour.value.toString() + "시 " + "40분"
                            else -> textView.text = "오전 " + hour.value.toString() + "시 " + "50분"
                        }
                    }
                    else -> {
                        when(min.value){
                            0 -> textView.text = "오후 " + hour.value.toString() + "시"
                            1 -> textView.text = "오후 " + hour.value.toString() + "시 " + "10분"
                            2 -> textView.text = "오후 " + hour.value.toString() + "시 " + "20분"
                            3 -> textView.text = "오후 " + hour.value.toString() + "시 " + "30분"
                            4 -> textView.text = "오후 " + hour.value.toString() + "시 " + "40분"
                            else -> textView.text = "오후 " + hour.value.toString() + "시 " + "50분"
                        }
                    }
                }
            })
            .setNegativeButton(
                R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->
                })
        builder.create().show()
    }
}