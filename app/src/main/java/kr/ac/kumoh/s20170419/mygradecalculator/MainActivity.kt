package kr.ac.kumoh.s20170419.mygradecalculator

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityMainBinding
import kotlin.collections.ArrayList

private var weekdata = Array(5) { kotlin.arrayOfNulls<String?>(11) }
private var autoData = ArrayList<ViewModel.Subject>()

open class MainActivity : AppCompatActivity() {
    private lateinit var view: ActivityMainBinding
    private val model: ViewModel by viewModels()
    var red: Int = 0
    var blue: Int = 0
    var green: Int = 0
    private lateinit var dbmodel: InnerDBViewmodel
    private lateinit var dbdata: List<weekstateminimal?>
    companion object {
        var gs = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        setContentView(view.root)

        if (intent.hasExtra("gs")) {
            gs = intent.getStringExtra("gs")!!
        }

        dbmodel = ViewModelProvider(this@MainActivity).get(InnerDBViewmodel::class.java)
        view.button2.setOnClickListener {
            val intent = Intent(this, TimetableGeneration::class.java)
            intent.putExtra("gs", gs)
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

        view.button4.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }
        timesplit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        var mInflater = menuInflater
        mInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                gs = "1-1"
            }
            R.id.menu2 -> {
                gs = "1-2"
            }
            R.id.menu3 -> {
                gs = "4-1"
            }
        }
        timesplit()

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if (intent.hasExtra("autoInfo")) {
            weekdata = Array(5) { kotlin.arrayOfNulls<String?>(11) }
            autoData = intent.getSerializableExtra("autoInfo") as ArrayList<ViewModel.Subject>
            autoTable(autoData)
            resetTextView()
            timesplit()
        }
        if (intent.hasExtra("manual")) {
            resetTextView()
            timesplit()
        }
    }

    fun databaseget() {
        Thread(Runnable {
            dbmodel.getweekdata(gs)
        }).start()
    }

    fun randomColor() {
        red = (Math.random() * 255).toInt()
        blue = (Math.random() * 255).toInt()
        green = (Math.random() * 255).toInt()
    }

    fun timesplit() {
        var resID: Int
        lateinit var weekID: TextView
        databaseget()
        Thread.sleep(100L)
        val time = dbmodel.gettime()
        val name = dbmodel.getname()
        if (time.size != 0) {
            for (i in 0 until time.size) {
                randomColor()
                val time2 = time[i].split(", ")
                for (t in time2) {
                    val temp = t.split(":")
                    when (temp[0].toInt()) {
                        0 -> {
                            resID = resources.getIdentifier("monday" + (temp[1].toInt() + 9), "id", packageName)
                            weekID = findViewById(resID)
                        }
                        1 -> {
                            resID = resources.getIdentifier("tuesday" + (temp[1].toInt() + 9), "id", packageName)
                            weekID = findViewById(resID)
                        }
                        2 -> {
                            resID = resources.getIdentifier("wednesday" + (temp[1].toInt() + 9), "id", packageName)
                            weekID = findViewById(resID)
                        }
                        3 -> {
                            resID = resources.getIdentifier("thursday" + (temp[1].toInt() + 9), "id", packageName)
                            weekID = findViewById(resID)
                        }
                        4 -> {
                            resID = resources.getIdentifier("friday" + (temp[1].toInt() + 9), "id", packageName)
                            weekID = findViewById(resID)
                        }
                    }
                    weekID.text = name[i] ?: ""
                    weekID.setBackgroundColor(Color.rgb(red, blue, green))
                }
            }
        }
        else
            resetTextView()
    }

    private fun autoTable(subjectList: ArrayList<ViewModel.Subject>) {
        resetDatabase()
        Thread.sleep(100L)
        connect(subjectList)
        Thread.sleep(100L)
    }

    fun connect(subjectdata: ArrayList<ViewModel.Subject>){
        Thread(Runnable {
            for(i in 0 until subjectdata.size){
                dbmodel.connect(gs, subjectdata[i])
            }
        }).start()
    }

    fun resetDatabase() {
        Thread(Runnable {
            dbmodel.resetDB(gs)
        }).start()
    }

    fun resetTextView() {
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
                weekID.text = ""
                weekID.setBackgroundResource(R.drawable.cell)
            }
        }
    }
}