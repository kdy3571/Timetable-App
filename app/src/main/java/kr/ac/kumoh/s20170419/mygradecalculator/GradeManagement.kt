package kr.ac.kumoh.s20170419.mygradecalculator

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityGradeManagementBinding


class GradeManagement : AppCompatActivity() {
    private lateinit var binding: ActivityGradeManagementBinding
    private lateinit var dbmodel: InnerDBViewmodel
    private var subjectList: List<weekstate> = arrayListOf()
    private var db: ArrayList<gpstate> = arrayListOf()
    private var gs = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGradeManagementBinding.inflate(layoutInflater)
        dbmodel = ViewModelProvider(this@GradeManagement).get(InnerDBViewmodel::class.java)
        setContentView(binding.root)

        if (intent.hasExtra("gs")) {
            gs = intent.getStringExtra("gs")!!
        }
        getInfo(gs)  // 해당 gs의 과목정보가 있으면 받아옴
        loadInfo(db) // 해당 gs의 과목정보가 있으면 불러옴

        val subject_grade_data: Array<String> = resources.getStringArray(R.array.subject_grade)
        val subject_grade_data_apter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, subject_grade_data)
        binding.gp1.adapter = subject_grade_data_apter
        binding.gp2.adapter = subject_grade_data_apter
        binding.gp3.adapter = subject_grade_data_apter
        binding.gp4.adapter = subject_grade_data_apter
        binding.gp5.adapter = subject_grade_data_apter
        binding.gp6.adapter = subject_grade_data_apter
        binding.gp7.adapter = subject_grade_data_apter
        binding.gp8.adapter = subject_grade_data_apter
        binding.gp9.adapter = subject_grade_data_apter
        binding.gp10.adapter = subject_grade_data_apter
        binding.gp11.adapter = subject_grade_data_apter
        binding.gp12.adapter = subject_grade_data_apter

        binding.LoadTimetable.setOnClickListener {
            getSubject(gs) // 해당 시간표 불러오기
            for (i in 1..subjectList.size) {
                val resID = resources.getIdentifier("subject$i", "id", packageName)
                val subjectID = findViewById<EditText>(resID)
                val resID2 = resources.getIdentifier("credit$i", "id", packageName)
                val creditID = findViewById<EditText>(resID2)
                val resID3 = resources.getIdentifier("gp$i", "id", packageName)
                val gpID = findViewById<Spinner>(resID3)
                val resID4 = resources.getIdentifier("major_check$i", "id", packageName)
                val checkID = findViewById<CheckBox>(resID4)
                subjectID.setText(subjectList[i - 1].name)
                creditID.setText(subjectList[i - 1].credit)
                when (subjectList[i -1].subject) {
                    "전공" -> checkID.isChecked = true
                    else -> checkID.isChecked = false
                }
            }
            deleteDB(gs)
            connect(subjectList)
        }

        var subjectListener = View.OnKeyListener { editText, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                when (editText.id) {
                    R.id.subject1 -> {
                        if (db.isNotEmpty()) {
                            db[0].name = binding.subject1.text.toString()
                            update(db[0])
                        } else
                            connect(gs, binding.subject1.text.toString(), binding.credit1.text.toString(), binding.gp1.toString(), binding.majorCheck1.isChecked)
                    }
                    R.id.subject2 -> {
                        if (db.size > 1) {
                            db[1].name = binding.subject2.text.toString()
                            update(db[1])
                        } else
                            connect(gs, binding.subject2.text.toString(), binding.credit2.text.toString(), binding.gp2.toString(), binding.majorCheck2.isChecked)
                    }
                    R.id.subject3 -> {
                        if (db.size > 2) {
                            db[2].name = binding.subject3.text.toString()
                            update(db[2])
                        } else
                            connect(gs, binding.subject3.text.toString(), binding.credit3.text.toString(), binding.gp3.toString(), binding.majorCheck3.isChecked)
                    }
                    R.id.subject4 -> {
                        if (db.size > 3) {
                            db[3].name = binding.subject4.text.toString()
                            update(db[3])
                        } else
                            connect(gs, binding.subject4.text.toString(), binding.credit4.text.toString(), binding.gp4.toString(), binding.majorCheck4.isChecked)
                    }
                    R.id.subject5 -> {
                        if (db.size > 4) {
                            db[4].name = binding.subject5.text.toString()
                            update(db[4])
                        } else
                            connect(gs, binding.subject5.text.toString(), binding.credit5.text.toString(), binding.gp5.toString(), binding.majorCheck5.isChecked)
                    }
                    R.id.subject6 -> {
                        if (db.size > 5) {
                            db[5].name = binding.subject6.text.toString()
                            update(db[5])
                        } else
                            connect(gs, binding.subject6.text.toString(), binding.credit6.text.toString(), binding.gp6.toString(), binding.majorCheck6.isChecked)
                    }
                    R.id.subject7 -> {
                        if (db.size > 6) {
                            db[6].name = binding.subject7.text.toString()
                            update(db[6])
                        } else
                            connect(gs, binding.subject7.text.toString(), binding.credit7.text.toString(), binding.gp7.toString(), binding.majorCheck7.isChecked)
                    }
                    R.id.subject8 -> {
                        if (db.size > 7) {
                            db[7].name = binding.subject8.text.toString()
                            update(db[7])
                        } else
                            connect(gs, binding.subject8.text.toString(), binding.credit8.text.toString(), binding.gp8.toString(), binding.majorCheck8.isChecked)
                    }
                    R.id.subject9 -> {
                        if (db.size > 8) {
                            db[8].name = binding.subject9.text.toString()
                            update(db[8])
                        } else
                            connect(gs, binding.subject9.text.toString(), binding.credit9.text.toString(), binding.gp9.toString(), binding.majorCheck9.isChecked)
                    }
                    R.id.subject10 -> {
                        if (db.size > 9) {
                            db[9].name = binding.subject10.text.toString()
                            update(db[9])
                        } else
                            connect(gs, binding.subject10.text.toString(), binding.credit10.text.toString(), binding.gp10.toString(), binding.majorCheck10.isChecked)
                    }
                    R.id.subject11 -> {
                        if (db.size > 10) {
                            db[10].name = binding.subject11.text.toString()
                            update(db[10])
                        } else
                            connect(gs, binding.subject11.text.toString(), binding.credit11.text.toString(), binding.gp11.toString(), binding.majorCheck11.isChecked)
                    }
                    R.id.subject12 -> {
                        if (db.size > 11) {
                            db[11].name = binding.subject12.text.toString()
                            update(db[11])
                        } else
                            connect(gs, binding.subject12.text.toString(), binding.credit12.text.toString(), binding.gp12.toString(), binding.majorCheck12.isChecked)
                    }
                }
            }
            else
                true
            false
        }
        binding.subject1.setOnKeyListener(subjectListener)
        binding.subject2.setOnKeyListener(subjectListener)
        binding.subject3.setOnKeyListener(subjectListener)
        binding.subject4.setOnKeyListener(subjectListener)
        binding.subject5.setOnKeyListener(subjectListener)
        binding.subject6.setOnKeyListener(subjectListener)
        binding.subject7.setOnKeyListener(subjectListener)
        binding.subject8.setOnKeyListener(subjectListener)
        binding.subject9.setOnKeyListener(subjectListener)
        binding.subject10.setOnKeyListener(subjectListener)
        binding.subject11.setOnKeyListener(subjectListener)
        binding.subject12.setOnKeyListener(subjectListener)

        var creditListener = View.OnKeyListener { editText, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                when (editText.id) {
                    R.id.credit1 -> {
                        if (db.isNotEmpty()) {
                            db[0].credit = binding.credit1.text.toString()
                            update(db[0])
                        } else
                            connect(gs, binding.subject1.text.toString(), binding.credit1.text.toString(), binding.gp1.toString(), binding.majorCheck1.isChecked)
                    }
                    R.id.credit2 -> {
                        if (db.size > 1) {
                            db[1].credit = binding.credit2.text.toString()
                            update(db[1])
                        } else
                            connect(gs, binding.subject2.text.toString(), binding.credit2.text.toString(), binding.gp2.toString(), binding.majorCheck2.isChecked)
                    }
                    R.id.credit3 -> {
                        if (db.size > 2) {
                            db[2].credit = binding.credit3.text.toString()
                            update(db[2])
                        } else
                            connect(gs, binding.subject3.text.toString(), binding.credit3.text.toString(), binding.gp3.toString(), binding.majorCheck3.isChecked)
                    }
                    R.id.credit4 -> {
                        if (db.size > 3) {
                            db[3].credit = binding.credit4.text.toString()
                            update(db[3])
                        } else
                            connect(gs, binding.subject4.text.toString(), binding.credit4.text.toString(), binding.gp4.toString(), binding.majorCheck4.isChecked)
                    }
                    R.id.credit5 -> {
                        if (db.size > 4) {
                            db[4].credit = binding.credit5.text.toString()
                            update(db[4])
                        } else
                            connect(gs, binding.subject5.text.toString(), binding.credit5.text.toString(), binding.gp5.toString(), binding.majorCheck5.isChecked)
                    }
                    R.id.credit6 -> {
                        if (db.size > 5) {
                            db[5].credit = binding.credit6.text.toString()
                            update(db[5])
                        } else
                            connect(gs, binding.subject6.text.toString(), binding.credit6.text.toString(), binding.gp6.toString(), binding.majorCheck6.isChecked)
                    }
                    R.id.credit7 -> {
                        if (db.size > 6) {
                            db[6].credit = binding.credit7.text.toString()
                            update(db[6])
                        } else
                            connect(gs, binding.subject7.text.toString(), binding.credit7.text.toString(), binding.gp7.toString(), binding.majorCheck7.isChecked)
                    }
                    R.id.credit8 -> {
                        if (db.size > 7) {
                            db[7].credit = binding.credit8.text.toString()
                            update(db[7])
                        } else
                            connect(gs, binding.subject8.text.toString(), binding.credit8.text.toString(), binding.gp8.toString(), binding.majorCheck8.isChecked)
                    }
                    R.id.credit9 -> {
                        if (db.size > 8) {
                            db[8].credit = binding.subject9.text.toString()
                            update(db[8])
                        } else
                            connect(gs, binding.subject9.text.toString(), binding.credit9.text.toString(), binding.gp9.toString(), binding.majorCheck9.isChecked)
                    }
                    R.id.credit10 -> {
                        if (db.size > 9) {
                            db[9].credit = binding.credit10.text.toString()
                            update(db[9])
                        } else
                            connect(gs, binding.subject10.text.toString(), binding.credit10.text.toString(), binding.gp10.toString(), binding.majorCheck10.isChecked)
                    }
                    R.id.credit11 -> {
                        if (db.size > 10) {
                            db[10].credit = binding.credit11.text.toString()
                            update(db[10])
                        } else
                            connect(gs, binding.subject11.text.toString(), binding.credit11.text.toString(), binding.gp11.toString(), binding.majorCheck11.isChecked)
                    }
                    R.id.credit12 -> {
                        if (db.size > 11) {
                            db[11].credit = binding.credit12.text.toString()
                            update(db[11])
                        } else
                            connect(gs, binding.subject12.text.toString(), binding.credit12.text.toString(), binding.gp12.toString(), binding.majorCheck12.isChecked)
                    }
                }
            }
            else
                true
            false
        }
        binding.credit1.setOnKeyListener(creditListener)
        binding.credit2.setOnKeyListener(creditListener)
        binding.credit3.setOnKeyListener(creditListener)
        binding.credit4.setOnKeyListener(creditListener)
        binding.credit5.setOnKeyListener(creditListener)
        binding.credit6.setOnKeyListener(creditListener)
        binding.credit7.setOnKeyListener(creditListener)
        binding.credit8.setOnKeyListener(creditListener)
        binding.credit9.setOnKeyListener(creditListener)
        binding.credit10.setOnKeyListener(creditListener)
        binding.credit11.setOnKeyListener(creditListener)
        binding.credit12.setOnKeyListener(creditListener)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        var mInflater = menuInflater
        mInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu11 -> gs = "1-1"
            R.id.menu12 -> gs = "1-2"
            R.id.menu21 -> gs = "2-1"
            R.id.menu22 -> gs = "2-2"
            R.id.menu31 -> gs = "3-1"
            R.id.menu32 -> gs = "3-2"
            R.id.menu41 -> gs = "4-1"
            R.id.menu42 -> gs = "4-2"
        }
        getInfo(gs)
        loadInfo(db)

        return super.onOptionsItemSelected(item)
    }

    private fun loadInfo(db: List<gpstate>) {
        clear()
        if(db.isNotEmpty()) {
            for (i in 1..db.size) {
                val resID = resources.getIdentifier("subject$i", "id", packageName)
                val subjectID = findViewById<EditText>(resID)
                val resID2 = resources.getIdentifier("credit$i", "id", packageName)
                val creditID = findViewById<EditText>(resID2)
                val resID3 = resources.getIdentifier("gp$i", "id", packageName)
                val gpID = findViewById<Spinner>(resID3)
                val resID4 = resources.getIdentifier("major_check$i", "id", packageName)
                val checkID = findViewById<CheckBox>(resID4)
                subjectID.setText(db[i - 1].name)
                creditID.setText(db[i - 1].credit)
                when (db[i - 1].gp) {
                    "A+" -> gpID.setSelection(0)
                    "A" -> gpID.setSelection(1)
                    "B+" -> gpID.setSelection(2)
                    "B" -> gpID.setSelection(3)
                    "C+" -> gpID.setSelection(4)
                    "C" -> gpID.setSelection(5)
                    "D+" -> gpID.setSelection(6)
                    "D" -> gpID.setSelection(7)
                    "F" -> gpID.setSelection(8)
                }
                when (db[i -1].subject) {
                    "전공" -> checkID.isChecked = true
                    else -> checkID.isChecked = false
                }
            }
        }
    }

    private fun clear() {
        for (i in 1..12) {
            val resID = resources.getIdentifier("subject$i", "id", packageName)
            val subjectID = findViewById<EditText>(resID)
            val resID2 = resources.getIdentifier("credit$i", "id", packageName)
            val creditID = findViewById<EditText>(resID2)
            val resID3 = resources.getIdentifier("gp$i", "id", packageName)
            val gpID = findViewById<Spinner>(resID3)
            val resID4 = resources.getIdentifier("major_check$i", "id", packageName)
            val checkID = findViewById<CheckBox>(resID4)
            subjectID.setText("")
            creditID.setText("0")
            gpID.setSelection(0)
            checkID.isChecked = false
        }
    }

    private fun getSubject(gs: String) { // 시간표에서 과목정보 불러오기
        Thread(Runnable {
            subjectList = dbmodel.getSubject(gs)
        }).start()
        Thread.sleep(100L)
    }

    private fun getInfo(gs: String) { // 저장된 과목정보 불러오기
        Thread(Runnable {
            db = dbmodel.getInfo(gs) as ArrayList<gpstate>
        }).start()
        Thread.sleep(100L)
    }

    fun connect(db: List<weekstate>){   // 불러온 과목정보를 db에 저장
        Thread(Runnable {
            for(element in db){
                dbmodel.connect(element)
            }
        }).start()
        Thread.sleep(100L)
    }

    private fun connect(gs: String, name: String?, credit: String?, gp: String?, check: Boolean) {   // 새로 추가된 정보
        Thread(Runnable {
            dbmodel.connect(gs, name, credit, gp, check)
        }).start()
        Thread.sleep(100L)
    }

    private fun update(info: gpstate) {
        Thread(Runnable {
            dbmodel.update(info)
        }).start()
        Thread.sleep(100L)
    }

    fun deleteDB(gs: String) {
        Thread(Runnable {
            dbmodel.delInfo(gs)
        }).start()
        Thread.sleep(100L)
    }
}
