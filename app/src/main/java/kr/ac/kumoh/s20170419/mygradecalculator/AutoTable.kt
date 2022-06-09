package kr.ac.kumoh.s20170419.mygradecalculator

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityAutoTableBinding
import java.util.ArrayList

class AutoTable : AppCompatActivity() {
    private lateinit var binding: ActivityAutoTableBinding
    private var weekdata =  Array(5) { arrayOfNulls<String?>(11) }
    private var subjectInfo = ArrayList<ViewModel.Subject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAutoTableBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("timetable") && intent.hasExtra("info")) {
            weekdata = intent.getSerializableExtra("timetable") as Array<Array<String?>>
            subjectInfo = intent.getSerializableExtra("info") as ArrayList<ViewModel.Subject>
            setting()
        }

        binding.addButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("autoInfo", subjectInfo)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            finishAffinity()
            startActivity(intent)
        }

        binding.resetButton.setOnClickListener {
            val intent = Intent(this, TimetableGeneration::class.java)
            intent.putExtra("button", "재생성")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            finishAffinity()
            startActivity(intent)
        }
    }

    override fun onBackPressed(){
        val intent = Intent(this, TimetableGeneration::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        finishAffinity()
        startActivity(intent)
    }


    private fun setting() {
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
                weekID.text = weekdata[i][j] ?: ""
                if (weekdata[i][j] == null)
                    weekID.setBackgroundResource(R.drawable.cell)
            }
        }
    }
}