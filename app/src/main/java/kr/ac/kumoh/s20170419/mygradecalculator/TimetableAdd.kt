package kr.ac.kumoh.s20170419.mygradecalculator

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
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
    private lateinit var searchData : String
    var college = ""
    var major = ""
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimetableAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbmodel = ViewModelProvider(this@TimetableAdd).get(InnerDBViewmodel::class.java)

        val gradeData: Array<String> = resources.getStringArray(R.array.grade)
        val gradeAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, gradeData)
        binding.gradeSpinner.adapter = gradeAdapter

        val semesterData: Array<String> = resources.getStringArray(R.array.semester)
        val semesterAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, semesterData)
        binding.semesterSpinner.adapter = semesterAdapter

        val subjectData: Array<String> = resources.getStringArray(R.array.subject)
        val subjectAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, subjectData)
        binding.subjectSpinner.adapter = subjectAdapter

        val divisionData: Array<String> = resources.getStringArray(R.array.division)
        val divisionAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, divisionData)
        binding.divisionSpinner.adapter = divisionAdapter

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

        if (intent.hasExtra("gs")) {
            val temp = intent.getStringExtra("gs")!!.split("-")
            college =  intent.getStringExtra("college")!!
            major =  intent.getStringExtra("major")!!
        }
        model.requestList(college, major, gradeSpinner.selectedItem.toString(), semesterSpinner.selectedItem.toString(), subjectSpinner.selectedItem.toString(), divisionSpinner.selectedItem.toString())


        search_btn.setOnClickListener {
            searchData = binding.et1.text.toString()
            model.requestList(college, major, "전체", "1", "전체", "전체")
            model.search(searchData, "name") // ""에는 searchType 입력
        }
    }

    private fun adapterOnClick(subjectData: ViewModel.Subject): Unit {
        val dlg = Dialog(this)
        val mIntent = Intent(this, MainActivity::class.java)
        dlg.dialog(subjectData.name, "추가")
        dlg.setOnClickedListener(object :
            Dialog.ButtonClickListener {
            override fun onClicked(data: Int) {
                if (data == 1) {
                    connect(subjectData)
                    mIntent.putExtra("manual", 1)
                    mIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    finish()
                    startActivity(mIntent)
                } else if (data == 0)
                    Toast.makeText(application, "취소하였습니다.", Toast.LENGTH_LONG).show()
            }
        })
    }
    fun connect(subjectData: ViewModel.Subject){
        Thread(Runnable {
            dbmodel.connect(gs, subjectData)
        }).start()
    }
}
