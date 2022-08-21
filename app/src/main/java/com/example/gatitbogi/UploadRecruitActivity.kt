package com.example.gatitbogi

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gatitbogi.databinding.ActivityUploadRecruitBinding
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*


class UploadRecruitActivity : AppCompatActivity() {
    lateinit var binding: ActivityUploadRecruitBinding
    lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadRecruitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.uploadRecruitTitleEt.clearFocus()

        // 구해요 제목을 작성하였을 때
        writeTitle()

        // 클릭 이벤트 발생 시
        onClickListener()

        //모두 다 작성하였을 때
        writeAllView()

    }

    // edittext 이외 영역 클릭 시 키보드를 숨기도록 재정의
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
            }
            binding.uploadRecruitTitleEt.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

    // 키보드 숨기기
    private fun hideKeyBoard(){
        var inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    // 제목 작성 시
    private fun writeTitle(){
        binding.uploadRecruitActivity.viewTreeObserver.addOnGlobalLayoutListener {
            val rec = Rect()
            binding.uploadRecruitActivity.getWindowVisibleDisplayFrame(rec)

            val screenHeight = binding.uploadRecruitActivity.height

            val keypadHeight = screenHeight - rec.bottom

            if (keypadHeight < screenHeight * 0.15) {

                if (binding.uploadRecruitTitleEt.text.toString().isNotEmpty()) {
                    binding.uploadRecruitTitleIv.setColorFilter(Color.parseColor("#6574FF"))
                }
                else {
                    binding.uploadRecruitTitleIv.setColorFilter(Color.parseColor("#F2F1F1"))
                }
            }
            else {
                binding.uploadRecruitBtnTv.setTextColor(Color.parseColor("#797979"))
                binding.uploadRecruitBtnIv.setBackgroundColor(Color.parseColor("#F2F1F1"))
                binding.uploadRecruitBtnCv.setOnClickListener {
                    binding.uploadRecruitBtnTv.setTextColor(Color.parseColor("#797979"))
                    binding.uploadRecruitBtnIv.setBackgroundColor(Color.parseColor("#F2F1F1"))
                    Toast.makeText(this,"아직 작성되지 않은 부분이 있어요 😅", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // 모든 TextView를 작성
    private fun writeAllView(){
        binding.uploadRecruitActivity.viewTreeObserver.addOnGlobalLayoutListener {
            if(binding.uploadRecruitTitleEt.text.toString().isNotEmpty() && binding.uploadRecruitSelectDateTv.text.toString().isNotEmpty()
                && binding.uploadRecruitSelectTimeTv.text.toString().isNotEmpty() && binding.uploadRecruitSelectPersonTv.text.toString().isNotEmpty()) {
                binding.uploadRecruitBtnTv.setTextColor(Color.parseColor("#FFFFFF"))
                binding.uploadRecruitBtnIv.setBackgroundColor(Color.parseColor("#6574FF"))

                binding.uploadRecruitBtnCv.setOnClickListener {
                    // 구해요를 다 작성하였을 때
                    Toast.makeText(this,"업로드 완료! 이제 기다려봐요 😆", Toast.LENGTH_LONG).show()

                    val recruitDB = RecruitDatabase.getInstance(this)!!
                    recruitDB.recruitDao().insert(getRecruit())

                    finish()
                }
            }
            else {
                binding.uploadRecruitBtnTv.setTextColor(Color.parseColor("#797979"))
                binding.uploadRecruitBtnIv.setBackgroundColor(Color.parseColor("#F2F1F1"))
                binding.uploadRecruitBtnCv.setOnClickListener {
                    binding.uploadRecruitBtnTv.setTextColor(Color.parseColor("#797979"))
                    binding.uploadRecruitBtnIv.setBackgroundColor(Color.parseColor("#F2F1F1"))
                    Toast.makeText(this,"아직 작성되지 않은 부분이 있어요 😅", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // 클릭 이벤트 함수
    private fun onClickListener(){

        // 날짜 선택시
        binding.uploadRecruitSelectDateIv.setOnClickListener {
            DatePicker(binding.uploadRecruitSelectDateTv,this)
        }

        // 시간 선택시
        binding.uploadRecruitSelectTimeIv.setOnClickListener {
            TimePicker(binding.uploadRecruitSelectTimeTv,this)
        }

        // 인원 수 선택시
        binding.uploadRecruitSelectPersonIv.setOnClickListener {
            PersonnelPicker(binding.uploadRecruitSelectPersonTv, this)
        }

        // 토글 켜기
        binding.uploadToggleOff.setOnClickListener {
            binding.uploadToggleOff.visibility = View.GONE
            binding.uploadToggleOn.visibility = View.VISIBLE
        }

        // 토글 끄기
        binding.uploadToggleOn.setOnClickListener {
            binding.uploadToggleOn.visibility = View.GONE
            binding.uploadToggleOff.visibility = View.VISIBLE
        }

        //뒤로가기 누를 시
        binding.uploadRecruitBackIv.setOnClickListener {
            finish()
        }
    }

    // 인원수 선택 대화창
    private fun PersonnelPicker(textView: TextView, context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val view: View = this.layoutInflater.inflate(R.layout.dialog_personnel, null)
        builder.setView(view)

        val picker = view.findViewById<View>(R.id.dialog_person_picker) as NumberPicker
        picker.minValue = 2
        picker.maxValue = 8
        picker.wrapSelectorWheel = false

        builder.setPositiveButton(
            R.string.ok,
            DialogInterface.OnClickListener { dialog, id ->
                textView.text = picker.value.toString() + "명 희망"
                Log.d("Person", textView.text.toString())
            })
            .setNegativeButton(
                R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->
                })
        builder.create().show()
    }

    // 날짜 선택 대화창
    private fun DatePicker(textView: TextView, context: Context){
        val cal = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, day)

            textView.text = SimpleDateFormat("yyyy년 M월 d일").format(cal.timeInMillis)
            Log.d("Date", textView.text.toString())
        },cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.show()


    }

    // 시간 선택 대화창
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
                Log.d("Time", textView.text.toString())
            })
            .setNegativeButton(
                R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->
                })
        builder.create().show()
    }

    private fun getRecruit(): Recruit {
        val title: String = binding.uploadRecruitTitleEt.text.toString()
        val date: String = binding.uploadRecruitSelectDateTv.text.toString()
        val time: String = binding.uploadRecruitSelectTimeTv.text.toString()
        val person: String = binding.uploadRecruitSelectPersonTv.text.get(0).toString()
        var other = false

        if(binding.uploadToggleOff.visibility == View.VISIBLE)
             other = false
        else if(binding.uploadToggleOn.visibility == View.VISIBLE)
            other = true

        return Recruit(0, "", title, date, time, person, 0, other, 1)
    }
}