package kr.ac.kumoh.s20170419.mygradecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import auto_schedule
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityTimetableGenerationBinding

class TimetableGeneration : AppCompatActivity() {
    lateinit var gbinding: ActivityTimetableGenerationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gbinding = ActivityTimetableGenerationBinding.inflate(layoutInflater)
        setContentView(gbinding.root)

        var credit: Int = gbinding.creditInput.text.toString().toInt()
        var s_subject: ArrayList<String>
        val e_subject: ArrayList<String>
        var rest = ArrayList<Int>()
        var ge: Int = 0

        gbinding.button1.setOnClickListener {
            //과목 리스트 불러오기
            //불러온 과목들 s_subject에 추가
        }

        gbinding.button2.setOnClickListener {
            //과목 리스트 불러오기
            //불러온 과목들 e_subject에 추가
        }

//        var listener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                when (buttonView.id) {
//                    R.id.check1 -> rest.add(0)
//                    R.id.check2 -> rest.add(1)
//                    R.id.check3 -> rest.add(2)
//                    R.id.check4 -> rest.add(3)
//                    R.id.check5 -> rest.add(4)
//                }
//            }
//            else {
//                when (buttonView.id) {
//                    R.id.check1 -> rest.removeAt(0)
//                    R.id.check2 -> rest.removeAt(1)
//                    R.id.check3 -> rest.removeAt(2)
//                    R.id.check4 -> rest.removeAt(3)
//                    R.id.check5 -> rest.removeAt(4)
//                }
//            }
//
//            gbinding.check1.setOnCheckedChangeListener(listener)
//            gbinding.check2.setOnCheckedChangeListener(listener)
//            gbinding.check3.setOnCheckedChangeListener(listener)
//            gbinding.check4.setOnCheckedChangeListener(listener)
//            gbinding.check5.setOnCheckedChangeListener(listener)

            gbinding.create.setOnClickListener {
                auto_schedule(credit, s_subject, e_subject, rest, ge)
            }
        }
    }
}
