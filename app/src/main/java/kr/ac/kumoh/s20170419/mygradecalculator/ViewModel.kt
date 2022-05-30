package kr.ac.kumoh.s20170419.mygradecalculator

import android.app.Application
import android.hardware.Camera
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable

class ViewModel(application: Application): AndroidViewModel(application) {
    companion object {
        const val QUEUE_TAG = "VolleyRequest"
        private val R_subject = ArrayList<Subject>()
    }

    private lateinit var mQueue: RequestQueue

    data class Subject(
        val college: String,
        val subject: String,
        val name: String,
        val professor: String,
        val code: String,
        val room: String,
        val time: String,
        val division: String,
        val credit: String,
        val grade: String,
        val semester: String
    ): Serializable

    val list = MutableLiveData<ArrayList<Subject>>()
    val filteredList = MutableLiveData<ArrayList<Subject>>()

    init {
        list.value = R_subject
        mQueue = VolleyRequest.getInstance(application).requestQueue
    }

    fun requestList(College: String, Major: String, Grade: String, Semester: String, Subject: String, Division: String) {
        val url = "https://expresssongdb-ocmes.run.goorm.io/?t=1651835082540"

        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {
                R_subject.clear()
                parseSubjectJSON(it, College, Major, Grade, Semester, Subject, Division)
                list.value = R_subject
            },
            {
                Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
            }
        )
        request.tag = QUEUE_TAG
        mQueue.add(request)
    }

    override fun onCleared() {
        super.onCleared()
        mQueue.cancelAll(QUEUE_TAG)
    }

    fun getFilter(name: String): MutableLiveData<ArrayList<Subject>> {
        if (name == "")
            filteredList.value = list.value
        else {
            filteredList.value = list.value?.filter {
                it.name.isNotEmpty() && it.name == name
            } as ArrayList<Subject>?
        }
        return filteredList
    }

    fun getR_subject(): ArrayList<Subject> {
        return R_subject
    }
    
    fun getR_subject(i: Int) = R_subject[i]
    fun getSize() = R_subject.size
    private fun parseSubjectJSON(items: JSONArray, College: String, Major: String, Grade: String, Semester: String, Subject: String, Division: String) {
        for (i in 0 until items.length()) {
            val item: JSONObject = items.getJSONObject(i)
            val college = item.getString("college")
            val subject = item.getString("subject")
            val name = item.getString("name")
            val professor = item.getString("professor")
            val code = item.getString("code")
            val room = item.getString("room")
            val time = item.getString("time")
            val division = item.getString("division")
            val credit = item.getString("credit")
            val grade = item.getString("grade")
            val semester = item.getString("semester")

            if (College == college && Major == "컴퓨터공학과" && Semester == semester) {
                if (Grade == "전체" && Subject == "전체" && Division == "전체") {
                    R_subject.add(Subject(college, subject, name, professor, code, room, time, division, credit, grade, semester))
                } else if (Grade == "전체" && Subject == "전체" && Division == division) {
                    R_subject.add(Subject(college, subject, name, professor, code, room, time, division, credit, grade, semester))
                } else if (Grade == "전체" && Subject == Subject && Division == "전체") {
                    R_subject.add(Subject(college, subject, name, professor, code, room, time, division, credit, grade, semester))
                } else if (Grade == "전체" && Subject == Subject && Division == division) {
                    R_subject.add(Subject(college, subject, name, professor, code, room, time, division, credit, grade, semester))
                } else if (Grade == grade && Subject == "전체" && Division == "전체") {
                    R_subject.add(Subject(college, subject, name, professor, code, room, time, division, credit, grade, semester))
                } else if (Grade == grade && Subject == "전체" && Division == division) {
                    R_subject.add(Subject(college, subject, name, professor, code, room, time, division, credit, grade, semester))
                } else if (Grade == grade && Subject == subject && Division == "전체") {
                    R_subject.add(Subject(college, subject, name, professor, code, room, time, division, credit, grade, semester))
                } else if (Grade == grade && Subject == Subject && Division == division) {
                    R_subject.add(Subject(college, subject, name, professor, code, room, time, division, credit, grade, semester))
                }
            }
        }
    }
    
    fun search(searchData: String, searchType: String) {
        for (i in R_subject) {
            when(searchType) {
               "name" -> {
                   if (!i.name.contains(searchData))
                      R_subject.remove(i)
               }
               "professor" -> {
                   if (!i.professor.contains(searchData))
                       R_subject.remove(i)
               }
               "code" -> {
                   if (!i.code.contains(searchData))
                       R_subject.remove(i)
               }
            }
        }
    }
}
