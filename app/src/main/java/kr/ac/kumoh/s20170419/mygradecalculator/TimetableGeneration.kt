package kr.ac.kumoh.s20170419.mygradecalculator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityTimetableGenerationBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log10
import kotlin.math.pow

class TimetableGeneration : AppCompatActivity() {
    private val model: ViewModel by viewModels()
    lateinit var gbinding: ActivityTimetableGenerationBinding

    var credit = 21
    var s_subject =  ArrayList<String>()
    var e_subject = ArrayList<String>()
    var rest = ArrayList<Int>()
    var ge = 3
    var timetable = Array(5) { arrayOfNulls<ViewModel.Subject?>(12) }
    lateinit var em_list: ArrayList<ViewModel.Subject>
    lateinit var cs_list: ArrayList<ViewModel.Subject>
    lateinit var ge_list: ArrayList<ViewModel.Subject>
    lateinit var list: ArrayList<ViewModel.Subject>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gbinding = ActivityTimetableGenerationBinding.inflate(layoutInflater)
        setContentView(gbinding.root)

        list = model.requestList("4", "1", "")
        ge_list = model.requestList("4", "1", "교양선택")
//
//        for(l in list){
//            if(l.geselection_id != null) {
//                ge_list.add(l)
//            }
//            else if(l.majorselection_id != null) {
//                em_list.add(l)
//            }
//            else if(l.requiredsubject_id != null) {
//                cs_list.add(l)
//            }
//        }
//        list.clear()
//
//        Log.d("ge_list", ge_list.toString())
//        Log.d("cs_list", em_list.toString())
//        Log.d("em_list", cs_list.toString())


        gbinding.creditInput.setOnClickListener {
            credit = gbinding.creditInput.text.toString().toInt()
            Log.d("credit", "credit: "+ credit)
        }

        gbinding.geInput.setOnClickListener {
            ge = gbinding.geInput.text.toString().toInt()
            Log.d("ge", "ge: "+ ge)
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
            auto_schedule()
            Log.d("timetable", Arrays.deepToString(timetable))
        }
    }


    private fun auto_schedule() {

//    while (cs_list.isNotEmpty()) // 배열에서 필수과목(division) 불러오기 4-1학기가 없을때까지
//        subject_add(cs_list, slist, credit)
//
//    if (rest.isNotEmpty()) { // 공강일 존재
//        for(i in rest) {
//            for (j in 0..11)
//                if (slist[i][j] == null)
//                //종료, 공강일 불가능 반환
//            slist[i] = arrayOf("Rest")
//        }
//    }
        Log.d("ge_list 전", ge_list.toString())
        while (ge != 0) { // 교양을 넣기를 희망한다면
                if (subject_add(ge_list) == 1)
                    ge -= 1
        }
        Log.d("ge", ge.toString())
        Log.d("ge_list 후", ge_list.toString())

//    while (credit[0] != 0) { // 학점이 0이 될때까지 채워주기
//        if (em_list.isNotEmpty())
//            subject_add(em_list, slist, credit)
//        else
//            if (ge_list.isNotEmpty())
//                subject_add(ge_list, slist, credit)
//            else
//                break
//    }
    }


    private fun subject_add(list: ArrayList<ViewModel.Subject>): Int {
        val random = Random()
        val num = random.nextInt(list.size)

        if (credit - list[num].credit.toInt() > 0) {// list로 불러온 과목의 학점 체크
            val time = list[num].time.split(", ")
            Log.d("time", time.toString())
            Log.d("code", list[num].code)
            for (t in time) {
                val n = log10(t.toDouble()).toInt().toDouble()
                val temp = timetable[(t.toInt() / 10.0.pow(n)).toInt()][(t.toInt() % 10.0.pow(n)).toInt()]
                when (temp) {
                    null -> timetable[((t.toInt() / 10.0.pow(n)).toInt())][(t.toInt() % 10.0.pow(n)).toInt()] = list[num]
                    else -> {
                        list.removeAt(num)
                        return 0
                    }
                }
            }
        }
        else {
            list.removeAt(num)
            return 0
        }
        list.removeAt(num)
        return 1
    }
}