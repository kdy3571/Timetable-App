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
import androidx.activity.viewModels
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.listdesign.*
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityMainBinding
import kotlin.math.log10
import kotlin.math.pow

private var week = Array(5){ kotlin.arrayOfNulls<String?>(14)}

open class MainActivity : AppCompatActivity() {
    private lateinit var view: ActivityMainBinding
    private val model: ViewModel by viewModels()
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

    override fun onResume() {
        super.onResume()
        Log.d("life_cycle", "onResume")

        view.button2.setOnClickListener {
            val intent = Intent(this, TimetableGeneration::class.java)
            startActivity(intent)
        }

        view.button.setOnClickListener {
            val intent = Intent(this, TimetableAdd::class.java)
            finish()
            startActivity(intent)
            timesplit()
            setting()
        }

        view.button3.setOnClickListener {
            val intent = Intent(this, GradeManagement::class.java)
            startActivity(intent)
        }
    }

    fun timesplit() {
        val time = intent.getStringExtra("time")?.split(", ")
        if (time != null) {
            for (t in time) {
                val temp = t.split(":")
                var day: String? = null
                when (temp[0].toInt()) {
                    0 -> week[0][temp[1].toInt()] = intent.getStringExtra("name")
                    1 -> week[1][temp[1].toInt()] = intent.getStringExtra("name")
                    2 -> week[2][temp[1].toInt()] = intent.getStringExtra("name")
                    3 -> week[3][temp[1].toInt()] = intent.getStringExtra("name")
                    4 -> week[4][temp[1].toInt()] = intent.getStringExtra("name")
                }
            }
        }
    }
    fun setting() {
        for (i in 0 until week.size){
            for (j in 0 until week[0].size){
                if (week[i][j] != null) {
                    when (i) {
                        0 -> {
                            val resID = resources.getIdentifier("monday" + (j + 9), "id", packageName)
                            val weekID = findViewById<TextView>(resID)
                            weekID.text = week[i][j]
                            weekID.setBackgroundColor(Color.GREEN)
                        }
                        1 -> {
                            val resID = resources.getIdentifier("tuesday" + (j + 9), "id", packageName)
                            val weekID = findViewById<TextView>(resID)
                            weekID.text = week[i][j]
                            weekID.setBackgroundColor(Color.GREEN)
                        }
                        2 -> {
                            val resID = resources.getIdentifier("wednesday" + (j + 9), "id", packageName)
                            val weekID = findViewById<TextView>(resID)
                            weekID.text = week[i][j]
                            weekID.setBackgroundColor(Color.GREEN)
                        }
                        3 -> {
                            val resID = resources.getIdentifier("thursday" + (j + 9), "id", packageName)
                            val weekID = findViewById<TextView>(resID)
                            weekID.text = week[i][j]
                            weekID.setBackgroundColor(Color.GREEN)
                        }
                        4 -> {
                            val resID = resources.getIdentifier("friday" + (j + 9), "id", packageName)
                            val weekID = findViewById<TextView>(resID)
                            weekID.text = week[i][j]
                            weekID.setBackgroundColor(Color.GREEN)
                        }
                    }
                }
            }
        }
    }
}
