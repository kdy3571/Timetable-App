package kr.ac.kumoh.s20170419.mygradecalculator

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
        model.requestList("4", "1", "선택")

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
        val intent = Intent(this, TimetableGeneration::class.java)
        dlg.dialog()
        dlg.setOnClickedListener(object : kr.ac.kumoh.s20170419.mygradecalculator.Dialog.ButtonClickListener{
            override fun onClicked(data: Int) {
                if(data == 1) {
                    intent.putExtra("code", subjectData.code)
                }
                else if(data == 0)
                    Toast.makeText(application, "취소당", Toast.LENGTH_LONG).show()
            }
        })
    }
}