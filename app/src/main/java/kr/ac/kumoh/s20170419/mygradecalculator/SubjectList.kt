package kr.ac.kumoh.s20170419.mygradecalculator

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivitySubjectListBinding

class SubjectList : TimetableGeneration() {
    lateinit var binding : ActivitySubjectListBinding
    private val model: ViewModel by viewModels()
    private lateinit var dbadapter: DatabaseAdapter
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubjectListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbadapter = DatabaseAdapter(model) { subject -> adapterOnClick(subject) }
        binding.list.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = this@SubjectList.dbadapter
        }

        model.list.observe(this) {
            dbadapter.notifyDataSetChanged()
        }

        model.filteredList.observe(this) {
            dbadapter.notifyDataSetChanged()
        }
    }

    private fun adapterOnClick(subjectData: ViewModel.Subject):Unit {
        val dlg = kr.ac.kumoh.s20170419.mygradecalculator.Dialog(this)
        val intent = intent
        val subject = intent.getSerializableExtra("list") as ArrayList<ViewModel.Subject>
        var removeSubject = ArrayList<String>()
        val resultIntent = Intent(this, TimetableGeneration::class.java)

        if(intent.hasExtra("list")) {
            val stime = subjectData.time.split(", ")

            if (subject.isEmpty())
                dlg.dialog(subjectData.name, "추가")
            else {
                loop@ for (i in subject) {
                    val time = i.time.split(", ")
                    for (j in time) {
                        for (k in stime) {
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
                    Toast.makeText(application, "취소당", Toast.LENGTH_LONG).show()
            }
        })
    }
}