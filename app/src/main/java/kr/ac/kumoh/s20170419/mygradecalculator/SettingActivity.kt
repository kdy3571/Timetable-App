package kr.ac.kumoh.s20170419.mygradecalculator

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_login.*
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var setting: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setting = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(setting.root)

        val collegeData: Array<String> = resources.getStringArray(R.array.college_select)
        val collegeDataAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, collegeData)
        setting.collegeSelect.adapter = collegeDataAdapter

        val majorData: Array<String> = resources.getStringArray(R.array.major_select)
        val majorDataAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, majorData)
        setting.majorSelect.adapter = majorDataAdapter

        val gradeData: Array<String> = resources.getStringArray(R.array.grade_select)
        val gradeDataAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, gradeData)
        setting.gradeSelect.adapter = gradeDataAdapter

        val semesterData: Array<String> = resources.getStringArray(R.array.semester_select)
        val semesterDataAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, semesterData)
        setting.semesterSelect.adapter = semesterDataAdapter

        setting.changeButton.setOnClickListener {
            val user = getSharedPreferences("user", Context.MODE_PRIVATE)
            val editor = user.edit()
            editor.putString("college", college_select.selectedItem.toString())
            editor.putString("major", major_select.selectedItem.toString())
            editor.putString("grade", grade_select.selectedItem.toString())
            editor.putString("semester", semester_select.selectedItem.toString())
            editor.apply()
            finishAffinity()
            val intent = Intent(this, MainActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }
    }
}