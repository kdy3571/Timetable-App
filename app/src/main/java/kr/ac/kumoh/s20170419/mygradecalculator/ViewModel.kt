package kr.ac.kumoh.s20170419.mygradecalculator

import android.app.Application
import android.text.Editable
import android.widget.Filter
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray
import org.json.JSONObject

class ViewModel(application: Application): AndroidViewModel(application) {
    companion object {
        const val QUEUE_TAG = "VolleyRequest"
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
    )

    val list = MutableLiveData<ArrayList<Subject>>()
    val filteredList = MutableLiveData<ArrayList<Subject>>()
    private val R_subject = ArrayList<Subject>()

    init {
        list.value = R_subject
        mQueue = VolleyRequest.getInstance(application).requestQueue
    }

    fun requestList(grade: String, semester: String, Area: String?) {
        val url = "https://expresssongdb-ocmes.run.goorm.io/?t=1651835082540"

        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {
                R_subject.clear()
                parseSubjectJSON(it, grade, semester, Area)
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

    fun getR_subject() = R_subject
    fun getR_subject(i: Int) = R_subject[i]
    fun getSize() = R_subject.size
    private fun parseSubjectJSON(items: JSONArray, Grade: String, Semester: String, Area: String?) {
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

            if (Grade == grade && Semester == semester) {
                if (Area == null) {
                    R_subject.add(Subject(college, subject, name, professor, code, room, time, division, credit, grade, semester))
                } else {
                    var token = Area.chunked(2)
                    if(token[0] == division)
                        R_subject.add(Subject(college, subject, name, professor, code, room, time, division, credit, grade, semester))
                    else if(subject == token[0] && division == token[1])
                        R_subject.add(Subject(college, subject, name, professor, code, room, time, division, credit, grade, semester))
                }
            }
        }
    }
}