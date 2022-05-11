package kr.ac.kumoh.s20170419.mygradecalculator

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray
import org.json.JSONObject

class ViewModel(application: Application): AndroidViewModel(application) {
    companion object{
        const val QUEUE_TAG = "VolleyRequest"
    } // 서버를 사용하기 위해 requestQueue 사용
    private lateinit var mQueue: RequestQueue
    data class Subject( //데이터베이스를 위한 data class
        val requiredsubject_id: String?,
        val majorselection_id: String?,
        val geselection_id: String?,
        val name: String, //강의 이름
        val professor: String, //교수님 이름
        val code: String,// 학과코드
        val room: String,// 강의실
        val time: String,// 강의시간
        val division: String, //필수, 선택
        val credit: String, //학점
        val grade: String, //학년
        val semester: String //학기
    )
    val list = MutableLiveData<ArrayList<Subject>>()
    private val R_subject = ArrayList<Subject>()
    init {
        list.value = R_subject
        mQueue = VolleyRequest.getInstance(application).requestQueue
    }
    fun requestList(grade: String, semester: String, division: String) {
        val url = "https://expresssongdb-ocmes.run.goorm.io/?t=1651835082540"
        //DB 서버주소
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {
                R_subject.clear()
                parseSubjectJSON(it, grade, semester, division)
                list.value = R_subject
            },
            {
                Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
            }
        )
        request.tag = QUEUE_TAG
        mQueue.add(request) //request 전송
    }
    override fun onCleared() {
        super.onCleared()
        mQueue.cancelAll(QUEUE_TAG)
    }
    fun getR_subject(i : Int) = R_subject[i]
    fun get_gid(i : Int) = R_subject[i].geselection_id
    fun getSize() = R_subject.size
    private fun parseSubjectJSON(items: JSONArray, Grade: String, Semester: String, Division: String){
        for (i in 0 until items.length()){ //JSONArray를 받아와 해당되는 변수에 넣음
            val item: JSONObject = items.getJSONObject(i)
            val requiredsubject_id = item.getString("requiredsubject_id")
            val majorselection_id = item.getString("majorselection_id")
            val geselection_id = item.getString("geselection_id")
            val name = item.getString("name")
            val professor = item.getString("professor")
            val code = item.getString("code")
            val room = item.getString("room")
            val time = item.getString("time")
            val division = item.getString("division")
            val credit = item.getString("credit")
            val grade = item.getString("grade")
            val semester = item.getString("semester")
            //spinner를 위한 if 구문
            if(Grade == grade && Semester == semester) {
                if(Division == "필수" && requiredsubject_id != "null") {
                    R_subject.add(
                        Subject(
                            requiredsubject_id, majorselection_id, geselection_id, name,
                            professor, code, room, time, division, credit, grade, semester
                        )
                    )
                }
                else if(Division == "전공선택" && majorselection_id != "null") {
                    R_subject.add(
                        Subject(
                            requiredsubject_id, majorselection_id, geselection_id, name,
                            professor, code, room, time, division, credit, grade, semester
                        )
                    )
                }
                else if(Division == "교양선택" && geselection_id != "null"){
                    R_subject.add(
                        Subject(
                            requiredsubject_id, majorselection_id, geselection_id, name,
                            professor, code, room, time, division, credit, grade, semester
                        )
                    )
                }
            }
        }
    }
}