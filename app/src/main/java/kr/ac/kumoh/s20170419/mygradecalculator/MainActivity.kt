package kr.ac.kumoh.s20170419.mygradecalculator

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.listdesign.*
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityMainBinding

open class MainActivity : AppCompatActivity() {
    private lateinit var view: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        setContentView(view.root)

        view.button2.setOnClickListener {
            val intent = Intent(this, TimetableGeneration::class.java)
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
        view.monday09.text = intent.getStringExtra("name")
    }
    private val monday = ArrayList<String>(14)
    val tuesday = ArrayList<TextView>()
    val wednesday = ArrayList<TextView>()
    val thursday = ArrayList<TextView>()
    val friday = ArrayList<TextView>()
    fun cleararray(){
        monday[0] = findViewById(R.id.monday09)
    }
    fun setting(name: String, room: String, code: String, time: String) {
        //cleararray()
        view.monday09.text = name
        view.monday09.setBackgroundColor(Color.GREEN)
    }
}
