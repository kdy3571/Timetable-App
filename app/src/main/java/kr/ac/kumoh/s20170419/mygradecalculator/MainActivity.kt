package kr.ac.kumoh.s20170419.mygradecalculator
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_auto_table.*
import kotlinx.android.synthetic.main.listdesign.*
import kotlinx.android.synthetic.main.listdesign.view.*
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityMainBinding
import kotlin.collections.ArrayList

private var weekdata = Array(5) { kotlin.arrayOfNulls<String?>(11) }
private var autoData = ArrayList<ViewModel.Subject>()

open class MainActivity : AppCompatActivity() {
    private lateinit var view: ActivityMainBinding
    private val model: ViewModel by viewModels()
    var alldb : List<weekstate> = arrayListOf()
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
            finish()
            startActivity(intent)
        }

        view.button.setOnClickListener {
            val intent = Intent(this, TimetableAdd::class.java)
            startActivity(intent)
        }

        view.button3.setOnClickListener {
            val intent = Intent(this, GradeManagement::class.java)
            intent.putExtra("gs", gs)
            startActivity(intent)
        }

        view.button4.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        monday9.setOnClickListener{ delete(monday9)}
        monday10.setOnClickListener{ delete(monday10)}
        monday11.setOnClickListener{ delete(monday11)}
        monday12.setOnClickListener{ delete(monday12)}
        monday13.setOnClickListener{ delete(monday13)}
        monday14.setOnClickListener{ delete(monday14)}
        monday15.setOnClickListener{ delete(monday15)}
        monday16.setOnClickListener{ delete(monday16)}
        monday17.setOnClickListener{ delete(monday17)}
        monday18.setOnClickListener{ delete(monday18)}
        monday19.setOnClickListener{ delete(monday19)}
        monday20.setOnClickListener{ delete(monday20)}
        tuesday9.setOnClickListener{ delete(tuesday9)}
        tuesday10.setOnClickListener{ delete(tuesday10)}
        tuesday11.setOnClickListener{ delete(tuesday11)}
        tuesday12.setOnClickListener{ delete(tuesday12)}
        tuesday13.setOnClickListener{ delete(tuesday13)}
        tuesday14.setOnClickListener{ delete(tuesday14)}
        tuesday15.setOnClickListener{ delete(tuesday15)}
        tuesday16.setOnClickListener{ delete(tuesday16)}
        tuesday17.setOnClickListener{ delete(tuesday17)}
        tuesday18.setOnClickListener{ delete(tuesday18)}
        tuesday19.setOnClickListener{ delete(tuesday19)}
        tuesday20.setOnClickListener{ delete(tuesday20)}
        wednesday9.setOnClickListener{ delete(wednesday9)}
        wednesday10.setOnClickListener{ delete(wednesday10)}
        wednesday11.setOnClickListener{ delete(wednesday11)}
        wednesday12.setOnClickListener{ delete(wednesday12)}
        wednesday13.setOnClickListener{ delete(wednesday13)}
        wednesday14.setOnClickListener{ delete(wednesday14)}
        wednesday15.setOnClickListener{ delete(wednesday15)}
        wednesday16.setOnClickListener{ delete(wednesday16)}
        wednesday17.setOnClickListener{ delete(wednesday17)}
        wednesday18.setOnClickListener{ delete(wednesday18)}
        wednesday19.setOnClickListener{ delete(wednesday19)}
        wednesday20.setOnClickListener{ delete(wednesday20)}
        thursday9.setOnClickListener{ delete(thursday9)}
        thursday10.setOnClickListener{ delete(thursday10)}
        thursday11.setOnClickListener{ delete(thursday11)}
        thursday12.setOnClickListener{ delete(thursday12)}
        thursday13.setOnClickListener{ delete(thursday13)}
        thursday14.setOnClickListener{ delete(thursday14)}
        thursday15.setOnClickListener{ delete(thursday15)}
        thursday16.setOnClickListener{ delete(thursday16)}
        thursday17.setOnClickListener{ delete(thursday17)}
        thursday18.setOnClickListener{ delete(thursday18)}
        thursday19.setOnClickListener{ delete(thursday19)}
        thursday20.setOnClickListener{ delete(thursday20)}
        friday9.setOnClickListener{ delete(friday9)}
        friday10.setOnClickListener{ delete(friday10)}
        friday11.setOnClickListener{ delete(friday11)}
        friday12.setOnClickListener{ delete(friday12)}
        friday13.setOnClickListener{ delete(friday13)}
        friday14.setOnClickListener{ delete(friday14)}
        friday15.setOnClickListener{ delete(friday15)}
        friday16.setOnClickListener{ delete(friday16)}
        friday17.setOnClickListener{ delete(friday17)}
        friday18.setOnClickListener{ delete(friday18)}
        friday19.setOnClickListener{ delete(friday19)}
        friday20.setOnClickListener{ delete(friday20)}

        timesplit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        var mInflater = menuInflater
        mInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu11 -> gs = "1-1"
            R.id.menu12 -> gs = "1-2"
            R.id.menu21 -> gs = "2-1"
            R.id.menu22 -> gs = "2-2"
            R.id.menu31 -> gs = "3-1"
            R.id.menu32 -> gs = "3-2"
            R.id.menu41 -> gs = "4-1"
            R.id.menu42 -> gs = "4-2"
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
            timesplit()
        }
        if (intent.hasExtra("manual")) {
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
        resetTextView()
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

    fun DBgetall(){
        Thread(Runnable {
            alldb = dbmodel.getall(gs)
        }).start()
        Thread.sleep(100L)
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
        DBgetall()
        if (weekID.text != ""){
            for (i in 0 until alldb.size){
                if( weekID.text == alldb[i].name ){
                    val dlg = kr.ac.kumoh.s20170419.mygradecalculator.Dialog(this)
                    val dlg2 = kr.ac.kumoh.s20170419.mygradecalculator.ListDialog(this)
                    dlg2.dialog(alldb[i], "리스트")
                    dlg2.setOnClickedListener(object : ListDialog.ButtonClickListener {
                        override fun onClicked(data: String) {
                            if (data == "리스트"){
                                dlg.dialog(weekID.text.toString(), "삭제")
                                dlg.setOnClickedListener(object : Dialog.ButtonClickListener{
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
                        }
                    })
                }
            }
        }
    }

    fun deleteSchedule(weekID : TextView){
        Thread(Runnable {
            dbmodel.deleteDB(weekID.text.toString(), gs)
        }).start()
        Thread.sleep(100L)
        resetTextView()
        timesplit()
    }
}