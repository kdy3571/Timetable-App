package kr.ac.kumoh.s20170419.mygradecalculator

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_auto_table.*
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityMainBinding
import java.nio.file.Files.delete
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
        monday9.setOnLongClickListener{ delete(monday9); return@setOnLongClickListener(true) }
        monday10.setOnLongClickListener{ delete(monday10); return@setOnLongClickListener(true) }
        monday11.setOnLongClickListener{ delete(monday11); return@setOnLongClickListener(true) }
        monday12.setOnLongClickListener{ delete(monday12); return@setOnLongClickListener(true) }
        monday13.setOnLongClickListener{ delete(monday13); return@setOnLongClickListener(true) }
        monday14.setOnLongClickListener{ delete(monday14); return@setOnLongClickListener(true) }
        monday15.setOnLongClickListener{ delete(monday15); return@setOnLongClickListener(true) }
        monday16.setOnLongClickListener{ delete(monday16); return@setOnLongClickListener(true) }
        monday17.setOnLongClickListener{ delete(monday17); return@setOnLongClickListener(true) }
        monday18.setOnLongClickListener{ delete(monday18); return@setOnLongClickListener(true) }
        monday19.setOnLongClickListener{ delete(monday19); return@setOnLongClickListener(true) }
        monday20.setOnLongClickListener{ delete(monday20); return@setOnLongClickListener(true) }
        tuesday9.setOnLongClickListener{ delete(tuesday9); return@setOnLongClickListener(true) }
        tuesday10.setOnLongClickListener{ delete(tuesday10); return@setOnLongClickListener(true) }
        tuesday11.setOnLongClickListener{ delete(tuesday11); return@setOnLongClickListener(true) }
        tuesday12.setOnLongClickListener{ delete(tuesday12); return@setOnLongClickListener(true) }
        tuesday13.setOnLongClickListener{ delete(tuesday13); return@setOnLongClickListener(true) }
        tuesday14.setOnLongClickListener{ delete(tuesday14); return@setOnLongClickListener(true) }
        tuesday15.setOnLongClickListener{ delete(tuesday15); return@setOnLongClickListener(true) }
        tuesday16.setOnLongClickListener{ delete(tuesday16); return@setOnLongClickListener(true) }
        tuesday17.setOnLongClickListener{ delete(tuesday17); return@setOnLongClickListener(true) }
        tuesday18.setOnLongClickListener{ delete(tuesday18); return@setOnLongClickListener(true) }
        tuesday19.setOnLongClickListener{ delete(tuesday19); return@setOnLongClickListener(true) }
        tuesday20.setOnLongClickListener{ delete(tuesday20); return@setOnLongClickListener(true) }
        wednesday9.setOnLongClickListener{ delete(wednesday9); return@setOnLongClickListener(true) }
        wednesday10.setOnLongClickListener{ delete(wednesday10); return@setOnLongClickListener(true) }
        wednesday11.setOnLongClickListener{ delete(wednesday11); return@setOnLongClickListener(true) }
        wednesday12.setOnLongClickListener{ delete(wednesday12); return@setOnLongClickListener(true) }
        wednesday13.setOnLongClickListener{ delete(wednesday13); return@setOnLongClickListener(true) }
        wednesday14.setOnLongClickListener{ delete(wednesday14); return@setOnLongClickListener(true) }
        wednesday15.setOnLongClickListener{ delete(wednesday15); return@setOnLongClickListener(true) }
        wednesday16.setOnLongClickListener{ delete(wednesday16); return@setOnLongClickListener(true) }
        wednesday17.setOnLongClickListener{ delete(wednesday17); return@setOnLongClickListener(true) }
        wednesday18.setOnLongClickListener{ delete(wednesday18); return@setOnLongClickListener(true) }
        wednesday19.setOnLongClickListener{ delete(wednesday19); return@setOnLongClickListener(true) }
        wednesday20.setOnLongClickListener{ delete(wednesday20); return@setOnLongClickListener(true) }
        thursday9.setOnLongClickListener{ delete(thursday9); return@setOnLongClickListener(true) }
        thursday10.setOnLongClickListener{ delete(thursday10); return@setOnLongClickListener(true) }
        thursday11.setOnLongClickListener{ delete(thursday11); return@setOnLongClickListener(true) }
        thursday12.setOnLongClickListener{ delete(thursday12); return@setOnLongClickListener(true) }
        thursday13.setOnLongClickListener{ delete(thursday13); return@setOnLongClickListener(true) }
        thursday14.setOnLongClickListener{ delete(thursday14); return@setOnLongClickListener(true) }
        thursday15.setOnLongClickListener{ delete(thursday15); return@setOnLongClickListener(true) }
        thursday16.setOnLongClickListener{ delete(thursday16); return@setOnLongClickListener(true) }
        thursday17.setOnLongClickListener{ delete(thursday17); return@setOnLongClickListener(true) }
        thursday18.setOnLongClickListener{ delete(thursday18); return@setOnLongClickListener(true) }
        thursday19.setOnLongClickListener{ delete(thursday19); return@setOnLongClickListener(true) }
        thursday20.setOnLongClickListener{ delete(thursday20); return@setOnLongClickListener(true) }
        friday9.setOnLongClickListener{ delete(friday9); return@setOnLongClickListener(true) }
        friday10.setOnLongClickListener{ delete(friday10); return@setOnLongClickListener(true) }
        friday11.setOnLongClickListener{ delete(friday11); return@setOnLongClickListener(true) }
        friday12.setOnLongClickListener{ delete(friday12); return@setOnLongClickListener(true) }
        friday13.setOnLongClickListener{ delete(friday13); return@setOnLongClickListener(true) }
        friday14.setOnLongClickListener{ delete(friday14); return@setOnLongClickListener(true) }
        friday15.setOnLongClickListener{ delete(friday15); return@setOnLongClickListener(true) }
        friday16.setOnLongClickListener{ delete(friday16); return@setOnLongClickListener(true) }
        friday17.setOnLongClickListener{ delete(friday17); return@setOnLongClickListener(true) }
        friday18.setOnLongClickListener{ delete(friday18); return@setOnLongClickListener(true) }
        friday19.setOnLongClickListener{ delete(friday19); return@setOnLongClickListener(true) }
        friday20.setOnLongClickListener{ delete(friday20); return@setOnLongClickListener(true) }

        resetTextView()
        timesplit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        var mInflater = menuInflater
        mInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> gs = "1-1"
            R.id.menu2 -> gs = "1-2"
            R.id.menu3 -> gs = "4-1"
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

    fun delete(weekID : TextView){
        val dlg = kr.ac.kumoh.s20170419.mygradecalculator.Dialog(this)
        dlg.dialog(weekID.text.toString(), "삭제")
        dlg.setOnClickedListener(object :
            kr.ac.kumoh.s20170419.mygradecalculator.Dialog.ButtonClickListener {
            override fun onClicked(data: Int) {
                if (data == 1) {
                    deleteSchedule(weekID)
                    finish()
                    startActivity(getIntent())
                } else if (data == 0)
                    Toast.makeText(getApplication(), "취소당", Toast.LENGTH_LONG).show()
            }
        })
    }
    fun deleteSchedule(weekID : TextView){
        Thread(Runnable {
            dbmodel.deleteDB(weekID.text.toString())
        }).start()
        resetTextView()
        timesplit()
    }
}