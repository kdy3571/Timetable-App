package kr.ac.kumoh.s20170419.mygradecalculator

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel

class InnerDBViewmodel(context: Application): AndroidViewModel(context) {
    private val db = ScheduleDatabase.getDatabase(context)!!.weekDao()

    fun connect(subject: ViewModel.Subject){
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
        val result:Boolean = temp.contains(data.code)
        if (result == false){
            db.insert(data)
        }
        val temp2 = db.getAll()
        Log.i("database", temp2.toString())
    }

    fun getdata() : List<weekstateminimal>{
        val data = db.getDATA()
        Log.i("database", data[0].time.toString())
        return data
    }

//    fun get(): List<weekstate> {
//        val temp = db.getAll()
//        val temp2 = db.getID()
//        Log.i("database", temp.toString())
//        Log.i("database", temp.toString())
//        return temp
//    }
}