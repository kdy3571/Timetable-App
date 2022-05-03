package kr.ac.kumoh.s20170419.mygradecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast

class TimetableAdd : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable_add)
        val year_spinner = findViewById<Spinner>(R.id.yearSpinner)
        year_spinner.adapter = ArrayAdapter.createFromResource(this, R.array.grade, android.R.layout.simple_spinner_item)
        year_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position != 0)
                    Toast.makeText(this@TimetableAdd, itemList[position], Toast.LENGTH_SHORT).show()
            }
        }
    }
}
