package kr.ac.kumoh.s20170419.mygradecalculator

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.listdesign.*
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityMainBinding
import kotlin.math.log10
import kotlin.math.pow

open class MainActivity : AppCompatActivity() {
    private lateinit var view: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        setContentView(view.root)

        view.button2.setOnClickListener {
            val intent = Intent(this, TimetableGeneration::class.java)
            startActivity(intent)
        }

        view.button.setOnClickListener {
            val intent = Intent(this, TimetableAdd::class.java)
            startActivity(intent)
        }

        view.button3.setOnClickListener {
            val intent = Intent(this, GradeManagement::class.java)
            startActivity(intent)
        }
    }
//    fun timesplit(subjectdata: ViewModel.Subject){
//        setContentView(view.root)
//        val time = subjectdata.time.split(", ")
//        for (t in time) {
//            val n = log10(t.toDouble()).toInt().toDouble()
//            var day: String? = "monday"
//            when ((t.toInt() / 10.0.pow(n)).toInt()) {
//                0 -> day = "monday"
//                1 -> day = "tuesday"
//                2 -> day = "wednesday"
//                3 -> day = "thursday"
//                4 -> day = "friday"
//            }
//            val resID = resources.getIdentifier(day + (((t.toInt() % 10.0.pow(n)).toInt())+8), "id", packageName)
//            val week_id = findViewById<TextView>(resID)
//            week_id.text = subjectdata.name
//            week_id.setBackgroundColor(Color.GREEN)
//        }
//    }
}
