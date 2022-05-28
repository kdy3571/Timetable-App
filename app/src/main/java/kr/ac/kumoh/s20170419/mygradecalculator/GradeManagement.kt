package kr.ac.kumoh.s20170419.mygradecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityGradeManagementBinding

class GradeManagement : AppCompatActivity() {
    private lateinit var view: ActivityGradeManagementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityGradeManagementBinding.inflate(layoutInflater)
        setContentView(view.root)

        val subject_grade_data:Array<String> = resources.getStringArray(R.array.subject_grade)
        val subject_grade_data_apter = ArrayAdapter(this, android.R.layout.simple_list_item_1, subject_grade_data)
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
    }
}