package kr.ac.kumoh.s20170419.mygradecalculator

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat.startActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

private var weekdata = Array(5) { kotlin.arrayOfNulls<String?>(14) }
private var autoData = ArrayList<ViewModel.Subject>()

open class MainActivity : AppCompatActivity() {
    private lateinit var view: ActivityMainBinding
    private val model: ViewModel by viewModels()

    //    var db: ScheduleDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        setContentView(view.root)

//        db = ScheduleDatabase.getDatabase(this)
//        CoroutineScope(Dispatchers.IO).launch{
//            var savedContacts = db!!.weekDao().selectALL()
//            if (savedContacts.isEmpty()) {
//                weekdata[0] = arrayOf(db!!.weekDao().selectmonday())
//                weekdata[1] = arrayOf(db!!.weekDao().selecttuesday())
//                weekdata[2] = arrayOf(db!!.weekDao().selectwednesday())
//                weekdata[3] = arrayOf(db!!.weekDao().selectthursday())
//                weekdata[4] = arrayOf(db!!.weekDao().selectfriday())
//            }
//        }

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        var mInflater = menuInflater
        mInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onResume() {
        super.onResume()
        if (intent.hasExtra("auto")) {
            autoData = intent.getSerializableExtra("auto") as ArrayList<ViewModel.Subject>
            autoTable(autoData)
        }
    }


    fun timesplit() {
        val time = intent.getStringExtra("time")?.split(", ")
        if (time != null) {
            for (t in time) {
                val temp = t.split(":")
                var day: String? = null
                when (temp[0].toInt()) {
                    0 -> weekdata[0][temp[1].toInt()] = intent.getStringExtra("name")
                    1 -> weekdata[1][temp[1].toInt()] = intent.getStringExtra("name")
                    2 -> weekdata[2][temp[1].toInt()] = intent.getStringExtra("name")
                    3 -> weekdata[3][temp[1].toInt()] = intent.getStringExtra("name")
                    4 -> weekdata[4][temp[1].toInt()] = intent.getStringExtra("name")
                }
            }
        }
    }

    fun setting() {
        for (i in 0 until weekdata.size) {
            for (j in 0 until weekdata[0].size) {
                if (weekdata[i][j] != null) {
                    when (i) {
                        0 -> {
                            val resID =
                                resources.getIdentifier("monday" + (j + 9), "id", packageName)
                            val weekID = findViewById<TextView>(resID)
                            weekID.text = weekdata[i][j]
                            weekID.setBackgroundColor(Color.GREEN)

                        }
                        1 -> {
                            val resID =
                                resources.getIdentifier("tuesday" + (j + 9), "id", packageName)
                            val weekID = findViewById<TextView>(resID)
                            weekID.text = weekdata[i][j]
                            weekID.setBackgroundColor(Color.GREEN)
                        }
                        2 -> {
                            val resID =
                                resources.getIdentifier("wednesday" + (j + 9), "id", packageName)
                            val weekID = findViewById<TextView>(resID)
                            weekID.text = weekdata[i][j]
                            weekID.setBackgroundColor(Color.GREEN)
                        }
                        3 -> {
                            val resID =
                                resources.getIdentifier("thursday" + (j + 9), "id", packageName)
                            val weekID = findViewById<TextView>(resID)
                            weekID.text = weekdata[i][j]
                            weekID.setBackgroundColor(Color.GREEN)
                        }
                        4 -> {
                            val resID =
                                resources.getIdentifier("friday" + (j + 9), "id", packageName)
                            val weekID = findViewById<TextView>(resID)
                            weekID.text = weekdata[i][j]
                            weekID.setBackgroundColor(Color.GREEN)
                        }
                    }
                }
            }
        }
    }

    fun autoTable(subjectList: ArrayList<ViewModel.Subject>) {
        for (i in 0 until subjectList.size) {
            val time = subjectList[i].time.split(", ")
            for (t in time) {
                val temp = t.split(":")
                weekdata[temp[0].toInt()][temp[1].toInt()] = subjectList[i].name
            }
        }

        for (i in 0 until weekdata.size) {
            for (j in 0 until weekdata[0].size) {
                if (weekdata[i][j] != null) {
                    when (i) {
                        0 -> {
                            val resID =
                                resources.getIdentifier("monday" + (j + 9), "id", packageName)
                            val weekID = findViewById<TextView>(resID)
                            weekID.text = weekdata[i][j]
                            weekID.setBackgroundColor(Color.GREEN)

                        }
                        1 -> {
                            val resID =
                                resources.getIdentifier("tuesday" + (j + 9), "id", packageName)
                            val weekID = findViewById<TextView>(resID)
                            weekID.text = weekdata[i][j]
                            weekID.setBackgroundColor(Color.GREEN)
                        }
                        2 -> {
                            val resID =
                                resources.getIdentifier("wednesday" + (j + 9), "id", packageName)
                            val weekID = findViewById<TextView>(resID)
                            weekID.text = weekdata[i][j]
                            weekID.setBackgroundColor(Color.GREEN)
                        }
                        3 -> {
                            val resID =
                                resources.getIdentifier("thursday" + (j + 9), "id", packageName)
                            val weekID = findViewById<TextView>(resID)
                            weekID.text = weekdata[i][j]
                            weekID.setBackgroundColor(Color.GREEN)
                        }
                        4 -> {
                            val resID =
                                resources.getIdentifier("friday" + (j + 9), "id", packageName)
                            val weekID = findViewById<TextView>(resID)
                            weekID.text = weekdata[i][j]
                            weekID.setBackgroundColor(Color.GREEN)
                        }
                    }
                }
            }
        }
    }
}
