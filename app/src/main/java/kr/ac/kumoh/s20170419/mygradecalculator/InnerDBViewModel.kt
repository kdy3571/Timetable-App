package kr.ac.kumoh.s20170419.mygradecalculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class InnerDBViewModel(context: Application): AndroidViewModel(context) {
    private val dao = ScheduleDatabase.getDatabase(context)!!.weekDao()
}