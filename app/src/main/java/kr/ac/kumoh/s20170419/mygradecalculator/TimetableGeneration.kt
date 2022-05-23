package kr.ac.kumoh.s20170419.mygradecalculator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
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
        val grade = "4"
        val semester= "1"
    }
    var subjectInfo = ArrayList<ViewModel.Subject>()
    var timeTable = Array(5) { arrayOfNulls<String?>(12) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gbinding = ActivityTimetableGenerationBinding.inflate(layoutInflater)
        setContentView(gbinding.root)
        model.requestList("금오공과대학교", grade, semester, "전체")

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
            intent.putExtra("list", selectSubject)
            startActivity(intent)
        }

        gbinding.exceptButton.setOnClickListener {
            val intent = Intent(this, SubjectList::class.java)
            intent.putExtra("type", "제외")
            intent.putExtra("list", exceptSubject)
            startActivity(intent)
        }

        if (intent.hasExtra("data") && intent.hasExtra("button")) {
            when (intent.getStringExtra("button")) {
                "선택" -> {
                    selectSubject =
                        intent.getSerializableExtra("data") as ArrayList<ViewModel.Subject>
                }
                "제외" -> {
                    exceptSubject =
                        intent.getSerializableExtra("data") as ArrayList<ViewModel.Subject>
                    Log.d("제외과목", exceptSubject.toString())
                }
            }
        }

        for (i in selectSubject)
            gbinding.selectSubject.append("${i.name} ${i.code.split("-")[1]}분반\n")
        gbinding.selectSubject.movementMethod =  ScrollingMovementMethod()

        for (i in exceptSubject)
            gbinding.exceptSubject.append("${i.name} ${i.code.split("-")[1]}분반\n")
        gbinding.exceptSubject.movementMethod =  ScrollingMovementMethod()

        for (i in rest) {
            when (i) {
                0 -> gbinding.check1.isChecked = true
                1 -> gbinding.check2.isChecked = true
                2 -> gbinding.check3.isChecked = true
                3 -> gbinding.check4.isChecked = true
                4 -> gbinding.check5.isChecked = true
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
                    R.id.check1 -> rest.remove(0)
                    R.id.check2 -> rest.remove(1)
                    R.id.check3 -> rest.remove(2)
                    R.id.check4 -> rest.remove(3)
                    R.id.check5 -> rest.remove(4)
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
            var creditTemp = credit
            var geTemp = ge
            var selectSubjectTemp =  selectSubject
            var exceptSubjectTemp = exceptSubject

            loop@ for (i in 0..5) {
                model.requestList("금오공과대학교", grade, semester, "전체")
                while (true) {
                    if (credit == 0) {
                        Toast.makeText(this@TimetableGeneration, "학점을 입력해주세요.", Toast.LENGTH_SHORT)
                            .show()
                        break@loop
                    } else {
                        when (autoSchedule("0")) { // 선택 학년에서 계산
                            -1 -> break@loop    // 공강일이 잘못된 경우
                            1 -> {  // 정상 작동
                                Log.d("timetable", timeTable.contentDeepToString())
                                Log.d("과목정보", subjectInfo.toString())
                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra("auto", subjectInfo)
                                finish()
                                startActivity(intent)
                                break@loop
                            }
                            2 -> break
                        }
                    }
                }

                model.requestList("금오공과대학교", "전체", semester, "전체")
                while (true) {
                    when (autoSchedule(grade)) { // 모든 학년에서 계산
                        1 -> {
                            Log.d("timetable", timeTable.contentDeepToString())
                            Log.d("과목정보", subjectInfo.toString())
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("auto", subjectInfo)
                            finish()
                            startActivity(intent)
                            break@loop
                        }
                        2 -> { // 실패 초기화 후 다시 시뮬레이션
                            timeTable = Array(5) { arrayOfNulls<String?>(12) }
                            selectSubject = selectSubjectTemp
                            exceptSubject = exceptSubjectTemp
                            credit = creditTemp
                            ge = geTemp
                            subjectInfo.clear()
                            break
                        }
                    }
                }
            }
        }
    }


    private fun autoSchedule(Grade: String): Int { // Grade는 특정 학년에서 시간표를 완성하지 못하였을때 사용
        var slist = Array(3) { ArrayList<ViewModel.Subject>() } // slist[0]: 필수, slist[1]: 전공선택, slist[2]: 교양선택
        slist[0] = model.getR_subject()

        if (Grade == grade) { // 전체 학년에서 특정 grade 및 필수과목 제거
            slist[0].removeIf { it.grade == grade }
            slist[0].removeIf { it.division == "필수" }
        }

        for(i in exceptSubject){    // 제외 과목 과목 리스트에서 삭제
            slist[0].removeIf { it.code == i.code }
            exceptSubject.remove(i)
        }

        for(i in selectSubject) { // 선택 과목 테이블에 추가
            slist[0].removeIf { it.name == i.name } // 선택한 과목과 일치하는 분반 모두 삭제
            subjectAdd(selectSubject)
        }

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

        if (Grade != grade) { // 처음에 전체학년 대상이 아닐때 작동
            while (slist[0].isNotEmpty()) // 필수 과목 추가
                subjectAdd(slist[0])

            if (rest.isNotEmpty()) {
                for (i in rest) {
                    for (j in 0..11) {
                        if (timeTable[i][j] != null) { //종료, 공강일 불가능 반환
                            Toast.makeText(this@TimetableGeneration, "다른 공강일을 선택해주세요.", Toast.LENGTH_SHORT)
                                .show()
                            rest.clear()
                            return -1
                        }
                    }
                }
                for (i in rest) // 공강일 가능한 경우
                    for (j in 0..11)
                        for (j in 0..11)
                            timeTable[i][j] = "Rest"
            }

            while (slist[2].isNotEmpty()) {
                while (ge != 0) { // 교양을 넣기를 희망한다면
                    if (subjectAdd(slist[2]) == 1)
                        ge -= 1
                }
            }
        }

        while (credit != 0) { // 학점이 0이 될때까지 채워주기
            if (slist[1].isNotEmpty())
                subjectAdd(slist[1])
            else {
                if (slist[2].isNotEmpty())
                    subjectAdd(slist[2])
                else {
                    return 2    // 전체 학년으로 리스트 변환
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
        if (credit - subjectList[num].credit.toInt() >= 0) {// list로 불러온 학점이 남은 학점을 초과하는지 확인
            val time = subjectList[num].time.split(", ")

            for (t in time) { // 공강일 확인
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
            credit -= subjectList[num].credit.toInt()
            subjectList.removeIf { it.name == subjectList[num].name } // 과목 추가에 성공했을 경우 과목명이 같으면 모두 삭제(분반 제거)
        }
        else {
            subjectList.removeAt(num)
            return 0
        }
        return 1
    }
}