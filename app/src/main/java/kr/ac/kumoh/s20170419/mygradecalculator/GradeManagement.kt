package kr.ac.kumoh.s20170419.mygradecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityGradeManagementBinding

class GradeManagement : AppCompatActivity() {
    private lateinit var view: ActivityGradeManagementBinding
    private lateinit var dbmodel: InnerDBViewmodel
    private var subjectList: List<weekstate> = arrayListOf()
    private var db: List<gpstate> = arrayListOf()
    private var gs = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityGradeManagementBinding.inflate(layoutInflater)
        dbmodel = ViewModelProvider(this@GradeManagement).get(InnerDBViewmodel::class.java)
        setContentView(view.root)

        if (intent.hasExtra("gs")) {
            gs = intent.getStringExtra("gs")!!
        }
        getInfo(gs)  // 해당 gs의 과목정보가 있으면 받아옴
        loadInfo(db) // 해당 gs의 과목정보가 있으면 불러옴

        val subject_grade_data: Array<String> = resources.getStringArray(R.array.subject_grade)
        val subject_grade_data_apter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, subject_grade_data)
        view.gp1.adapter = subject_grade_data_apter
        view.gp2.adapter = subject_grade_data_apter
        view.gp3.adapter = subject_grade_data_apter
        view.gp4.adapter = subject_grade_data_apter
        view.gp5.adapter = subject_grade_data_apter
        view.gp6.adapter = subject_grade_data_apter
        view.gp7.adapter = subject_grade_data_apter
        view.gp8.adapter = subject_grade_data_apter
        view.gp9.adapter = subject_grade_data_apter
        view.gp10.adapter = subject_grade_data_apter
        view.gp11.adapter = subject_grade_data_apter
        view.gp12.adapter = subject_grade_data_apter

        view.LoadTimetable.setOnClickListener {
            getAll(gs) // 해당 학년 불러오기
            for (i in 1..subjectList.size) {
                val resID = resources.getIdentifier("subject$i", "id", packageName)
                val subjectID = findViewById<EditText>(resID)
                val resID2 = resources.getIdentifier("credit$i", "id", packageName)
                val creditID = findViewById<EditText>(resID2)
                subjectID.setText(subjectList[i - 1].name)
                creditID.setText(subjectList[i - 1].credit)
            }
            deleteDB(gs)
            connect(subjectList)
        }
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
        getInfo(gs)
        loadInfo(db)

        return super.onOptionsItemSelected(item)
    }

    private fun loadInfo(db: List<gpstate>) {
        if(db.isNotEmpty()) {
            for (i in 1..db.size) {
                val resID = resources.getIdentifier("subject$i", "id", packageName)
                val subjectID = findViewById<EditText>(resID)
                val resID2 = resources.getIdentifier("credit$i", "id", packageName)
                val creditID = findViewById<EditText>(resID2)
                val resID3 = resources.getIdentifier("gp$i", "id", packageName)
                val gpID = findViewById<Spinner>(resID3)
                subjectID.setText(db[i - 1].name)
                creditID.setText(db[i - 1].credit)
                when (db[i - 1].gp) {
                    "A+" -> gpID.setSelection(0)
                    "A" -> gpID.setSelection(1)
                    "B+" -> gpID.setSelection(2)
                    "B" -> gpID.setSelection(3)
                    "C+" -> gpID.setSelection(4)
                    "C" -> gpID.setSelection(5)
                    "D+" -> gpID.setSelection(6)
                    "D" -> gpID.setSelection(7)
                    "F" -> gpID.setSelection(8)
                }
            }
        }
        else
            clear()
    }

    private fun clear() {
        for (i in 1..12) {
            val resID = resources.getIdentifier("subject$i", "id", packageName)
            val subjectID = findViewById<EditText>(resID)
            val resID2 = resources.getIdentifier("credit$i", "id", packageName)
            val creditID = findViewById<EditText>(resID2)
            val resID3 = resources.getIdentifier("gp$i", "id", packageName)
            val gpID = findViewById<Spinner>(resID3)
            subjectID.setText("")
            creditID.setText("0")
            gpID.setSelection(0)
        }
    }

    private fun getAll(gs: String) { // 시간표에서 과목정보 불러오기
        Thread(Runnable {
            subjectList = dbmodel.getall(gs)
        }).start()
        Thread.sleep(100L)
    }

    private fun getInfo(gs: String) { // 저장된 과목정보 불러오기
        Thread(Runnable {
            db = dbmodel.getInfo(gs)
        }).start()
        Thread.sleep(100L)
    }

    fun connect(db: List<weekstate>){   // 불러온 과목정보를 db에 저장
        Thread(Runnable {
            for(element in db){
                dbmodel.connect(element)
            }
        }).start()
        Thread.sleep(100L)
    }

    fun deleteDB(gs: String) {
        Thread(Runnable {
            dbmodel.delInfo(gs)
        }).start()
        Thread.sleep(100L)
    }
}
