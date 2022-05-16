package kr.ac.kumoh.s20170419.mygradecalculator

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule")
data class week (
    @PrimaryKey(autoGenerate = true)
    val monday: String,
    val tuesday: String,
    val wednseday: String,
    val thursday: String,
    val firday: String
)

