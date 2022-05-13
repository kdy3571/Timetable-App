package kr.ac.kumoh.s20170419.mygradecalculator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_timetable_add.*
import kotlinx.android.synthetic.main.listdesign.*
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityMainBinding
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityTimetableAddBinding
import kotlin.math.log10
import kotlin.math.pow

class TimetableAdd : MainActivity() {
    lateinit var binding : ActivityTimetableAddBinding
    lateinit var mbinding: ActivityMainBinding
    private val model: ViewModel by viewModels()
    private lateinit var dbadapter: DatabaseAdapter
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimetableAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(mbinding.root)

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

    private fun adapterOnClick(subjectdata: ViewModel.Subject):Unit {
        val dlg = kr.ac.kumoh.s20170419.mygradecalculator.Dialog(this)
        val intent = Intent(this, MainActivity::class.java)
        dlg.dialog()
        dlg.setOnClickedListener(object : kr.ac.kumoh.s20170419.mygradecalculator.Dialog.ButtonClickListener{
            override fun onClicked(data: Int) {
                if(data == 1) {
                    timesplit(subjectdata)
                }
                else if(data == 0)
                    Toast.makeText(getApplication(), "취소당", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun timesplit(subjectdata: ViewModel.Subject){
        val time = subjectdata.time.split(", ")
        for (t in time) {
            val n = log10(t.toDouble()).toInt().toDouble()
            var day: String? = null
            when ((t.toInt() / 10.0.pow(n)).toInt()) {
                0 -> day = "monday"
                1 -> day = "tuesday"
                2 -> day = "wednesday"
                3 -> day = "thursday"
                4 -> day = "friday"
            }
            var resID = resources.getIdentifier(day + (((t.toInt() % 10.0.pow(n)).toInt()) + 8), "id", packageName)
            if (t == "09")
                resID = resources.getIdentifier(day + t.toInt(), "id", packageName)

            val week_id = findViewById<TextView>(resID)
            week_id.text = subjectdata.name
            week_id.setBackgroundColor(Color.GREEN)
        }
    }
}