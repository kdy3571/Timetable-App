package kr.ac.kumoh.s20170419.mygradecalculator

import android.content.Context
import androidx.room.*

@Entity(tableName = "Schedule")
data class WeekState(
    @PrimaryKey()
    val key: String,
    val gs: String?,
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
    val semester  : String?,
    val red : Int,
    val blue : Int,
    val green : Int
)

@Entity(tableName = "GP")
data class GPState(
    @PrimaryKey(autoGenerate = true)
    val key : Int,
    val gs : String?,
    var subject: String?,
    var name: String?,
    var credit: String?,
    var gp: String?
)

@Dao
interface WeekDao {
    @Query("SELECT * FROM schedule")
    fun getAll() : List<WeekState>

    @Query("SELECT * FROM schedule WHERE gs = :gs")
    fun getSubject(gs: String) : List<WeekState>

    @Query("SELECT code FROM schedule")
    fun getCODE() : List<String>

    @Query("DELETE FROM schedule")
    fun deleteALL()

    @Query("DELETE FROM schedule where gs = :gs")
    fun delete(gs: String)

    @Query("DELETE FROM schedule WHERE name = :name AND gs = :gs")
    fun deletename(name: String?, gs: String?)

    @Insert
    fun insert(vararg weekstate: WeekState)
}

@Dao
interface GPDao {
    @Query("SELECT * FROM GP WHERE gs = :gs")
    fun getInfo(gs: String) : List<GPState>

    @Query("DELETE FROM GP where gs = :gs")
    fun delete(gs: String)

    @Query("SELECT * FROM GP")
    fun getAll() : List<GPState>

    @Update
    fun update(vararg GPAState: GPState)

    @Insert
    fun insert(vararg GPAState: GPState)
}

@Database(entities = [WeekState::class], version = 1, exportSchema = false)
abstract class ScheduleDatabase : RoomDatabase() {
    abstract fun weekDao(): WeekDao
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

@Database(entities = [GPState::class], version = 1, exportSchema = false)
abstract class GPDatabase : RoomDatabase() {
    abstract fun gpDao(): GPDao
    companion object {
        private var database: GPDatabase? = null
        fun getDatabase(context: Context): GPDatabase? {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    GPDatabase::class.java,
                    "gpa.db"
                ).fallbackToDestructiveMigration().build()
            }
            return database
        }
    }
}
