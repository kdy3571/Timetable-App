package kr.ac.kumoh.s20170419.mygradecalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityGradeManagementBinding
import kotlin.math.pow
import kotlin.math.roundToInt


class GradeManagement : AppCompatActivity() {
    private lateinit var binding: ActivityGradeManagementBinding
    private lateinit var dbmodel: InnerDBViewModel
    private var subjectList: ArrayList<GPState> = arrayListOf()
    private var db: ArrayList<GPState> = arrayListOf()
    private var gs = ""
    private var purpose = 0.0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGradeManagementBinding.inflate(layoutInflater)
        dbmodel = ViewModelProvider(this@GradeManagement).get(InnerDBViewModel::class.java)
        setContentView(binding.root)

        val gradeData: Array<String> = resources.getStringArray(R.array.subject_grade)
        val gradeDataApter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, gradeData)
        binding.gp1.adapter = gradeDataApter
        binding.gp2.adapter = gradeDataApter
        binding.gp3.adapter = gradeDataApter
        binding.gp4.adapter = gradeDataApter
        binding.gp5.adapter = gradeDataApter
        binding.gp6.adapter = gradeDataApter
        binding.gp7.adapter = gradeDataApter
        binding.gp8.adapter = gradeDataApter
        binding.gp9.adapter = gradeDataApter
        binding.gp10.adapter = gradeDataApter
        binding.gp11.adapter = gradeDataApter
        binding.gp12.adapter = gradeDataApter

        if (intent.hasExtra("gs")) {
            gs = intent.getStringExtra("gs")!!
            binding.gradeSemester.text = "${gs.split("-")[0]}학년 ${gs.split("-")[1]}학기"
        }
        clear()
        getInfo(gs)

        val subjectListener = View.OnKeyListener { editText, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                when (editText.id) {
                    R.id.subject1 -> {
                        if (subjectList.isNotEmpty()) {
                            subjectList[0].name = binding.subject1.text.toString()
                            update(subjectList[0])
                        } else
                            connect(gs, binding.subject1.text.toString(), binding.credit1.text.toString(), binding.gp1.selectedItem.toString(), binding.majorCheck1.isChecked)
                    }
                    R.id.subject2 -> {
                        if (subjectList.size > 1) {
                            subjectList[1].name = binding.subject2.text.toString()
                            update(subjectList[1])
                        } else
                            connect(gs, binding.subject2.text.toString(), binding.credit2.text.toString(), binding.gp2.toString(), binding.majorCheck2.isChecked)
                    }
                    R.id.subject3 -> {
                        if (subjectList.size > 2) {
                            subjectList[2].name = binding.subject3.text.toString()
                            update(subjectList[2])
                        } else
                            connect(gs, binding.subject3.text.toString(), binding.credit3.text.toString(), binding.gp3.selectedItem.toString(), binding.majorCheck3.isChecked)
                    }
                    R.id.subject4 -> {
                        if (subjectList.size > 3) {
                            subjectList[3].name = binding.subject4.text.toString()
                            update(subjectList[3])
                        } else
                            connect(gs, binding.subject4.text.toString(), binding.credit4.text.toString(), binding.gp4.selectedItem.toString(), binding.majorCheck4.isChecked)
                    }
                    R.id.subject5 -> {
                        if (subjectList.size > 4) {
                            subjectList[4].name = binding.subject5.text.toString()
                            update(subjectList[4])
                        } else
                            connect(gs, binding.subject5.text.toString(), binding.credit5.text.toString(), binding.gp5.selectedItem.toString(), binding.majorCheck5.isChecked)
                    }
                    R.id.subject6 -> {
                        if (subjectList.size > 5) {
                            subjectList[5].name = binding.subject6.text.toString()
                            update(subjectList[5])
                        } else
                            connect(gs, binding.subject6.text.toString(), binding.credit6.text.toString(), binding.gp6.selectedItem.toString(), binding.majorCheck6.isChecked)
                    }
                    R.id.subject7 -> {
                        if (subjectList.size > 6) {
                            subjectList[6].name = binding.subject7.text.toString()
                            update(subjectList[6])
                        } else
                            connect(gs, binding.subject7.text.toString(), binding.credit7.text.toString(), binding.gp7.selectedItem.toString(), binding.majorCheck7.isChecked)
                    }
                    R.id.subject8 -> {
                        if (subjectList.size > 7) {
                            subjectList[7].name = binding.subject8.text.toString()
                            update(subjectList[7])
                        } else
                            connect(gs, binding.subject8.text.toString(), binding.credit8.text.toString(), binding.gp8.selectedItem.toString(), binding.majorCheck8.isChecked)
                    }
                    R.id.subject9 -> {
                        if (subjectList.size > 8) {
                            subjectList[8].name = binding.subject9.text.toString()
                            update(subjectList[8])
                        } else
                            connect(gs, binding.subject9.text.toString(), binding.credit9.text.toString(), binding.gp9.selectedItem.toString(), binding.majorCheck9.isChecked)
                    }
                    R.id.subject10 -> {
                        if (subjectList.size > 9) {
                            subjectList[9].name = binding.subject10.text.toString()
                            update(subjectList[9])
                        } else
                            connect(gs, binding.subject10.text.toString(), binding.credit10.text.toString(), binding.gp10.selectedItem.toString(), binding.majorCheck10.isChecked)
                    }
                    R.id.subject11 -> {
                        if (subjectList.size > 10) {
                            subjectList[10].name = binding.subject11.text.toString()
                            update(subjectList[10])
                        } else
                            connect(gs, binding.subject11.text.toString(), binding.credit11.text.toString(), binding.gp11.selectedItem.toString(), binding.majorCheck11.isChecked)
                    }
                    R.id.subject12 -> {
                        if (subjectList.size > 11) {
                            subjectList[11].name = binding.subject12.text.toString()
                            update(subjectList[11])
                        } else
                            connect(gs, binding.subject12.text.toString(), binding.credit12.text.toString(), binding.gp12.selectedItem.toString(), binding.majorCheck12.isChecked)
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

        val creditListener = View.OnKeyListener { editText, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                when (editText.id) {
                    R.id.credit1 -> {
                        if (subjectList.isNotEmpty()) {
                            subjectList[0].credit = binding.credit1.text.toString()
                            update(subjectList[0])
                        } else
                            connect(gs, binding.subject1.text.toString(), binding.credit1.text.toString(), binding.gp1.selectedItem.toString(), binding.majorCheck1.isChecked)
                    }
                    R.id.credit2 -> {
                        if (subjectList.size > 1) {
                            subjectList[1].credit = binding.credit2.text.toString()
                            update(subjectList[1])
                        } else
                            connect(gs, binding.subject2.text.toString(), binding.credit2.text.toString(), binding.gp2.selectedItem.toString(), binding.majorCheck2.isChecked)
                    }
                    R.id.credit3 -> {
                        if (subjectList.size > 2) {
                            subjectList[2].credit = binding.credit3.text.toString()
                            update(subjectList[2])
                        } else
                            connect(gs, binding.subject3.text.toString(), binding.credit3.text.toString(), binding.gp3.selectedItem.toString(), binding.majorCheck3.isChecked)
                    }
                    R.id.credit4 -> {
                        if (subjectList.size > 3) {
                            subjectList[3].credit = binding.credit4.text.toString()
                            update(subjectList[3])
                        } else
                            connect(gs, binding.subject4.text.toString(), binding.credit4.text.toString(), binding.gp4.selectedItem.toString(), binding.majorCheck4.isChecked)
                    }
                    R.id.credit5 -> {
                        if (subjectList.size > 4) {
                            subjectList[4].credit = binding.credit5.text.toString()
                            update(subjectList[4])
                        } else
                            connect(gs, binding.subject5.text.toString(), binding.credit5.text.toString(), binding.gp5.selectedItem.toString(), binding.majorCheck5.isChecked)
                    }
                    R.id.credit6 -> {
                        if (subjectList.size > 5) {
                            subjectList[5].credit = binding.credit6.text.toString()
                            update(subjectList[5])
                        } else
                            connect(gs, binding.subject6.text.toString(), binding.credit6.text.toString(), binding.gp6.selectedItem.toString(), binding.majorCheck6.isChecked)
                    }
                    R.id.credit7 -> {
                        if (subjectList.size > 6) {
                            subjectList[6].credit = binding.credit7.text.toString()
                            update(subjectList[6])
                        } else
                            connect(gs, binding.subject7.text.toString(), binding.credit7.text.toString(), binding.gp7.selectedItem.toString(), binding.majorCheck7.isChecked)
                    }
                    R.id.credit8 -> {
                        if (subjectList.size > 7) {
                            subjectList[7].credit = binding.credit8.text.toString()
                            update(subjectList[7])
                        } else
                            connect(gs, binding.subject8.text.toString(), binding.credit8.text.toString(), binding.gp8.selectedItem.toString(), binding.majorCheck8.isChecked)
                    }
                    R.id.credit9 -> {
                        if (subjectList.size > 8) {
                            subjectList[8].credit = binding.subject9.text.toString()
                            update(subjectList[8])
                        } else
                            connect(gs, binding.subject9.text.toString(), binding.credit9.text.toString(), binding.gp9.selectedItem.toString(), binding.majorCheck9.isChecked)
                    }
                    R.id.credit10 -> {
                        if (subjectList.size > 9) {
                            subjectList[9].credit = binding.credit10.text.toString()
                            update(subjectList[9])
                        } else
                            connect(gs, binding.subject10.text.toString(), binding.credit10.text.toString(), binding.gp10.selectedItem.toString(), binding.majorCheck10.isChecked)
                    }
                    R.id.credit11 -> {
                        if (subjectList.size > 10) {
                            subjectList[10].credit = binding.credit11.text.toString()
                            update(subjectList[10])
                        } else
                            connect(gs, binding.subject11.text.toString(), binding.credit11.text.toString(), binding.gp11.selectedItem.toString(), binding.majorCheck11.isChecked)
                    }
                    R.id.credit12 -> {
                        if (subjectList.size > 11) {
                            subjectList[11].credit = binding.credit12.text.toString()
                            update(subjectList[11])
                        } else
                            connect(gs, binding.subject12.text.toString(), binding.credit12.text.toString(), binding.gp12.selectedItem.toString(), binding.majorCheck12.isChecked)
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

        val checkListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (subjectList.isNotEmpty()) {
                if (isChecked) {
                    when (buttonView.id) {
                        R.id.major_check1 -> {
                            if (subjectList.isNotEmpty()) {
                                subjectList[0].subject = "전공"
                                update(subjectList[0])
                            } else
                                connect(gs, binding.subject1.text.toString(), binding.credit1.text.toString(), binding.gp1.selectedItem.toString(), binding.majorCheck1.isChecked)
                        }
                        R.id.major_check2 -> {
                            if (subjectList.size > 1) {
                                subjectList[1].subject = "전공"
                                update(subjectList[1])
                            } else
                                connect(gs, binding.subject2.text.toString(), binding.credit2.text.toString(), binding.gp2.selectedItem.toString(), binding.majorCheck2.isChecked)
                        }
                        R.id.major_check3 -> {
                            if (subjectList.size > 2) {
                                subjectList[2].subject = "전공"
                                update(subjectList[2])
                            } else
                                connect(gs, binding.subject3.text.toString(), binding.credit3.text.toString(), binding.gp3.selectedItem.toString(), binding.majorCheck3.isChecked)
                        }
                        R.id.major_check4 -> {
                            if (subjectList.size > 3) {
                                subjectList[3].subject = "전공"
                                update(subjectList[3])
                            } else
                                connect(gs, binding.subject4.text.toString(), binding.credit4.text.toString(), binding.gp4.selectedItem.toString(), binding.majorCheck4.isChecked)
                        }
                        R.id.major_check5 -> {
                            if (subjectList.size > 4) {
                                subjectList[4].subject = "전공"
                                update(subjectList[4])
                            } else
                                connect(gs, binding.subject5.text.toString(), binding.credit5.text.toString(), binding.gp5.selectedItem.toString(), binding.majorCheck5.isChecked)
                        }
                        R.id.major_check6 -> {
                            if (subjectList.size > 5) {
                                subjectList[5].subject = "전공"
                                update(subjectList[5])
                            } else
                                connect(gs, binding.subject6.text.toString(), binding.credit6.text.toString(), binding.gp6.selectedItem.toString(), binding.majorCheck6.isChecked)
                        }
                        R.id.major_check7 -> {
                            if (subjectList.size > 6) {
                                subjectList[6].subject = "전공"
                                update(subjectList[6])
                            } else
                                connect(gs, binding.subject7.text.toString(), binding.credit7.text.toString(), binding.gp7.selectedItem.toString(), binding.majorCheck7.isChecked)
                        }
                        R.id.major_check8 -> {
                            if (subjectList.size > 7) {
                                subjectList[7].subject = "전공"
                                update(subjectList[7])
                            } else
                                connect(gs, binding.subject8.text.toString(), binding.credit8.text.toString(), binding.gp8.selectedItem.toString(), binding.majorCheck8.isChecked)
                        }
                        R.id.major_check9 -> {
                            if (subjectList.size > 8) {
                                subjectList[8].subject = "전공"
                                update(subjectList[8])
                            } else
                                connect(gs, binding.subject9.text.toString(), binding.credit9.text.toString(), binding.gp9.selectedItem.toString(), binding.majorCheck9.isChecked)
                        }
                        R.id.major_check10 -> {
                            if (subjectList.size > 9) {
                                subjectList[9].subject = "전공"
                                update(subjectList[9])
                            } else
                                connect(gs, binding.subject10.text.toString(), binding.credit10.text.toString(), binding.gp10.selectedItem.toString(), binding.majorCheck10.isChecked)
                        }
                        R.id.major_check11 -> {
                            if (subjectList.size > 10) {
                                subjectList[10].subject = "전공"
                                update(subjectList[10])
                            } else
                                connect(gs, binding.subject11.text.toString(), binding.credit11.text.toString(), binding.gp11.selectedItem.toString(), binding.majorCheck11.isChecked)
                        }
                        R.id.major_check12 -> {
                            if (subjectList.size > 11) {
                                subjectList[11].subject = "전공"
                                update(subjectList[11])
                            } else
                                connect(gs, binding.subject12.text.toString(), binding.credit12.text.toString(), binding.gp12.selectedItem.toString(), binding.majorCheck12.isChecked)
                        }
                    }
                } else {
                    when (buttonView.id) {
                        R.id.major_check1 -> {
                            if (subjectList.isNotEmpty()) {
                                subjectList[0].subject = ""
                                update(subjectList[0])
                            } else
                                connect(gs, binding.subject1.text.toString(), binding.credit1.text.toString(), binding.gp1.selectedItem.toString(), binding.majorCheck1.isChecked)
                        }
                        R.id.major_check2 -> {
                            if (subjectList.size > 1) {
                                subjectList[1].subject = ""
                                update(subjectList[1])
                            } else
                                connect(gs, binding.subject2.text.toString(), binding.credit2.text.toString(), binding.gp2.selectedItem.toString(), binding.majorCheck2.isChecked)
                        }
                        R.id.major_check3 -> {
                            if (subjectList.size > 2) {
                                subjectList[2].subject = ""
                                update(subjectList[2])
                            } else
                                connect(gs, binding.subject3.text.toString(), binding.credit3.text.toString(), binding.gp3.selectedItem.toString(), binding.majorCheck3.isChecked)
                        }
                        R.id.major_check4 -> {
                            if (subjectList.size > 3) {
                                subjectList[3].subject = ""
                                update(subjectList[3])
                            } else
                                connect(gs, binding.subject4.text.toString(), binding.credit4.text.toString(), binding.gp4.selectedItem.toString(), binding.majorCheck4.isChecked)
                        }
                        R.id.major_check5 -> {
                            if (subjectList.size > 4) {
                                subjectList[4].subject = ""
                                update(subjectList[4])
                            } else
                                connect(gs, binding.subject5.text.toString(), binding.credit5.text.toString(), binding.gp5.selectedItem.toString(), binding.majorCheck5.isChecked)
                        }
                        R.id.major_check6 -> {
                            if (subjectList.size > 5) {
                                subjectList[5].subject = ""
                                update(subjectList[5])
                            } else
                                connect(gs, binding.subject6.text.toString(), binding.credit6.text.toString(), binding.gp6.selectedItem.toString(), binding.majorCheck6.isChecked)
                        }
                        R.id.major_check7 -> {
                            if (subjectList.size > 6) {
                                subjectList[6].subject = ""
                                update(subjectList[6])
                            } else
                                connect(gs, binding.subject7.text.toString(), binding.credit7.text.toString(), binding.gp7.selectedItem.toString(), binding.majorCheck7.isChecked)
                        }
                        R.id.major_check8 -> {
                            if (subjectList.size > 7) {
                                subjectList[7].subject = ""
                                update(subjectList[7])
                            } else
                                connect(gs, binding.subject8.text.toString(), binding.credit8.text.toString(), binding.gp8.selectedItem.toString(), binding.majorCheck8.isChecked)
                        }
                        R.id.major_check9 -> {
                            if (subjectList.size > 8) {
                                subjectList[8].subject = ""
                                update(subjectList[8])
                            } else
                                connect(gs, binding.subject9.text.toString(), binding.credit9.text.toString(), binding.gp9.selectedItem.toString(), binding.majorCheck9.isChecked)
                        }
                        R.id.major_check10 -> {
                            if (subjectList.size > 9) {
                                subjectList[9].subject = ""
                                update(subjectList[9])
                            } else
                                connect(gs, binding.subject10.text.toString(), binding.credit10.text.toString(), binding.gp10.selectedItem.toString(), binding.majorCheck10.isChecked)
                        }
                        R.id.major_check11 -> {
                            if (subjectList.size > 10) {
                                subjectList[10].subject = ""
                                update(subjectList[10])
                            } else
                                connect(gs, binding.subject11.text.toString(), binding.credit11.text.toString(), binding.gp11.selectedItem.toString(), binding.majorCheck11.isChecked)
                        }
                        R.id.major_check12 -> {
                            if (subjectList.size > 11) {
                                subjectList[11].subject = ""
                                update(subjectList[11])
                            } else
                                connect(gs, binding.subject12.text.toString(), binding.credit12.text.toString(), binding.gp12.selectedItem.toString(), binding.majorCheck12.isChecked)
                        }
                    }
                }
            }
        }
        binding.majorCheck1.setOnCheckedChangeListener(checkListener)
        binding.majorCheck2.setOnCheckedChangeListener(checkListener)
        binding.majorCheck3.setOnCheckedChangeListener(checkListener)
        binding.majorCheck4.setOnCheckedChangeListener(checkListener)
        binding.majorCheck5.setOnCheckedChangeListener(checkListener)
        binding.majorCheck6.setOnCheckedChangeListener(checkListener)
        binding.majorCheck7.setOnCheckedChangeListener(checkListener)
        binding.majorCheck8.setOnCheckedChangeListener(checkListener)
        binding.majorCheck9.setOnCheckedChangeListener(checkListener)
        binding.majorCheck10.setOnCheckedChangeListener(checkListener)
        binding.majorCheck11.setOnCheckedChangeListener(checkListener)
        binding.majorCheck12.setOnCheckedChangeListener(checkListener)

        val gpListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(gradePoint: AdapterView<*>, p0: View?, position: Int, p1: Long) {
                if (subjectList.isNotEmpty()) {
                    when (gradePoint.id) {
                        R.id.gp1 -> {
                            if (subjectList.isNotEmpty()) {
                                subjectList[0].gp = binding.gp1.selectedItem.toString()
                                update(subjectList[0])
                            } else
                                connect(gs, binding.subject1.text.toString(), binding.credit1.text.toString(), binding.gp1.selectedItem.toString(), binding.majorCheck1.isChecked)
                        }
                        R.id.gp2 -> {
                            if (subjectList.size > 1) {
                                subjectList[1].gp = binding.gp2.selectedItem.toString()
                                update(subjectList[1])
                            } else
                                connect(gs, binding.subject2.text.toString(), binding.credit2.text.toString(), binding.gp2.selectedItem.toString(), binding.majorCheck2.isChecked)
                        }
                        R.id.gp3 -> {
                            if (subjectList.size > 2) {
                                subjectList[2].gp = binding.gp3.selectedItem.toString()
                                update(subjectList[2])
                            } else
                                connect(gs, binding.subject3.text.toString(), binding.credit3.text.toString(), binding.gp3.selectedItem.toString(), binding.majorCheck3.isChecked)
                        }
                        R.id.gp4 -> {
                            if (subjectList.size > 3) {
                                subjectList[3].gp = binding.gp4.selectedItem.toString()
                                update(subjectList[3])
                            } else
                                connect(gs, binding.subject4.text.toString(), binding.credit4.text.toString(), binding.gp4.selectedItem.toString(), binding.majorCheck4.isChecked)
                        }
                        R.id.gp5 -> {
                            if (subjectList.size > 4) {
                                subjectList[4].gp = binding.gp5.selectedItem.toString()
                                update(subjectList[4])
                            } else
                                connect(gs, binding.subject5.text.toString(), binding.credit5.text.toString(), binding.gp5.selectedItem.toString(), binding.majorCheck5.isChecked)
                        }
                        R.id.gp6 -> {
                            if (subjectList.size > 5) {
                                subjectList[5].gp = binding.gp6.selectedItem.toString()
                                update(subjectList[5])
                            } else
                                connect(gs, binding.subject6.text.toString(), binding.credit6.text.toString(), binding.gp6.selectedItem.toString(), binding.majorCheck6.isChecked)
                        }
                        R.id.gp7 -> {
                            if (subjectList.size > 6) {
                                subjectList[6].gp = binding.gp7.selectedItem.toString()
                                update(subjectList[6])
                            } else
                                connect(gs, binding.subject7.text.toString(), binding.credit7.text.toString(), binding.gp7.selectedItem.toString(), binding.majorCheck7.isChecked)
                        }
                        R.id.gp8 -> {
                            if (subjectList.size > 7) {
                                subjectList[7].gp = binding.gp8.selectedItem.toString()
                                update(subjectList[7])
                            } else
                                connect(gs, binding.subject8.text.toString(), binding.credit8.text.toString(), binding.gp8.selectedItem.toString(), binding.majorCheck8.isChecked)
                        }
                        R.id.gp9 -> {
                            if (subjectList.size > 8) {
                                subjectList[8].gp = binding.gp9.selectedItem.toString()
                                update(subjectList[8])
                            } else
                                connect(gs, binding.subject9.text.toString(), binding.credit9.text.toString(), binding.gp9.selectedItem.toString(), binding.majorCheck9.isChecked)
                        }
                        R.id.gp10 -> {
                            if (subjectList.size > 9) {
                                subjectList[9].gp = binding.gp10.selectedItem.toString()
                                update(subjectList[9])
                            } else
                                connect(gs, binding.subject10.text.toString(), binding.credit10.text.toString(), binding.gp10.selectedItem.toString(), binding.majorCheck10.isChecked)
                        }
                        R.id.gp11 -> {
                            if (subjectList.size > 10) {
                                subjectList[10].gp = binding.gp11.selectedItem.toString()
                                update(subjectList[10])
                            } else
                                connect(gs, binding.subject11.text.toString(), binding.credit11.text.toString(), binding.gp11.selectedItem.toString(), binding.majorCheck11.isChecked)
                        }
                        R.id.gp12 -> {
                            if (subjectList.size > 11) {
                                subjectList[11].gp = binding.gp12.selectedItem.toString()
                                update(subjectList[11])
                            } else
                                connect(gs, binding.subject12.text.toString(), binding.credit12.text.toString(), binding.gp12.selectedItem.toString(), binding.majorCheck12.isChecked)
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        binding.gp1.onItemSelectedListener = gpListener
        binding.gp2.onItemSelectedListener = gpListener
        binding.gp3.onItemSelectedListener = gpListener
        binding.gp4.onItemSelectedListener = gpListener
        binding.gp5.onItemSelectedListener = gpListener
        binding.gp6.onItemSelectedListener = gpListener
        binding.gp7.onItemSelectedListener = gpListener
        binding.gp8.onItemSelectedListener = gpListener
        binding.gp9.onItemSelectedListener = gpListener
        binding.gp10.onItemSelectedListener = gpListener
        binding.gp11.onItemSelectedListener = gpListener
        binding.gp12.onItemSelectedListener = gpListener

        binding.LoadTimetable.setOnClickListener {
            deleteDB(gs)    // DB에서 gs에 해당하는 data 삭제
            getSubjectList(gs) // 해당 시간표 불러와 Room에 저장
            getInfo(gs) // 새로 업데이트
        }

        binding.purposeGP.setOnClickListener {
            if (binding.purposeGP.text.toString() != "") {
                if(binding.purposeGP.text.toString().toDouble() > 4.5) {
                    purpose = 4.5
                    binding.purposeGP.setText("4.5")
                }
                else
                    purpose = binding.purposeGP.text.toString().toDouble()
            }
            else
                purpose = 0.0
            getGS()
        }
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
        binding.gradeSemester.text = "${gs.split("-")[0]}학년 ${gs.split("-")[1]}학기"
        clear()
        getInfo(gs)

        return super.onOptionsItemSelected(item)
    }

    private fun getSubjectList(gs: String) { // 시간표에서 과목을 불러와 Room에 저장
        Thread(Runnable {
            connect(dbmodel.getSubject(gs))
        }).start()
        Thread.sleep(100L)
    }

    fun connect(db: List<WeekState>){   // 불러온 과목정보를 db에 저장
        Thread(Runnable {
            for(element in db){
                dbmodel.connect(element)
            }
        }).start()
        Thread.sleep(100L)
    }

    private fun getInfo(gs: String) { // 저장된 과목정보 불러오기
        Thread(Runnable {
            subjectList = dbmodel.getInfo(gs) as ArrayList<GPState>
        }).start()
        Thread.sleep(100L)
        loadInfo(subjectList)
    }

    private fun getInfo() { // 모든 저장된 과목정보 불러오기
        Thread(Runnable {
            db = dbmodel.getInfo() as ArrayList<GPState>
        }).start()
        Thread.sleep(100L)
    }

    private fun getGS() { // 학점 계산
        var GPSum = 0.0
        var majorGPSum = 0.0
        var creditSum = 0
        var majorCreditSum = 0

        getInfo()
        if(db.isNotEmpty()) {
            for (i in db) {  // 전체 학점
                if(i.credit != "") {
                    when (i.gp) {
                        "A+" -> GPSum += 4.5 * i.credit!!.toInt()
                        "A" -> GPSum += 4.0 * i.credit!!.toInt()
                        "B+" -> GPSum += 3.5 * i.credit!!.toInt()
                        "B" -> GPSum += 3.0 * i.credit!!.toInt()
                        "C+" -> GPSum += 2.5 * i.credit!!.toInt()
                        "C" -> GPSum += 2.0 * i.credit!!.toInt()
                        "D+" -> GPSum += 1.5 * i.credit!!.toInt()
                        "D" -> GPSum += 1.0 * i.credit!!.toInt()
                        "F" -> GPSum += 0.0 * i.credit!!.toInt()
                        else -> null
                    }
                    creditSum += i.credit!!.toInt()
                }

                if (i.subject == "전공") {
                    if (i.credit != "0") {
                        when (i.gp) {
                            "A+" -> majorGPSum += 4.5 * i.credit!!.toInt()
                            "A" -> majorGPSum += 4.0 * i.credit!!.toInt()
                            "B+" -> majorGPSum += 3.5 * i.credit!!.toInt()
                            "B" -> majorGPSum += 3.0 * i.credit!!.toInt()
                            "C+" -> majorGPSum += 2.5 * i.credit!!.toInt()
                            "C" -> majorGPSum += 2.0 * i.credit!!.toInt()
                            "D+" -> majorGPSum += 1.5 * i.credit!!.toInt()
                            "D" -> majorGPSum += 1.0 * i.credit!!.toInt()
                            "F" -> majorGPSum += 0.0 * i.credit!!.toInt()
                        }
                        majorCreditSum += i.credit!!.toInt()
                    }
                }
            }
            binding.allCredits.text = creditSum.toString()
            if(creditSum != 0) {
                val GPA = (GPSum / creditSum * 10.0.pow(2.0)).roundToInt() / 10.0.pow(2.0)
                binding.allGp.text = GPA.toString()

                if (purpose != 0.0 && purpose > GPA) {
                    val purposeGPA = ((purpose * 140 - GPSum) / (140 - creditSum) * 10.0.pow(2.0)).roundToInt() / 10.0.pow(2.0)
                    Log.d("purposeGPA", purposeGPA.toString())
                    if (purposeGPA <= 4.5)
                        binding.purposeRequest.text = "남은 평균 학점은 $purposeGPA 이상을 목표로 하세요."
                    else
                        binding.purposeRequest.text = "재수강이 필요합니다."
                }
                else
                    binding.purposeRequest.text = ""

            }
            if(majorCreditSum != 0) {
                val majorGPA = (majorGPSum / majorCreditSum * 10.0.pow(2.0)).roundToInt() / 10.0.pow(2.0)
                binding.majorGp.text = majorGPA.toString()
            }
        }

        val user = getSharedPreferences("user", Context.MODE_PRIVATE)
        val editor = user.edit()
        editor.putString("allGP", binding.allGp.text.toString())
        editor.putString("majorGP", binding.majorGp.text.toString())
        editor.putString("allCredits", binding.allCredits.text.toString())
        editor.apply()

        if (subjectList.isNotEmpty()) {
            GPSum = 0.0
            majorGPSum = 0.0
            creditSum = 0
            majorCreditSum = 0
            for (i in subjectList) {  // 현재 학기 학점
                when (i.gp) {
                    "A+" -> GPSum += 4.5 * i.credit!!.toInt()
                    "A" -> GPSum += 4.0 * i.credit!!.toInt()
                    "B+" -> GPSum += 3.5 * i.credit!!.toInt()
                    "B" -> GPSum += 3.0 * i.credit!!.toInt()
                    "C+" -> GPSum += 2.5 * i.credit!!.toInt()
                    "C" -> GPSum += 2.0 * i.credit!!.toInt()
                    "D+" -> GPSum += 1.5 * i.credit!!.toInt()
                    "D" -> GPSum += 1.0 * i.credit!!.toInt()
                    "F" -> GPSum += 0.0 * i.credit!!.toInt()
                }
                creditSum += i.credit!!.toInt()

                if (i.subject == "전공") {
                    when (i.gp) {
                        "A+" -> majorGPSum += 4.5 * i.credit!!.toInt()
                        "A" -> majorGPSum += 4.0 * i.credit!!.toInt()
                        "B+" -> majorGPSum += 3.5 * i.credit!!.toInt()
                        "B" -> majorGPSum += 3.0 * i.credit!!.toInt()
                        "C+" -> majorGPSum += 2.5 * i.credit!!.toInt()
                        "C" -> majorGPSum += 2.0 * i.credit!!.toInt()
                        "D+" -> majorGPSum += 1.5 * i.credit!!.toInt()
                        "D" -> majorGPSum += 1.0 * i.credit!!.toInt()
                        "F" -> majorGPSum += 0.0 * i.credit!!.toInt()
                    }
                    majorCreditSum += i.credit!!.toInt()
                }
            }
            binding.currentCredits.text = creditSum.toString()
            if(creditSum != 0) {
                binding.currentAllGp.text =
                    ((GPSum / creditSum * 10.0.pow(2.0)).roundToInt() / 10.0.pow(2.0)).toString()
            }
            if(majorCreditSum != 0) {
                binding.currentMajorGp.text =
                    ((majorGPSum / majorCreditSum * 10.0.pow(2.0)).roundToInt() / 10.0.pow(2.0)).toString()
            }
        }
    }

    private fun loadInfo(subjectList: List<GPState>) {
        if(subjectList.isNotEmpty()) {
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
                when (subjectList[i - 1].gp) {
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
                when (subjectList[i -1].subject) {
                    "전공" -> checkID.isChecked = true
                    else -> checkID.isChecked = false
                }
            }
        }
        getGS()
    }

    private fun clear() {
        subjectList.clear()
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
        binding.currentAllGp.text = "0.0"
        binding.currentMajorGp.text = "0.0"
        binding.currentCredits.text = "0"
    }

    private fun connect(gs: String, name: String?, credit: String?, gp: String?, check: Boolean) {   // 새로 추가된 정보 Room에 저장
        Thread(Runnable {
            dbmodel.connect(gs, name, credit, gp, check)
        }).start()
        Thread.sleep(100L)
        getInfo(gs)
    }

    private fun update(info: GPState) { // 정보를 Room에 업뎃
        Thread(Runnable {
            dbmodel.update(info)
        }).start()
        Thread.sleep(100L)
        getInfo(gs)
    }

    private fun deleteDB(gs: String) { // gs에 해당하는 과목 삭제
        Thread(Runnable {
            dbmodel.delInfo(gs)
        }).start()
        Thread.sleep(100L)
        clear()
    }
    override fun onBackPressed(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
