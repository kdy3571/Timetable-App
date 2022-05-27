package kr.ac.kumoh.s20170419.mygradecalculator

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel

class InnerDBViewmodel(context: Application) : AndroidViewModel(context) {
    private val weekdb = ScheduleDatabase.getDatabase(context)!!.weekDao()
    val name: ArrayList<String> = arrayListOf()
    val time: ArrayList<String> = arrayListOf()
//    var red: Int = 0
//    var blue: Int = 0
//    var green: Int = 0
//    var redList: ArrayList<Int> = arrayListOf()
//    var blueList: ArrayList<Int> = arrayListOf()
//    var grrenList: ArrayList<Int> = arrayListOf()

    fun connect(gs: String,subject: ViewModel.Subject) {
        val key = gs + subject.code
        val data = weekstate(
            key,
            gs,
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
        weekdb.insert(data)
        val temp2 = weekdb.getAll()
        Log.i("database", temp2.toString())
    }


    fun getweekdata(gs: String) {
        var data: MutableList<weekstateminimal> = weekdb.getDATA(gs)
        name.clear()
        time.clear()
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

    fun gettime(): ArrayList<String> {
        return time
    }

    fun resetDB(gs: String){
        weekdb.delete(gs)
    }

    fun deleteDB(name : String){
        weekdb.deletename(name)
    }
}