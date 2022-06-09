package kr.ac.kumoh.s20170419.mygradecalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_subject_list.*
import kotlinx.android.synthetic.main.activity_timetable_add.*
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityTimetableAddBinding
import kotlin.concurrent.thread


class TimetableAdd : MainActivity() {
    lateinit var binding: ActivityTimetableAddBinding
    private val model: ViewModel by viewModels()
    private lateinit var dbadapter: DatabaseAdapter
    private lateinit var dbmodel : InnerDBViewmodel
    var searchType : String = "name"
    var college = ""
    var major = ""
    val mainActivity = MainActivity
    var alldbtable : List<weekstate> = arrayListOf()
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimetableAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbmodel = ViewModelProvider(this@TimetableAdd).get(InnerDBViewmodel::class.java)
        DBGetAll()

        val gradeData: Array<String> = resources.getStringArray(R.array.grade)
        val gradeAdapter = ArrayAdapter(this, R.layout.item_spinner, gradeData)
        binding.gradeSpinner.adapter = gradeAdapter

        val semesterData: Array<String> = resources.getStringArray(R.array.semester)
        val semesterAdapter = ArrayAdapter(this, R.layout.item_spinner, semesterData)
        binding.semesterSpinner.adapter = semesterAdapter

        val subjectData: Array<String> = resources.getStringArray(R.array.subject)
        val subjectAdapter = ArrayAdapter(this, R.layout.item_spinner, subjectData)
        binding.subjectSpinner.adapter = subjectAdapter

        val divisionData: Array<String> = resources.getStringArray(R.array.division)
        val divisionAdapter = ArrayAdapter(this, R.layout.item_spinner, divisionData)
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

        binding.Ridiogroup.check(R.id.RB1)
        binding.Ridiogroup.setOnCheckedChangeListener { radioGroup, i ->
            when(i) {
                R.id.RB1 -> searchType = "name"
                R.id.RB2 -> searchType = "code"
                R.id.RB3 -> searchType = "professor"
            }
        }

        val user = getSharedPreferences("user", Context.MODE_PRIVATE)
        if (user.getString("college", "") != "") {
            college =  user.getString("college", "")!!
            major =  user.getString("major", "")!!
        }

        model.requestList(college, major, gradeSpinner.selectedItem.toString(), semesterSpinner.selectedItem.toString(), subjectSpinner.selectedItem.toString(), divisionSpinner.selectedItem.toString())

        gradeSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                model.requestList(college, major, gradeSpinner.selectedItem.toString(), semesterSpinner.selectedItem.toString(), subjectSpinner.selectedItem.toString(), divisionSpinner.selectedItem.toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        semesterSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                model.requestList(college, major, gradeSpinner.selectedItem.toString(), semesterSpinner.selectedItem.toString(), subjectSpinner.selectedItem.toString(), divisionSpinner.selectedItem.toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        subjectSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                model.requestList(college, major, gradeSpinner.selectedItem.toString(), semesterSpinner.selectedItem.toString(), subjectSpinner.selectedItem.toString(), divisionSpinner.selectedItem.toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        divisionSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                model.requestList(college, major, gradeSpinner.selectedItem.toString(), semesterSpinner.selectedItem.toString(), subjectSpinner.selectedItem.toString(), divisionSpinner.selectedItem.toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        searchView_timetable_add.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                model.search(query, searchType) // ""에는 searchType 입력
                dbadapter.notifyDataSetChanged()
                hideSoftInput()
                Thread.sleep(100L)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                model.requestList(college, major, gradeSpinner.selectedItem.toString(), semesterSpinner.selectedItem.toString(), subjectSpinner.selectedItem.toString(), divisionSpinner.selectedItem.toString())
                return false
            }
        })
    }

    override fun onBackPressed(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun hideSoftInput() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(searchView_timetable_add.windowToken, 0)
    }

    private fun adapterOnClick(subjectData: ViewModel.Subject): Unit {
        val dlg = Dialog(this)
        val Intent = Intent(this, MainActivity::class.java)
        var trigger = 0
        for (i in 0 until alldbtable.size){
            if(alldbtable[i].name == subjectData.name)
                trigger = 1
        }
        if (trigger == 1)
            dlg.dialog(subjectData.name, "변경")
        else
            dlg.dialog(subjectData.name, "추가")
        dlg.setOnClickedListener(object :
            Dialog.ButtonClickListener {
            override fun onClicked(data: Int) {
                if (data == 1) {
                    if (trigger == 1)
                        deleteSchedule(subjectData.name)
                    connect(subjectData)
                    Thread.sleep(100L)
                    Intent.putExtra("manual", 1)
                    finish()
                    startActivity(Intent)
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

    private fun DBGetAll(){
        Thread(Runnable {
            alldbtable = dbmodel.getSubject(gs)
        }).start()
        Thread.sleep(100L)
    }

    fun deleteSchedule(str : String){
        Thread(Runnable {
            dbmodel.deleteDB(str, gs)
        }).start()
        Thread.sleep(100L)
    }
}
