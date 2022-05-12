import android.util.Log
import kr.ac.kumoh.s20170419.mygradecalculator.ViewModel
import java.lang.Math.pow
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log10
import kotlin.math.pow


fun auto_schedule(
    slist: Array<Array<String?>>, credit: ArrayList<Int>, s_subject: ArrayList<String>, e_subject: ArrayList<String>,
    rest: ArrayList<Int>, ge: ArrayList<Int>, model: ViewModel
) {

    val em_list = model.requestList("4", "1", "전공선택")
    val ge_list = model.requestList("4", "1", "교양선택")
    val cs_list = model.requestList("4", "1", "필수")

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

    while (ge[0] != 0) { // 교양을 넣기를 희망한다면
        if (subject_add(ge_list, slist, credit) == 1)
            ge[0] = ge[0] - 1
    }
    Log.d("ge", ge[0].toString())

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

fun subject_add(
    list: ArrayList<ViewModel.Subject>,
    slist: Array<Array<String?>>,
    credit: ArrayList<Int>): Int {
    val random = Random()
    val num = random.nextInt(list.size)

    if (credit[0] - list[num].credit.toInt() > 0) {// list로 불러온 과목의 학점 체크
        val time = list[num].time.split(", ")
            Log.d("time", time.toString())
            Log.d("code", list[num].code)
        for (t in time) {
            val n = log10(t.toDouble()).toInt().toDouble()
            Log.d("slist", slist[(t.toInt() / 10.0.pow(n)).toInt()][(t.toInt() % 10.0.pow(n)).toInt()].isNullOrEmpty().toString())
            when (slist[(t.toInt() / 10.0.pow(n)).toInt()][(t.toInt() % 10.0.pow(n)).toInt()].isNullOrEmpty()) {
                true -> slist[((t.toInt() / 10.0.pow(n)).toInt())][(t.toInt() % 10.0.pow(n)).toInt()] = list[num].code
                false -> {
                    list.removeAt(num)
                    return 0
                }
            }
            Log.d("slist", slist[((t.toInt() / 10.0.pow(n)).toInt())][(t.toInt() % 10.0.pow(n)).toInt()].toString())
        }
    }
    return 1
}
