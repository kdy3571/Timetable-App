package kr.ac.kumoh.s20170419.mygradecalculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class InnerDBViewModel(context: Application) : AndroidViewModel(context) {
    private val weekDB = ScheduleDatabase.getDatabase(context)!!.weekDao()
    private val gpDB = GPDatabase.getDatabase(context)!!.gpDao()
    var red: Int = 0
    var blue: Int = 0
    var green: Int = 0

    fun connect(gs: String, subject: ViewModel.Subject) {
        val key = gs + subject.code
        randomColor()
        val data = WeekState(
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
            subject.semester,
            red,
            blue,
            green
        )
        weekDB.insert(data)
    }

    fun connect(db: WeekState) {
        val data = GPState(
            0,
            db.gs,
            db.subject,
            db.name,
            db.credit,
            "A+"
        )
        gpDB.insert(data)
    }

    fun connect(gs: String, name: String?, credit: String?, gp: String?, check: Boolean) {
        val subject = if (check) "전공" else null
        val data = GPState(
            0,
            gs,
            subject,
            name,
            credit,
            gp
        )
        gpDB.insert(data)
    }

    fun getInfo(): List<GPState> {
        return gpDB.getAll()
    }

    fun getInfo(gs: String): List<GPState> {
        return gpDB.getInfo(gs)
    }

    fun delInfo(gs: String) {
        gpDB.delete(gs)
    }

    fun getSubject(gs: String): List<WeekState> {
        return weekDB.getSubject(gs)
    }

    fun resetDB(gs: String){
        weekDB.delete(gs)
    }

    fun deleteDB(name : String?, gs: String?){
        weekDB.deletename(name, gs)
    }

    fun update(db: GPState) {
        gpDB.update(db)
    }

    private fun randomColor() {
        red = (Math.random() * 255).toInt()
        blue = (Math.random() * 255).toInt()
        green = (Math.random() * 255).toInt()
    }
}