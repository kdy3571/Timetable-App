package kr.ac.kumoh.s20170419.mygradecalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityLoginBinding
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityMainBinding

class loginActivity : AppCompatActivity() {

    private lateinit var view: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        view = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(view.root)

        val select_college_data:Array<String> = resources.getStringArray(R.array.college_select)
        val select_college_data_apter = ArrayAdapter(this, android.R.layout.simple_list_item_1, select_college_data)
        view.collegeSelect.adapter = select_college_data_apter

        val select_major_data:Array<String> = resources.getStringArray(R.array.major_select)
        val select_major_data_apter = ArrayAdapter(this, android.R.layout.simple_list_item_1, select_major_data)
        view.maiorSelect.adapter = select_major_data_apter

        val select_grade_data:Array<String> = resources.getStringArray(R.array.grade_select)
        val select_grade_data_apter = ArrayAdapter(this, android.R.layout.simple_list_item_1, select_grade_data)
        view.gradeSelect.adapter = select_grade_data_apter

        val select_semester_data:Array<String> = resources.getStringArray(R.array.semester_select)
        val select_semester_data_apter = ArrayAdapter(this, android.R.layout.simple_list_item_1, select_semester_data)
        view.semesterSelect.adapter = select_semester_data_apter

        view.loginButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


}