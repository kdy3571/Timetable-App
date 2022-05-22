package kr.ac.kumoh.s20170419.mygradecalculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class InnerDBViewmodel(context: Application): AndroidViewModel(context) {
    private val db = ScheduleDatabase.getDatabase(context)!!.weekDao()

    fun connect(subject: ViewModel.Subject){
        val data = db.insert()
    }
}