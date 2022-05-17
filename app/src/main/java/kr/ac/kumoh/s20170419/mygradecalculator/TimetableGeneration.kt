package kr.ac.kumoh.s20170419.mygradecalculator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityTimetableGenerationBinding
import java.util.*
import kotlin.collections.ArrayList


open class TimetableGeneration : AppCompatActivity() {
    private val model: ViewModel by viewModels()
    lateinit var gbinding: ActivityTimetableGenerationBinding

    var credit = 21
    var selectSubject =  ArrayList<String?>()
    var exceptSubject = ArrayList<String?>()
    var rest = ArrayList<Int>()
    var ge = 3
    var timeTable = Array(5) { arrayOfNulls<String?>(12) }
    var subjectInfo = ArrayList<ViewModel.Subject>()
    var slist = Array(3) { ArrayList<ViewModel.Subject>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gbinding = ActivityTimetableGenerationBinding.inflate(layoutInflater)
        setContentView(gbinding.root)
        model.requestList("4", "1", null)

        gbinding.creditInput.setOnClickListener {
            credit = gbinding.creditInput.text.toString().toInt()
            Log.d("credit", "credit: $credit")
        }

        gbinding.geInput.setOnClickListener {
            ge = gbinding.geInput.text.toString().toInt()
            Log.d("ge", "ge: $ge")
        }

        gbinding.button1.setOnClickListener {
            val intent = Intent(this, SubjectList::class.java)
            startActivity(intent)
            selectSubject.add(intent.getStringExtra("code"))
            //불러온 과목들 s_subject에 추가
        }

        gbinding.button2.setOnClickListener {
            val intent = Intent(this, SubjectList::class.java)
            startActivity(intent)
            exceptSubject.add(intent.getStringExtra("code"))
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
            Log.d("제외과목", exceptSubject.toString())
            Log.d("선택과목", selectSubject.toString())
            Log.d("timetable전", timeTable.contentDeepToString())
            autoSchedule()
            Log.d("timetable후", timeTable.contentDeepToString())
            Log.d("과목정보", subjectInfo.toString())
        }
    }


    private fun autoSchedule(): Int {
        slist[0] = model.getR_subject() // slist[0]: 필수, slist[1]: 전공선택, slist[2]: 교양선택

        for(i in exceptSubject){
            slist[0].removeIf {
                it.code == i
                val name = it.code
                slist[0].removeIf {
                    it.name == name
                }
            }
        }

        for(i in selectSubject){
            slist[0].removeIf {
                it.code == i
                val name = it.code
                slist[0].removeIf {
                    it.name == name
                }
            }
        }

        for(i in slist[0]) {
            if(i.division == "선택") {
                if(i.subject == "전공") {
                    slist[1].add(i)
                }
                else if (i.subject == "교양") {
                    slist[2].add(i)
                }
            }
        }
        slist[0].removeIf { it.division == "선택" }

        while (slist[0].isNotEmpty()) // 배열에서 필수과목(division) 불러오기
            subjectAdd(slist[0])

        if (rest.isNotEmpty()) { // 공강일 존재
            for (i in rest) {
                for (j in 0..11)
                    if (timeTable[i][j] != null) {
                        //종료, 공강일 불가능 반환
                        Toast.makeText(this@TimetableGeneration, "다른 공강일을 선택해주세요.", Toast.LENGTH_SHORT)
                            .show()
                        return -1
                    }
                for (j in 0..11)
                    timeTable[i][j] = "Rest"
            }
        }

        Log.d("list 전", slist[2].toString())
        while (ge != 0) { // 교양을 넣기를 희망한다면
            if (subjectAdd(slist[2]) == 1)
                ge -= 1
        }

        while (credit != 0) { // 학점이 0이 될때까지 채워주기
            if (slist[1].isNotEmpty())
                subjectAdd(slist[1])
            else {
                if (slist[2].isNotEmpty())
                    subjectAdd(slist[2])
                else {
                    // 불가능
                    break
                }
            }
        }
        return 1
    }

    @RequiresApi(Build.VERSION_CODES.N) // 24버전
    fun subjectAdd(subjectList: ArrayList<ViewModel.Subject>): Int {
        val random = Random()
        val num = random.nextInt(subjectList.size)
        if (credit - subjectList[num].credit.toInt() > 0) {// list로 불러온 학점이 남은 학점을 초과하는지 확인
            val time = subjectList[num].time.split(", ")

            for (t in time) {
                val temp = t.split(":")
                if(timeTable[temp[0].toInt()][temp[1].toInt()] == "Rest") {
                    subjectList.removeAt(num)
                    return 0
                }
            }

            for (t in time) {
                val temp = t.split(":")
                when (timeTable[temp[0].toInt()][temp[1].toInt()]) { // timeTable의 공간 확인
                    null -> {
                        timeTable[temp[0].toInt()][temp[1].toInt()] = subjectList[num].name
                    }
                    else -> {
                        subjectList.removeAt(num)
                        return 0
                    }
                }
            }
            subjectInfo.add(subjectList[num])
        }
        else {
            subjectList.removeAt(num)
            return 0
        }
        subjectList.removeIf { it.name == subjectList[num].name } // 과목 추가에 성공했을 경우 과목명이 같으면 모두 삭제(분반 제거)
        return 1
    }
}