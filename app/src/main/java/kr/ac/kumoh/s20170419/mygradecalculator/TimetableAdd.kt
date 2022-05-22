package kr.ac.kumoh.s20170419.mygradecalculator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_timetable_add.*
import kotlinx.android.synthetic.main.listdesign.*
import kotlinx.android.synthetic.main.timetable_layout.*
import kotlinx.android.synthetic.main.timetable_layout.view.*
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityTimetableAddBinding
import java.lang.Thread.sleep

class TimetableAdd : MainActivity() {
    lateinit var binding : ActivityTimetableAddBinding
    private val model: ViewModel by viewModels()
    private lateinit var dbadapter: DatabaseAdapter
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimetableAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val yeardata:Array<String> = resources.getStringArray(R.array.grade)
        val yearadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, yeardata)
        binding.yearSpinner.adapter = yearadapter

        val termdata:Array<String> = resources.getStringArray(R.array.semester)
        val termadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, termdata)
        binding.termSpinner.adapter = termadapter

        val areadata:Array<String> = resources.getStringArray(R.array.area)
        val areaadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, areadata)
        binding.areaSpinner.adapter = areaadapter

        val majordata:Array<String> = resources.getStringArray(R.array.major)
        val majoradapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, majordata)
        binding.majorSpinner.adapter = majoradapter

        val search_button : Button = findViewById(R.id.search_button)

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

        search_button.setOnClickListener {
            val year: String = yearSpinner.selectedItem.toString()
            val term: String = termSpinner.selectedItem.toString()
            val area: String = areaSpinner.selectedItem.toString()
            val major: String = majorSpinner.selectedItem.toString()
            model.requestList(major, year, term, area)
        }
    }

    private fun adapterOnClick(subjectdata: ViewModel.Subject):Unit {
        val dlg = kr.ac.kumoh.s20170419.mygradecalculator.Dialog(this)
        val iintent = Intent(this, MainActivity::class.java)
        dlg.dialog()
        dlg.setOnClickedListener(object : kr.ac.kumoh.s20170419.mygradecalculator.Dialog.ButtonClickListener{
            override fun onClicked(data: Int) {
                if(data == 1) {
                    iintent.putExtra("name", subjectdata.name)
                    iintent.putExtra("time", subjectdata.time)
                    finish()
                    startActivity(iintent)
                }
                else if(data == 0)
                    Toast.makeText(getApplication(), "취소당", Toast.LENGTH_LONG).show()
            }
        })
    }
}