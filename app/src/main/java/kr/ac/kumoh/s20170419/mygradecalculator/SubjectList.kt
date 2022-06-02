package kr.ac.kumoh.s20170419.mygradecalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_subject_list.*
import kotlinx.android.synthetic.main.activity_timetable_add.*
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivitySubjectListBinding

class SubjectList : TimetableGeneration() {
    lateinit var binding : ActivitySubjectListBinding
    private val model: ViewModel by viewModels()
    private lateinit var dbadapter: DatabaseAdapter
    var searchType : String = "name"
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubjectListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.requestList(college, major, "전체", semester, "전체", "전체")
        dbadapter = DatabaseAdapter(model) { subject -> adapterOnClick(subject) }
        binding.list.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = this@SubjectList.dbadapter
        }
        binding.Ridiogroup.check(R.id.RB1)
        binding.Ridiogroup.setOnCheckedChangeListener { radioGroup, i ->
            when(i) {
                R.id.RB1 -> searchType = "name"
                R.id.RB2 -> searchType = "code"
                R.id.RB3 -> searchType = "professor"
            }
        }

        model.list.observe(this) {
            dbadapter.notifyDataSetChanged()
        }

        searchView_subject_list.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                model.requestList(college, major, "전체", semester, "전체", "전체")
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                model.search(query, searchType) // ""에는 searchType 입력
                dbadapter.notifyDataSetChanged()
                hideSoftInput()
                return false
            }
        })
    }

    fun hideSoftInput() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(searchView_subject_list.windowToken, 0)
    }

    private fun adapterOnClick(subjectData: ViewModel.Subject):Unit {
        val dlg = Dialog(this)
        val intent = intent
        val subject = intent.getSerializableExtra("list") as ArrayList<ViewModel.Subject>
        var removeSubject = ArrayList<String>()
        val resultIntent = Intent(this, TimetableGeneration::class.java)

        if(intent.hasExtra("list")) {
            val sTime = subjectData.time.split(", ")

            if (subject.isEmpty())
                dlg.dialog(subjectData.name, "추가")
            else {
                loop@ for (i in subject) {
                    val time = i.time.split(", ")
                    for (j in time) {
                        for (k in sTime) {
                            if (k == j) {
                                dlg.dialog(i.name, "변경")
                                removeSubject.add(i.name)
                                break@loop
                            }
                        }
                    }
                }
                if (removeSubject.isEmpty())
                    dlg.dialog(subjectData.name, "추가")

                for(i in subject)
                    if (i.name == subjectData.name)
                        removeSubject.add(i.name)
            }
        }

        dlg.setOnClickedListener(object : kr.ac.kumoh.s20170419.mygradecalculator.Dialog.ButtonClickListener{
            override fun onClicked(data: Int) {
                if(data == 1) {
                    if(intent.hasExtra("type")) {
                        when (intent.getStringExtra("type")) {
                            "선택" -> {
                                resultIntent.putExtra("button", "선택")
                            }
                            "제외" -> {
                                resultIntent.putExtra("button", "제외")
                            }
                        }
                    }
                    for (i in removeSubject) // 해당 과목리스트에서 삭제
                        subject.removeIf { it.name == i }
                    subject.add(subjectData)
                    resultIntent.putExtra("data", subject)
                    finish()
                    startActivity(resultIntent)
                }
                else if(data == 0)
                    Toast.makeText(application, "취소하였습니다.", Toast.LENGTH_LONG).show()
            }
        })
    }
}