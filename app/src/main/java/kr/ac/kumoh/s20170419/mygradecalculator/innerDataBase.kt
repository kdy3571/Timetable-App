package kr.ac.kumoh.s20170419.mygradecalculator

import android.content.Context
import androidx.core.graphics.scaleMatrix
import androidx.room.*

@Entity(tableName = "schedule")
data class weekstate(
    @PrimaryKey(autoGenerate = true) val id:Int,
    var monday: String? = null,
    var tuesday: String? = null,
    var wednesday: String? = null,
    var thursday: String? = null,
    var friday: String? = null,
)

@Dao
interface weekDao {
    @Query("INSERT INTO schedule VALUES(:id, :monday, :tuesday, :wednesday, :thursday, :friday)")
    fun insertall(id: Int, monday: String?, tuesday: String?, wednesday: String?, thursday: String?, friday: String?)

    @Query("SELECT * FROM SCHEDULE")
    fun selectALL(): List<weekstate>

    @Query("SELECT monday FROM schedule")
    fun selectmonday(): String?
    @Query("SELECT tuesday FROM schedule")
    fun selecttuesday(): String?
    @Query("SELECT wednesday FROM schedule")
    fun selectwednesday(): String?
    @Query("SELECT thursday FROM schedule")
    fun selectthursday(): String?
    @Query("SELECT friday FROM schedule")
    fun selectfriday(): String?

    @Insert
    fun insert(vararg weekstate: weekstate)

    @Delete
    fun delete(weekstate: weekstate)
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
                ).build()
            }
            return database
        }
    }
}
