package kr.ac.kumoh.s20170419.mygradecalculator

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_login.*
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var view: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        view = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(view.root)

        val user = getSharedPreferences("user", Context.MODE_PRIVATE)
        val editor = user.edit()
        if (user.getString("college", "") != "") {
            val intent = Intent(this, MainActivity::class.java)
            val gs = "${user.getString("grade", "")}-${user.getString("semester", "")}"
            intent.putExtra("gs", gs)
            finishAffinity()
            startActivity(intent)
        }

        val selectCollegeData: Array<String> = resources.getStringArray(R.array.college_select)
        val selectCollegeDataApter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, selectCollegeData)
        view.collegeSelect.adapter = selectCollegeDataApter

        val selectMajorData: Array<String> = resources.getStringArray(R.array.major_select)
        val selectMajorDataApter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, selectMajorData)
        view.majorSelect.adapter = selectMajorDataApter

        val selectGradeData: Array<String> = resources.getStringArray(R.array.grade_select)
        val selectGradeDataApter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, selectGradeData)
        view.gradeSelect.adapter = selectGradeDataApter

        val selectSemesterData: Array<String> = resources.getStringArray(R.array.semester_select)
        val selectSemesterDataApter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, selectSemesterData)
        view.semesterSelect.adapter = selectSemesterDataApter

        view.loginButton.setOnClickListener {
            editor.putString("college", college_select.selectedItem.toString())
            editor.putString("major", major_select.selectedItem.toString())
            editor.putString("grade", grade_select.selectedItem.toString())
            editor.putString("semester", semester_select.selectedItem.toString())
            editor.apply()
            finishAffinity()
            val intent = Intent(this, MainActivity::class.java)
            val gs = "${user.getString("grade", "")}-${user.getString("semester", "")}"
            intent.putExtra("gs", gs)
            finishAffinity()
            startActivity(intent)
        }
    }
}
