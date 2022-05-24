package kr.ac.kumoh.s20170419.mygradecalculator

import android.content.Context
import androidx.core.graphics.scaleMatrix
import androidx.room.*

@Entity(tableName = "schedule")
data class weekstate(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val college : String?,
    val subject  : String?,
    val name : String?,
    val professor  : String?,
    val code  : String?,
    val room  : String?,
    val time  : String?,
    val division  : String?,
    val credit  : String?,
    val grade  : String?,
    val semester  : String?
)

data class weekstateminimal(
    val name : String?,
    val time  : String?
)

@Dao
interface weekDao {
    @Query("SELECT * FROM schedule")
    fun getAll() : List<weekstate>

    @Query("SELECT code FROM schedule")
    fun getCODE() : List<String>

    @Query("SELECT name, time FROM schedule")
    fun getDATA() : MutableList<weekstateminimal>

    @Insert
    fun insert(vararg weekstate: weekstate)
}

@Database(entities = [weekstate::class], version = 1, exportSchema = false)
abstract class ScheduleDatabase : RoomDatabase() {
    abstract fun weekDao(): weekDao

    companion object {
        private var database: ScheduleDatabase? = null

        fun getDatabase(context: Context): ScheduleDatabase? {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    ScheduleDatabase::class.java,
                    "week.db"
                ).fallbackToDestructiveMigration().build()
            }
            return database
        }
    }
}
