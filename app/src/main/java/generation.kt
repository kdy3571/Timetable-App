import android.util.Log
import kr.ac.kumoh.s20170419.mygradecalculator.ViewModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log10


fun auto_schedule(
    slist: Array<Array<String?>>, credit: ArrayList<Int>, s_subject: ArrayList<String>, e_subject: ArrayList<String>,
    rest: ArrayList<Int>, ge: ArrayList<Int>, model: ViewModel
) {

    val em_list = model.requestList("4", "1", "전공선택")
    val ge_list = model.requestList("4", "1", "교양선택")
    val cs_list = model.requestList("4", "1", "필수")

//    while (cs_list.isNotEmpty()) { // 배열에서 필수과목(division) 불러오기 4-1학기가 없을때까지
//        //slist[DB.time/10][DB.time%10] = DB.code
//        //credit -= DB.credit
//        // 배열.delete(DB.code), code일부분이 일치 하는게 있으면?
//    }
//
//    if (rest.isNotEmpty()) { // 공강일 존재
//        for(i in rest) {
//            for (j in 0..11)
//                if (slist[i][j] == null)
//                //종료, 공강일 불가능 반환
//            slist[i] = arrayOf("Rest")
//        }
//    }

    while (ge[0] != 0) {
        if (subject_add(ge_list, slist, "교양", credit) == 1)
            ge[0] = ge[0] - 1
        Log.d("Ge", ge[0].toString())
    }

//    while (credit != 0) {
//        if (em_list.isNotEmpty())
//            subject_add(em_list, slist, "전공", credit)
//        else
//            if (ge_list.isNotEmpty())
//                subject_add(ge_list, slist, "교양", credit)
//            else
//        break
//    }
}

fun subject_add(
    list: ArrayList<ViewModel.Subject>,
    slist: Array<Array<String?>>,
    division: String,
    credit: ArrayList<Int>
): Int {
    val random = Random()
    val num = random.nextInt(list.size)
    val time: List<String>

    if (division == "교양") {
        if (credit[0] - list[num].credit.toInt() > 0) {// list로 불러온 과목의 학점 체크
            time = list[num].time.split(",")
            Log.d("time", time.toString())
            Log.d("code", list[num].code)
            Log.d("log", log10(time[0].toDouble()).toString())
            for (t in time) {
                Log.d("t", t)
                if (log10(t.toDouble()).toInt() + 1 != 3) {
                    if (slist[t.toInt() / 10][t.toInt() % 10] == null) {
                        slist[t.toInt() / 10][t.toInt() % 10] = list[num].code
                        slist[t.toInt() / 10][t.toInt() % 10]?.let { Log.d("slist", it) }
                    }
                    else {
                        list.removeAt(num)
                        return 0
                    }
                } else {
                    if (slist[t.toInt() / 10][t.toInt() % 10] == null) {
                        slist[t.toInt() / 100][t.toInt() % 100] = list[num].code
                        slist[t.toInt() / 100][t.toInt() % 100]?.let { Log.i("slist", it) }
                    }
                    else {
                        list.removeAt(num)
                        return 0
                    }
                }
            }
            return 1
            // slist에 추가;
            // credit -= list[num].credit
            // list[num] 삭제
        } else {
            //list[num] 삭제
            return 0
        }
    }
//    } else if (division == "전공") {
//        if (credit[0] - list[num].credit.toInt() > 0) {// list로 불러온 과목의 학점 체크
//            // slist에 추가;
//            // credit -= list[num].credit
//            // list[num] 삭제
//        } else {
//            //list[num] 삭제
//        }
//    }
    return 1
}