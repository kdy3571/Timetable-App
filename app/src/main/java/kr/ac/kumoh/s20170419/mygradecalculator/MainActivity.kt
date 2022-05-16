package kr.ac.kumoh.s20170419.mygradecalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.split
import android.util.Log
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.listdesign.*
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityMainBinding
import kotlin.math.log10
import kotlin.math.pow

private var monday = Array<String?>(14, {null})

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
            finish()
            startActivity(intent)
        }

        view.button3.setOnClickListener {
            val intent = Intent(this, GradeManagement::class.java)
            startActivity(intent)
        }

        timesplit()
        setting()
    }

//    override fun onResume() {
//        super.onResume()
//        Log.d("life_cycle", "onResume")
//
//        view.button2.setOnClickListener {
//            val intent = Intent(this, TimetableGeneration::class.java)
//            startActivity(intent)
//        }
//
//        view.button.setOnClickListener {
//            val intent = Intent(this, TimetableAdd::class.java)
//            finish()
//            startActivity(intent)
//        }
//
//        view.button3.setOnClickListener {
//            val intent = Intent(this, GradeManagement::class.java)
//            startActivity(intent)
//        }
//    }

    fun timesplit() {
        val time = intent.getStringExtra("time")?.split(", ")
        if (time != null) {
            for (t in time) {
                val temp = t.split(":")
                var day: String? = null
                when (temp[0].toInt()) {
                    0 -> {day = "monday"
                        monday[temp[1].toInt()] = intent.getStringExtra("name")}
                    1 -> day = "tuesday"
                    2 -> day = "wednesday"
                    3 -> day = "thursday"
                    4 -> day = "friday"
                }
            }
        }
    }
    fun setting() {
        for (i in 0 until monday.size){
            if (monday[i] != null){
                val resID = resources.getIdentifier("monday" + (i + 9), "id", packageName)
                val weekID = findViewById<TextView>(resID)
                weekID.text = intent.getStringExtra("name")
                weekID.setBackgroundColor(Color.GREEN)
            }
        }
    }
}
