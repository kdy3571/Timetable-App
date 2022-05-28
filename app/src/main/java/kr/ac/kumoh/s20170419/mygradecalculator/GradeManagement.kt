package kr.ac.kumoh.s20170419.mygradecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityGradeManagementBinding

class GradeManagement : AppCompatActivity() {
    private lateinit var view: ActivityGradeManagementBinding
    private lateinit var dbmodel: InnerDBViewmodel
    private var subjectList: List<weekstate> = arrayListOf()
    private var gs = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityGradeManagementBinding.inflate(layoutInflater)
        dbmodel = ViewModelProvider(this@GradeManagement).get(InnerDBViewmodel::class.java)
        setContentView(view.root)

        if (intent.hasExtra("gs")) {
            gs = intent.getStringExtra("gs")!!
        }

        val subject_grade_data: Array<String> = resources.getStringArray(R.array.subject_grade)
        val subject_grade_data_apter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, subject_grade_data)
        view.grade1.adapter = subject_grade_data_apter
        view.grade2.adapter = subject_grade_data_apter
        view.grade3.adapter = subject_grade_data_apter
        view.grade4.adapter = subject_grade_data_apter
        view.grade5.adapter = subject_grade_data_apter
        view.grade6.adapter = subject_grade_data_apter
        view.grade7.adapter = subject_grade_data_apter
        view.grade8.adapter = subject_grade_data_apter
        view.grade9.adapter = subject_grade_data_apter
        view.grade10.adapter = subject_grade_data_apter
        view.grade11.adapter = subject_grade_data_apter
        view.grade12.adapter = subject_grade_data_apter


        getAll(gs)
        view.LoadTimetable.setOnClickListener {
            for (i in 1..subjectList.size) {
                val resID = resources.getIdentifier("subject$i", "id", packageName)
                val subjectID = findViewById<EditText>(resID)
                val resID2 = resources.getIdentifier("credit$i", "id", packageName)
                val creditID = findViewById<EditText>(resID2)
                subjectID.setText(subjectList[i - 1].name)
                creditID.setText(subjectList[i - 1].credit)
            }
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
        getAll(gs)

        return super.onOptionsItemSelected(item)
    }

    private fun getAll(gs: String) {
        Thread(Runnable {
            subjectList = dbmodel.getall(gs)
        }).start()
    }
}
