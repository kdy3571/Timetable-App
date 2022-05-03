package kr.ac.kumoh.s20170419.mygradecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityTimetableAddBinding

class TimetableAdd : AppCompatActivity() {
    lateinit var binding : ActivityTimetableAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        binding = ActivityTimetableAddBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val yeardata:Array<String> = resources.getStringArray(R.array.grade)
        val yearadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, yeardata)
        binding.yearSpinner.adapter = yearadapter

        //binding.yearSpinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener
    }
}
