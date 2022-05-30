package kr.ac.kumoh.s20170419.mygradecalculator

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_timetable_add.*
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityTimetableAddBinding

class TimetableAdd : MainActivity() {
    lateinit var binding: ActivityTimetableAddBinding
    private val model: ViewModel by viewModels()
    private lateinit var dbadapter: DatabaseAdapter
    private lateinit var dbmodel : InnerDBViewmodel
    private lateinit var searchdata : String
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimetableAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbmodel = ViewModelProvider(this@TimetableAdd).get(InnerDBViewmodel::class.java)

        val yeardata: Array<String> = resources.getStringArray(R.array.grade)
        val yearadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, yeardata)
        binding.yearSpinner.adapter = yearadapter

        val termdata: Array<String> = resources.getStringArray(R.array.semester)
        val termadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, termdata)
        binding.termSpinner.adapter = termadapter

        val areadata: Array<String> = resources.getStringArray(R.array.area)
        val areaadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, areadata)
        binding.areaSpinner.adapter = areaadapter


        dbadapter = DatabaseAdapter(model) { subject -> adapterOnClick(subject) }
        binding.listdata.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = this@TimetableAdd.dbadapter
        }
        model.list.observe(this) {
            dbadapter.notifyDataSetChanged()
        }
        model.requestList(college, major, "전체", "1", "전체", "전체")


        search_btn.setOnClickListener {
            searchdata = binding.et1.text.toString()
            model.requestList(college, major, "전체", "1", "전체", "전체")
            model.search(searchdata, "") // ""에는 searchType 입력
        }
    }

    private fun adapterOnClick(subjectdata: ViewModel.Subject): Unit {
        val dlg = kr.ac.kumoh.s20170419.mygradecalculator.Dialog(this)
        val iintent = Intent(this, MainActivity::class.java)
        dlg.dialog(subjectdata.name, "추가")
        dlg.setOnClickedListener(object :
            kr.ac.kumoh.s20170419.mygradecalculator.Dialog.ButtonClickListener {
            override fun onClicked(data: Int) {
                if (data == 1) {
                    connect(subjectdata)
                    iintent.putExtra("manual", 1)
                    iintent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    finish()
                    startActivity(iintent)
                } else if (data == 0)
                    Toast.makeText(getApplication(), "취소당", Toast.LENGTH_LONG).show()
            }
        })
    }
    fun connect(subjectdata: ViewModel.Subject){
        Thread(Runnable {
            dbmodel.connect(gs, subjectdata)
        }).start()
    }
}
