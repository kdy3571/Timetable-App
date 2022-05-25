package kr.ac.kumoh.s20170419.mygradecalculator

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityAutoTableBinding

class autoTable : AppCompatActivity() {
    private lateinit var binding: ActivityAutoTableBinding
    var weekdata =  Array(5) { arrayOfNulls<String?>(11) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAutoTableBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("timetable")) {
            weekdata = intent.getSerializableExtra("timetable") as Array<Array<String?>>
            setting()
        }

        binding.addButton.setOnClickListener {
            val intent = Intent(this, TimetableGeneration::class.java)
            intent.putExtra("button", "추가")
            finish()
            startActivity(intent)
        }

        binding.resetButton.setOnClickListener {
            val intent = Intent(this, TimetableGeneration::class.java)
            intent.putExtra("button", "재생성")
            finish()
            startActivity(intent)
        }
    }


    fun setting() {
        var resID: Int
        lateinit var weekID: TextView
        for (i in weekdata.indices) {
            for (j in 0 until weekdata[0].size) {
                when (i) {
                    0 -> {
                        resID = resources.getIdentifier("monday" + (j + 9), "id", packageName)
                        weekID = findViewById(resID)
                    }
                    1 -> {
                        resID = resources.getIdentifier("tuesday" + (j + 9), "id", packageName)
                        weekID = findViewById(resID)
                    }
                    2 -> {
                        resID = resources.getIdentifier("wednesday" + (j + 9), "id", packageName)
                        weekID = findViewById(resID)
                    }
                    3 -> {
                        resID = resources.getIdentifier("thursday" + (j + 9), "id", packageName)
                        weekID = findViewById(resID)
                    }
                    4 -> {
                        resID = resources.getIdentifier("friday" + (j + 9), "id", packageName)
                        weekID = findViewById(resID)
                    }
                }
                val range = 1..255
                weekID.text = weekdata[i][j] ?: ""
                if (weekdata[i][j] != null)
                    weekID.setBackgroundColor(Color.rgb(range.random(), range.random(), range.random()))
                else
                    weekID.setBackgroundResource(R.drawable.cell)
            }
        }
    }
}