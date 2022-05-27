package kr.ac.kumoh.s20170419.mygradecalculator



import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.CompactCalendarView.CompactCalendarViewListener
import com.github.sundeepk.compactcalendarview.domain.Event
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CalendarActivity : AppCompatActivity() {
    private val dateFormatForDisplaying = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    private val dateFormatForMonth = SimpleDateFormat("yyyy년 MM월", Locale.KOREA)
    private val dateFormatForMonth2 = SimpleDateFormat("yyyy-MM", Locale.KOREA)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        val compactCalendarView =
            findViewById<View>(R.id.compactcalendar_view) as CompactCalendarView
        val textView_month = findViewById<View>(R.id.textView_month) as TextView
        val textView_result = findViewById<View>(R.id.textView_result) as TextView
        textView_month.text = dateFormatForMonth.format(compactCalendarView.firstDayOfCurrentMonth)
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY)
        textView_result.setOnClickListener {
            val getDate = textView_result.text.toString()
            val getDate2 = getDate.split(":").toTypedArray()
            val getDateST = getDate2[0]
            Log.d(TAG, "태그 getDateST : $getDateST")
            val simpleDate = SimpleDateFormat("yyyy-MM-dd")
            val builder = AlertDialog.Builder(this@CalendarActivity)
            val dialog = builder.setTitle("일정을 삭제하겠습니까?")
                .setPositiveButton("확인"
                ) { dialog, which -> // 확인 클릭 시 실행할 거 작성
                    var writeDate: Date? = null
                    try {
                        writeDate = simpleDate.parse(getDateST)
                        Log.d(TAG,
                            "태그 writeDate: $writeDate")
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                    val currentLong = writeDate!!.time
                    Log.d(TAG,
                        "태그 currentLong : $currentLong")
                    compactCalendarView.removeEvents(currentLong)
                    textView_result.text = ""
                    Toast.makeText(this@CalendarActivity, "일정이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("취소"
                ) { dialog, which -> // 취소 클릭 시 실행할 거 작성
                    Toast.makeText(this@CalendarActivity, "일정삭제가 취소되었습니다..", Toast.LENGTH_SHORT)
                        .show()
                }
                .create()
            dialog.show()
        }


        // 날짜 클릭 이벤트 관련 코드
        compactCalendarView.setListener(object : CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
                Log.d(TAG, "태그 dateClicked : $dateClicked")
                val events = compactCalendarView.getEvents(dateClicked)

                // 해당날짜에 이벤트가 있으면
                if (events.size > 0) {
                    textView_result.text = events[0].data.toString()
                } else {
                    val simpleDate = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                    val clickDate = simpleDate.format(dateClicked)
                    val builder = AlertDialog.Builder(this@CalendarActivity)
                    val dialog = builder.setTitle("일정추가하기")
                        .setMessage(clickDate + "에 일정을 추가하시겠습니까?")
                        .setPositiveButton("확인"
                        ) { dialog, which -> // 확인 클릭 시 실행할 거 작성
                            Toast.makeText(this@CalendarActivity, "확인되었습니다.", Toast.LENGTH_SHORT).show()
                            val editText = EditText(this@CalendarActivity)

                            // 일정입력할 팝업 띄우기
                            val builder = AlertDialog.Builder(this@CalendarActivity)
                            val dialogText =
                                builder.setTitle("추가할 일정을 입력해 주세요.") // .setMessage("메시지 입력")
                                    .setView(editText)
                                    .setPositiveButton("저장하기"
                                    ) { dialog, which ->
                                        val dateClicked_st = simpleDate.format(dateClicked)
                                        Log.d(TAG,
                                            "태그 dateClicked_st : $dateClicked_st")
                                        var currentDay: Date? = null
                                        try {
                                            // .parse 함수 : Parses text from a string to produce a Date (문자열에서 텍스트를 분석하여 날짜 생성)
                                            currentDay = simpleDate.parse(dateClicked_st)
                                            Log.d(TAG,
                                                "태그 currentDay : $currentDay")
                                        } catch (e: ParseException) {
                                            e.printStackTrace()
                                        }
                                        val currentLong = currentDay!!.time
                                        Log.d(TAG,
                                            "태그 currentLong : $currentLong")
                                        val ev1 = Event(
                                            Color.GREEN,
                                            currentLong,
                                            clickDate + " : " + editText.text.toString())
                                        compactCalendarView.addEvent(ev1)
                                        textView_result.text =
                                            clickDate + " : " + editText.text.toString()
                                        Toast.makeText(this@CalendarActivity,
                                            "일정이 저장되었습니다.",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                    .setNegativeButton("취소"
                                    ) { dialog, which -> // 취소 클릭 시 실행할 거 작성
                                        Toast.makeText(this@CalendarActivity,
                                            "일정입력 취소되었습니다.",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                    .create()
                            dialogText.show()
                        }
                        .setNegativeButton("취소"
                        ) { dialog, which -> // 취소 클릭 시 실행할 거 작성
                            Toast.makeText(this@CalendarActivity, "취소되었습니다.", Toast.LENGTH_SHORT)
                                .show()
                        }
                        .create()
                    dialog.show()
                }
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                textView_month.text = dateFormatForMonth.format(firstDayOfNewMonth)
                Log.d(TAG,
                    "Month was scrolled to: $firstDayOfNewMonth")
            }
        })
    }

    companion object {
        private const val TAG = "CalendarActivity"
    }
}