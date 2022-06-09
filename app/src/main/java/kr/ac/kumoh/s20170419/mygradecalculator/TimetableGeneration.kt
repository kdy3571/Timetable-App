package kr.ac.kumoh.s20170419.mygradecalculator
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.*
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityTimetableGenerationBinding
import java.util.*


open class TimetableGeneration : AppCompatActivity() {
    private val model: ViewModel by viewModels()
    lateinit var gbinding: ActivityTimetableGenerationBinding

    companion object {
        var credit = 0
        var ge = 0
        var creditTemp = 0
        var geTemp = 0
        var rest = ArrayList<Int>()
        var selectSubjectTemp = ArrayList<ViewModel.Subject>()
        var exceptSubjectTemp = ArrayList<ViewModel.Subject>()
        var subjectInfo = ArrayList<ViewModel.Subject>()
        var timeTable = Array(5) { arrayOfNulls<String?>(12) }
        lateinit var grade: String
        lateinit var semester: String
    }
    lateinit var college: String
    lateinit var major: String
    var selectSubject =  ArrayList<ViewModel.Subject>()
    var exceptSubject = ArrayList<ViewModel.Subject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gbinding = ActivityTimetableGenerationBinding.inflate(layoutInflater)
        setContentView(gbinding.root)

        val user = getSharedPreferences("user", Context.MODE_PRIVATE)
        if (user.getString("college", "") != "") {
            college =  user.getString("college", "")!!
            major =  user.getString("major", "")!!
        }

        if (intent.hasExtra("gs")) {
            val temp = intent.getStringExtra("gs")!!.split("-")
            grade = temp[0]
            semester = temp[1]
        }

        gbinding.creditInput.setOnClickListener {
            credit = if (gbinding.creditInput.text.toString() != "")
                gbinding.creditInput.text.toString().toInt()
            else
                0
            creditTemp = credit
        }
        if(credit != 0)
            gbinding.creditInput.setText(credit.toString())

        gbinding.geInput.setOnClickListener {
            ge = if (gbinding.geInput.text.toString() != "")
                gbinding.geInput.text.toString().toInt()
            else
                0
            geTemp = ge
        }
        if(ge != 0)
            gbinding.creditInput.setText(ge.toString())

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
                }
            }
        }

        for (i in selectSubject)
            gbinding.selectSubject.append("${i.name} ${i.code.split("-")[1]}분반\n")
        gbinding.selectSubject.movementMethod = ScrollingMovementMethod()

        for (i in exceptSubject)
            gbinding.exceptSubject.append("${i.name} ${i.code.split("-")[1]}분반\n")
        gbinding.exceptSubject.movementMethod = ScrollingMovementMethod()

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

        model.requestList(college, major, "전체", semester, "전체", "전체")
        gbinding.create.setOnClickListener {
            generation()
        }

        if (intent.hasExtra("button")) {
            if (intent.getStringExtra("button") == "재생성") {
                selectSubject = selectSubjectTemp
                exceptSubject = exceptSubjectTemp
                credit = creditTemp
                ge = geTemp
                generation()
            }
        }
    }


    override fun onBackPressed(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun generation() {
        model.requestList(college, major, "전체", semester, "전체", "전체")
        loop@ for(i in 0..1000) {
            if (credit == 0) {
                Toast.makeText(this@TimetableGeneration, "학점을 입력해주세요.", Toast.LENGTH_SHORT)
                    .show()
                break@loop
            } else {
                when (autoSchedule()) {
                    0 -> {    // 공강일이 잘못된 경우
                        selectSubject = selectSubjectTemp
                        exceptSubject = exceptSubjectTemp
                        credit = creditTemp
                        ge = geTemp
                        break@loop
                    }
                    1 -> {  // 정상 작동
                        var creditCheck = 0
                        for (i in subjectInfo)
                            creditCheck += i.credit.toInt()
                        val tableIntent = Intent(this, AutoTable::class.java)
                        tableIntent.putExtra("timetable", timeTable)
                        tableIntent.putExtra("info", subjectInfo)
                        startActivity(tableIntent)
                        finish()
                        break@loop
                    }
                    2 -> { // 실패 초기화 후 다시 시뮬레이션
                        selectSubject = selectSubjectTemp
                        exceptSubject = exceptSubjectTemp
                        credit = creditTemp
                        ge = geTemp
                    }
                }
            }
        }
    }

    private fun autoSchedule(): Int {
        timeTable = Array(5) { arrayOfNulls<String?>(12) }
        subjectInfo.clear()
        selectSubjectTemp = selectSubject
        exceptSubjectTemp = exceptSubject

        var slist = Array(5) { ArrayList<ViewModel.Subject>() }
        slist[0] = model.getSubject()

        for (i in 0..4)
            if (i != grade.toInt())
                slist[0].removeIf { it.grade == i.toString() && it.division == "필수" }    // 다른 학년 필수 과목 삭제

        for (i in exceptSubject)    // 제외 과목 과목 리스트에서 삭제
            slist[0].removeIf { it.code == i.code }
        exceptSubject.clear()

        for (i in selectSubject) { // 선택 과목 테이블에 추가
            slist[0].removeIf { it.name == i.name } // 선택한 과목과 일치하는 분반 모두 삭제
            subjectAdd(selectSubject)
        }

        for (i in slist[0]) {   // 과목 분류 0: 필수, 1/2: grade 선택 전공/교양, 3/4: 나머지 선택 전공/교양
            if (i.division != "필수") {
                if (i.subject == "전공") {
                    if (i.grade == grade)
                        slist[1].add(i)
                    else
                        slist[3].add(i)
                } else if (i.subject == "교양") {
                    if (i.grade == grade)
                        slist[2].add(i)
                    else
                        slist[4].add(i)
                }
            }
        }
        slist[0].removeIf { it.division != "필수" }

        while (slist[0].isNotEmpty()) // 필수 과목 추가
            subjectAdd(slist[0])

        if (rest.isNotEmpty()) {
            for (i in rest) {
                for (j in 0..11) {
                    if (timeTable[i][j] != null) { //종료, 공강일 불가능 반환
                        Toast.makeText(this@TimetableGeneration, "다른 공강일을 선택해주세요.", Toast.LENGTH_SHORT)
                            .show()
                        rest.clear()
                        return 0
                    }
                }
            }
            for (i in rest) // 공강일 가능한 경우
                for (j in 0..11)
                    timeTable[i][j] = "Rest"
        }

        while (ge != 0) { // 교양이 있기를 원하면 2: grade 교양, 4: 전체 교양
            if (slist[2].isNotEmpty()) {
                if (subjectAdd(slist[2]) == 1)
                    ge -= 1
            } else if(slist[4].isNotEmpty()){
                if (subjectAdd(slist[4]) == 1)
                    ge -= 1
            }
            else
                break
        }

        while (credit != 0) { // 학점이 0이 될때까지 채워주기
            if (slist[1].isNotEmpty()) // grade 전공 - 전체 전공 - grade 교양 - 전체 교양 순서
                subjectAdd(slist[1])
            else{
                if (slist[3].isNotEmpty())
                    subjectAdd(slist[3])
                else {
                    if (slist[2].isNotEmpty())
                        subjectAdd(slist[2])
                    else {
                        if (slist[4].isNotEmpty())
                            subjectAdd(slist[4])
                        else
                            return 2
                    }
                }
            }
        }
        for (i in timeTable.indices) // 정상적으로 생성되었을 때, 공강일을 다시 null 값으로 변경
            for (j in timeTable[i].indices)
                 if (timeTable[i][j] == "Rest")
                     timeTable[i][j] == null
        return 1
    }

    @RequiresApi(Build.VERSION_CODES.N) // 24버전
    fun subjectAdd(subjectList: ArrayList<ViewModel.Subject>): Int {
        val random = Random(System.currentTimeMillis())
        val num = random.nextInt(subjectList.size)
        if (credit - subjectList[num].credit.toInt() >= 0) {// list 로 불러온 학점이 남은 학점을 초과하는지 확인
            val time = subjectList[num].time.split(", ")
            for (t in time) { // 공강인지 비었는지 확인
                val temp = t.split(":")
                if(timeTable[temp[0].toInt()][temp[1].toInt()] == "Rest" || timeTable[temp[0].toInt()][temp[1].toInt()] != null) {
                    subjectList.removeAt(num)
                    return 0
                }
            }
            for (t in time) {
                val temp = t.split(":")
                timeTable[temp[0].toInt()][temp[1].toInt()] = subjectList[num].name
            }
            subjectInfo.add(subjectList[num]) // 과목 추가에 성공했을 경우 subjectInfo 에 추가 및 분반 삭제
            credit -= subjectList[num].credit.toInt()
            subjectList.removeIf { it.name == subjectList[num].name }
        }
        else {
            subjectList.removeAt(num)
            return 0
        }
        return 1
    }
}
