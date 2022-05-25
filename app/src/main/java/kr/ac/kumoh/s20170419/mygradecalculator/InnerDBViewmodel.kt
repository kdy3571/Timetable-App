package kr.ac.kumoh.s20170419.mygradecalculator

import android.app.Application
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel

class InnerDBViewmodel(context: Application) : AndroidViewModel(context) {
    private val weekdb = ScheduleDatabase.getDatabase(context)!!.weekDao()
    private val userdb = UserDatabase.getDatabase(context)!!.UserDao()
    val name: ArrayList<String> = arrayListOf()
    val time: ArrayList<String> = arrayListOf()
//    var red: Int = 0
//    var blue: Int = 0
//    var green: Int = 0
//    var redList: ArrayList<Int> = arrayListOf()
//    var blueList: ArrayList<Int> = arrayListOf()
//    var grrenList: ArrayList<Int> = arrayListOf()

    fun connect(subject: ViewModel.Subject) {
        var temp = weekdb.getCODE()
        //randomColor()
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
            weekdb.insert(data)
        }
        val temp2 = weekdb.getAll()
        Log.i("database", temp2.toString())
    }

    fun connect(){

    }

    fun getweekdata() {
        var data: MutableList<weekstateminimal> = arrayListOf()
        data = weekdb.getDATA()
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

    fun resetDB(){
        weekdb.deleteALL()
        weekdb.resetID()
    }

//    fun getcolor(): ArrayList<Int> {
//        var data: MutableList<weekcolor> = db.getCOLOR()
//        Log.i("test1", data.size.toString())
//        if (data.size != 0) {
//            for (i in 0 until data.size) {
//                redList.add(data[i].red)
//                blueList.add(data[i].blue)
//                grrenList.add(data[i].green)
//            }
//        }
//    }
//
//    fun getred(): Int {
//        var data: weekcolor = db.getCOLOR()
//        red = data.red
//        return red
//    }
//
//    fun getblue(): Int {
//        var data: weekcolor = db.getCOLOR()
//        blue = data.blue
//        return blue
//    }
//
//    fun getgreen(): Int {
//        var data: weekcolor = db.getCOLOR()
//        green = data.green
//        return green
//    }
//
//    fun randomColor() {
//        red = (Math.random() * 255).toInt()
//        blue = (Math.random() * 255).toInt()
//        green = (Math.random() * 255).toInt()
//    }
}