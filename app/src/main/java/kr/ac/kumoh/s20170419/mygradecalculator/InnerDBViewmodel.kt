package kr.ac.kumoh.s20170419.mygradecalculator

import android.app.Application
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel

class InnerDBViewmodel(context: Application): AndroidViewModel(context) {
    private val db = ScheduleDatabase.getDatabase(context)!!.weekDao()
    val name: ArrayList<String> = arrayListOf()
    val time: ArrayList<String> = arrayListOf()

    fun connect(subject: ViewModel.Subject) {
        var temp = db.getCODE()
        val data = weekstate(
            0,
            subject.college,
            subject.subject,
            subject.name,
            subject.professor,
            subject.code,
            subject.room,
            subject.time,
            subject.division,
            subject.credit,
            subject.grade,
            subject.semester
        )
        val result: Boolean = temp.contains(data.code)
        if (result == false) {
            db.insert(data)
        }
        val temp2 = db.getAll()
        Log.i("database", temp2.toString())
    }

    fun getdata() {
        var data: MutableList<weekstateminimal> = arrayListOf()
        data = db.getDATA()
        Log.i("test1", data.size.toString())
        if (data.size != 0) {
            for (i in 0 until data.size) {
                name.add(data[i].name.toString())
                time.add(data[i].time.toString())
            }
        }
    }

    fun getname(): ArrayList<String> {
        return name
    }

    fun gettime(): ArrayList<String>{
        return time
    }
}
//    fun get(): List<weekstate> {
//        val temp = db.getAll()
//        val temp2 = db.getID()
//        Log.i("database", temp.toString())
//        Log.i("database", temp.toString())
//        return temp
//    }