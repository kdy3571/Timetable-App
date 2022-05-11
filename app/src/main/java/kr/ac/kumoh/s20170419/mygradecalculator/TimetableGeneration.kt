package kr.ac.kumoh.s20170419.mygradecalculator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import auto_schedule
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityTimetableGenerationBinding
import java.util.*
import kotlin.collections.ArrayList

class TimetableGeneration : AppCompatActivity() {
    private val model: ViewModel by viewModels()
    lateinit var gbinding: ActivityTimetableGenerationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gbinding = ActivityTimetableGenerationBinding.inflate(layoutInflater)
        setContentView(gbinding.root)

        var credit = arrayListOf(0)
        var s_subject =  ArrayList<String>()
        var e_subject = ArrayList<String>()
        var rest = ArrayList<Int>()
        var ge = arrayListOf(0)
        var timetable = Array(5) { arrayOfNulls<String?>(12) }

        gbinding.creditInput.setOnClickListener {
            credit[0] = gbinding.creditInput.text.toString().toInt()
            Log.d("credit", "credit: "+ credit[0])
        }

        gbinding.geInput.setOnClickListener {
            ge[0] = gbinding.geInput.text.toString().toInt()
            Log.d("ge", "ge: "+ ge[0])
        }

        gbinding.button1.setOnClickListener {
            //불러온 과목들 s_subject에 추가
        }

        gbinding.button2.setOnClickListener {
            //과목 리스트 불러오기
            //불러온 과목들 e_subject에 추가
        }

        var listener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                when (buttonView.id) {
                    R.id.check1 -> rest.add(0)
                    R.id.check2 -> rest.add(1)
                    R.id.check3 -> rest.add(2)
                    R.id.check4 -> rest.add(3)
                    R.id.check5 -> rest.add(4)
                }
            } else {
                when (buttonView.id) {
                    R.id.check1 -> rest.removeAt(0)
                    R.id.check2 -> rest.removeAt(1)
                    R.id.check3 -> rest.removeAt(2)
                    R.id.check4 -> rest.removeAt(3)
                    R.id.check5 -> rest.removeAt(4)
                }
            }
        }

        gbinding.check1.setOnCheckedChangeListener(listener)
        gbinding.check2.setOnCheckedChangeListener(listener)
        gbinding.check3.setOnCheckedChangeListener(listener)
        gbinding.check4.setOnCheckedChangeListener(listener)
        gbinding.check5.setOnCheckedChangeListener(listener)

        gbinding.create.setOnClickListener {
            auto_schedule(timetable, credit, s_subject, e_subject, rest, ge, model)
            timetable[0][1] = "1"
            Log.d("timetable", Arrays.deepToString(timetable))
        }
    }
}