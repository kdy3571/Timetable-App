package kr.ac.kumoh.s20170419.mygradecalculator

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityMainBinding
import kotlin.collections.ArrayList

private var weekdata = Array(5) { kotlin.arrayOfNulls<String?>(14) }
private var autoData = ArrayList<ViewModel.Subject>()

open class MainActivity : AppCompatActivity() {
    private lateinit var view: ActivityMainBinding
    private val model: ViewModel by viewModels()
    var red: Int = 0
    var blue: Int = 0
    var green: Int = 0
    private lateinit var dbmodel: InnerDBViewmodel
    private lateinit var dbdata: List<weekstateminimal?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        setContentView(view.root)

        dbmodel = ViewModelProvider(this@MainActivity).get(InnerDBViewmodel::class.java)
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
        //setting()
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

    fun databaseget() {
        Thread(Runnable {
            dbmodel.getdata()
        }).start()
    }

    fun randomColor() {
        red = (Math.random() * 255).toInt()
        blue = (Math.random() * 255).toInt()
        green = (Math.random() * 255).toInt()
    }

    fun timesplit() {
        databaseget()
        Thread.sleep(1000L)
        val time = dbmodel.gettime()
        val name = dbmodel.getname()
        if (time.size != 0) {
            Log.i("test2", time.size.toString())
            for (i in 0 until time.size) {
                randomColor()
                val time2 = time[i].toString().split(", ")
                if (time2 != null) {
                    for (t in time2) {
                        val temp = t.split(":")
                        when (temp[0].toInt()) {
                            0 -> {
                                val resID =
                                    resources.getIdentifier(
                                        "monday" + (temp[1].toInt() + 9),
                                        "id",
                                        packageName
                                    )
                                val weekID = findViewById<TextView>(resID)
                                weekID.text = name[i]
                                weekID.setBackgroundColor(Color.rgb(red, blue, green))
                            }
                            1 -> {
                                val resID =
                                    resources.getIdentifier(
                                        "tuesday" + (temp[1].toInt() + 9),
                                        "id",
                                        packageName
                                    )
                                val weekID = findViewById<TextView>(resID)
                                weekID.text = name[i]
                                weekID.setBackgroundColor(Color.rgb(red, blue, green))
                            }
                            2 -> {
                                val resID =
                                    resources.getIdentifier(
                                        "wednesday" + (temp[1].toInt() + 9),
                                        "id",
                                        packageName
                                    )
                                val weekID = findViewById<TextView>(resID)
                                weekID.text = name[i]
                                weekID.setBackgroundColor(Color.rgb(red, blue, green))
                            }
                            3 -> {
                                val resID =
                                    resources.getIdentifier(
                                        "thursday" + (temp[1].toInt() + 9),
                                        "id",
                                        packageName
                                    )
                                val weekID = findViewById<TextView>(resID)
                                weekID.text = name[i]
                                weekID.setBackgroundColor(Color.rgb(red, blue, green))
                            }
                            4 -> {
                                val resID =
                                    resources.getIdentifier(
                                        "friday" + (temp[1].toInt() + 9),
                                        "id",
                                        packageName
                                    )
                                val weekID = findViewById<TextView>(resID)
                                weekID.text = name[i]
                                weekID.setBackgroundColor(Color.rgb(red, blue, green))
                            }
                        }
                    }
                }
            }
        }
    }

//        val time = intent.getStringExtra("time")?.split(", ")
//        if (time != null) {
//            for (t in time) {
//                val temp = t.split(":")
//                var day: String? = null
//                when (temp[0].toInt()) {
//                    0 -> weekdata[0][temp[1].toInt()] = intent.getStringExtra("name")
//                    1 -> weekdata[1][temp[1].toInt()] = intent.getStringExtra("name")
//                    2 -> weekdata[2][temp[1].toInt()] = intent.getStringExtra("name")
//                    3 -> weekdata[3][temp[1].toInt()] = intent.getStringExtra("name")
//                    4 -> weekdata[4][temp[1].toInt()] = intent.getStringExtra("name")
//                }
//            }
//        }


//    fun setting() {
//        for (i in 0 until weekdata.size) {
//            for (j in 0 until weekdata[0].size) {
//                if (weekdata[i][j] != null) {
//                    when (i) {
//                        0 -> {
//                            val resID =
//                                resources.getIdentifier("monday" + (j + 9), "id", packageName)
//                            val weekID = findViewById<TextView>(resID)
//                            weekID.text = weekdata[i][j]
//                            weekID.setBackgroundColor(Color.rgb(red, blue, green))
//                        }
//                        1 -> {
//                            val resID =
//                                resources.getIdentifier("tuesday" + (j + 9), "id", packageName)
//                            val weekID = findViewById<TextView>(resID)
//                            weekID.text = weekdata[i][j]
//                            weekID.setBackgroundColor(Color.rgb(red, blue, green))
//                        }
//                        2 -> {
//                            val resID =
//                                resources.getIdentifier("wednesday" + (j + 9), "id", packageName)
//                            val weekID = findViewById<TextView>(resID)
//                            weekID.text = weekdata[i][j]
//                            weekID.setBackgroundColor(Color.rgb(red, blue, green))
//                        }
//                        3 -> {
//                            val resID =
//                                resources.getIdentifier("thursday" + (j + 9), "id", packageName)
//                            val weekID = findViewById<TextView>(resID)
//                            weekID.text = weekdata[i][j]
//                            weekID.setBackgroundColor(Color.rgb(red, blue, green))
//                        }
//                        4 -> {
//                            val resID =
//                                resources.getIdentifier("friday" + (j + 9), "id", packageName)
//                            val weekID = findViewById<TextView>(resID)
//                            weekID.text = weekdata[i][j]
//                            weekID.setBackgroundColor(Color.rgb(red, blue, green))
//                        }
//                    }
//                }
//            }
//        }
//    }

    fun autoTable(subjectList: ArrayList<ViewModel.Subject>) {
        for (i in 0 until subjectList.size) {
            val time = subjectList[i].time.split(", ")
            for (t in time) {
                val temp = t.split(":")
                weekdata[temp[0].toInt()][temp[1].toInt()] = subjectList[i].name
            }
        }

        randomColor()

        for (i in 0 until weekdata.size) {
            for (j in 0 until weekdata[0].size) {
                if (weekdata[i][j] != null) {
                    when (i) {
                        0 -> {
                            val resID =
                                resources.getIdentifier("monday" + (j + 9), "id", packageName)
                            val weekID = findViewById<TextView>(resID)
                            weekID.text = weekdata[i][j]
                            weekID.setBackgroundColor(Color.rgb(red, blue, green))
                        }
                        1 -> {
                            val resID =
                                resources.getIdentifier("tuesday" + (j + 9), "id", packageName)
                            val weekID = findViewById<TextView>(resID)
                            weekID.text = weekdata[i][j]
                            weekID.setBackgroundColor(Color.rgb(red, blue, green))
                        }
                        2 -> {
                            val resID =
                                resources.getIdentifier("wednesday" + (j + 9), "id", packageName)
                            val weekID = findViewById<TextView>(resID)
                            weekID.text = weekdata[i][j]
                            weekID.setBackgroundColor(Color.rgb(red, blue, green))
                        }
                        3 -> {
                            val resID =
                                resources.getIdentifier("thursday" + (j + 9), "id", packageName)
                            val weekID = findViewById<TextView>(resID)
                            weekID.text = weekdata[i][j]
                            weekID.setBackgroundColor(Color.rgb(red, blue, green))
                        }
                        4 -> {
                            val resID =
                                resources.getIdentifier("friday" + (j + 9), "id", packageName)
                            val weekID = findViewById<TextView>(resID)
                            weekID.text = weekdata[i][j]
                            weekID.setBackgroundColor(Color.rgb(red, blue, green))
                        }
                    }
                }
            }
        }
    }
}
