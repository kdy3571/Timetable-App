package kr.ac.kumoh.s20170419.mygradecalculator
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.activity.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_timetable_add.*
import kotlinx.android.synthetic.main.activity_timetable_add.view.*
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityTimetableAddBinding
import kotlin.properties.ReadWriteProperty

class TimetableAdd : AppCompatActivity() {
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

        dbadapter = DatabaseAdapter(model) {subject -> adapterOnClick(subject) }
        binding.listdata.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = this@TimetableAdd.dbadapter
        }
        model.list.observe(this) {
            dbadapter.notifyDataSetChanged()
        }

        search_button.setOnClickListener{
            val year:String = yearSpinner.selectedItem.toString()
            val term:String = termSpinner.selectedItem.toString()
            val area:String = areaSpinner.selectedItem.toString()
            val major:String = majorSpinner.selectedItem.toString()
            if(major == "컴퓨터공학과")
                model.requestList(year, term, area)
            else { // 나머지   학과
                model.requestList("", "", area)
            }
        }
    }
    private fun adapterOnClick(mechanic: ViewModel.Subject):Unit {
    }
}