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
import org.intellij.lang.annotations.Identifier
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        setContentView(view.root)

        dbmodel = ViewModelProvider(this@MainActivity).get(InnerDBViewmodel::class.java)
        view.button2.setOnClickListener {
            val intent = Intent(this, TimetableGeneration::class.java)
            finish()
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
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        var mInflater = menuInflater
        mInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onResume() {
        super.onResume()

        if (intent.hasExtra("auto")) {
            weekdata = Array(5) { kotlin.arrayOfNulls<String?>(11) }
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
        var resID: Int
        lateinit var weekID: TextView
        databaseget()
        Thread.sleep(1000L)
        val time = dbmodel.gettime()
        val name = dbmodel.getname()
        if (time.size != 0) {
            for (i in 0 until time.size) {
                randomColor()
                val time2 = time[i].toString().split(", ")
                for (t in time2) {
                    val temp = t.split(":")
                    when (temp[0].toInt()) {
                        0 -> {
                            resID = resources.getIdentifier("monday" + (temp[1].toInt() + 9), "id", packageName)
                            weekID = findViewById<TextView>(resID)
                        }
                        1 -> {
                            resID = resources.getIdentifier("tuesday" + (temp[1].toInt() + 9), "id", packageName)
                            weekID = findViewById<TextView>(resID)
                        }
                        2 -> {
                            resID = resources.getIdentifier("wednesday" + (temp[1].toInt() + 9), "id", packageName)
                            weekID = findViewById<TextView>(resID)
                        }
                        3 -> {
                            resID = resources.getIdentifier("thursday" + (temp[1].toInt() + 9), "id", packageName)
                            weekID = findViewById<TextView>(resID)
                        }
                        4 -> {
                            resID = resources.getIdentifier("friday" + (temp[1].toInt() + 9), "id", packageName)
                            weekID = findViewById<TextView>(resID)
                        }
                    }
                    weekID.text = name[i] ?: ""
                    weekID.setBackgroundColor(Color.rgb(red, blue, green))
                }
            }
        }
    }

    private fun autoTable(subjectList: ArrayList<ViewModel.Subject>) {
        connect(subjectList)
        Thread.sleep(1000L)
        timesplit()
    }

    fun connect(subjectdata: ArrayList<ViewModel.Subject>){
        Thread(Runnable {
            for(i in 0 until subjectdata.size){
                dbmodel.connect(subjectdata[i])
            }
        }).start()
    }
}