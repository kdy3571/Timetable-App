package kr.ac.kumoh.s20170419.mygradecalculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ViewModel(application: Application): AndroidViewModel(application) {
    data class RequiredSubject(
        val id: Int,
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
}