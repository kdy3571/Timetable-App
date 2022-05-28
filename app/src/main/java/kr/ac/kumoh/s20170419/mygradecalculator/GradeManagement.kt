package kr.ac.kumoh.s20170419.mygradecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityGradeManagementBinding

class GradeManagement : AppCompatActivity() {
    private lateinit var view: ActivityGradeManagementBinding
    private lateinit var dbmodel: InnerDBViewmodel
    private var subjectList: List<weekstate> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityGradeManagementBinding.inflate(layoutInflater)
        dbmodel = ViewModelProvider(this@GradeManagement).get(InnerDBViewmodel::class.java)
        setContentView(view.root)

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

        val gs = "4-1"
        view.LoadTimetable.setOnClickListener {
            getAll(gs)
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

    private fun getAll(gs: String) {
        Thread(Runnable {
            subjectList = dbmodel.getall(gs)
        }).start()
    }
}