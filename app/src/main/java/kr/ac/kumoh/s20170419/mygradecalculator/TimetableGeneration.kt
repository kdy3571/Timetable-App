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

    companion object {
        var credit = 0
        var ge = 0
        var rest = ArrayList<Int>()
        var selectSubject =  ArrayList<ViewModel.Subject>()
        var exceptSubject = ArrayList<ViewModel.Subject>()
    }
    var slist = Array(3) { ArrayList<ViewModel.Subject>() }
    var subjectInfo = ArrayList<ViewModel.Subject>()
    var timeTable = Array(5) { arrayOfNulls<String?>(12) }

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

        gbinding.selectButton.setOnClickListener {
            val intent = Intent(this, SubjectList::class.java)
            intent.putExtra("type", "선택")
            startActivity(intent)
        }

        gbinding.exceptButton.setOnClickListener {
            val intent = Intent(this, SubjectList::class.java)
            intent.putExtra("type", "제외")
            startActivity(intent)
        }

        if (intent.hasExtra("data") && intent.hasExtra("button")) {
            when(intent.getStringExtra("button")) {
                "선택" -> {
                    selectSubject.add(intent.getSerializableExtra("data") as ViewModel.Subject)
                    Log.d("선택과목", selectSubject.toString())
                }
                "제외" -> {
                    exceptSubject.add(intent.getSerializableExtra("data") as ViewModel.Subject)
                    Log.d("제외과목", exceptSubject.toString())
                }
            }
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
            Log.d("선택과목", selectSubject.toString())
            Log.d("제외과목", exceptSubject.toString())
            autoSchedule()
            Log.d("timetable", timeTable.contentDeepToString())
            Log.d("과목정보", subjectInfo.toString())
        }
    }


    private fun autoSchedule(): Int {
        slist[0] = model.getR_subject() // slist[0]: 필수, slist[1]: 전공선택, slist[2]: 교양선택

        for(i in exceptSubject){    // 제외 과목 삭제
            slist[0].removeIf { it.code == i.code }
        }

        while (selectSubject.isNotEmpty()) // 선택 과목 추가
            subjectAdd(selectSubject)

        for(i in slist[0]) {    // 과목 분류 0: 필수, 1: 선택/전공 2: 선택/교양
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
                    if (timeTable[i][j] != null) { //종료, 공강일 불가능 반환
                        Toast.makeText(this@TimetableGeneration, "다른 공강일을 선택해주세요.", Toast.LENGTH_SHORT)
                            .show()
                        timeTable = Array(5) { arrayOfNulls<String?>(12) }  // 초기화
                        credit = 0
                        ge = 0
                        rest = ArrayList<Int>()
                        return -1
                    }
                for (j in 0..11)
                    timeTable[i][j] = "Rest"
            }
        }

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