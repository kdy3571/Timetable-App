package kr.ac.kumoh.s20170419.mygradecalculator

import android.content.Context
import androidx.room.*

@Entity(tableName = "Schedule")
data class weekstate(
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
    val semester  : String?
)

@Entity(tableName = "GP")
data class gpstate(
    @PrimaryKey(autoGenerate = true)
    val key : Int,
    val gs : String?,
    val subject: String?,
    val name: String?,
    val credit: String?,
    val gp: String?
)

//data class weekcolor(
//    val red : Int,
//    val blue : Int,
//    val green : Int
//)

@Dao
interface weekDao {
    @Query("SELECT * FROM schedule")
    fun getAll() : List<weekstate>

    @Query("SELECT * FROM schedule WHERE gs = :gs")
    fun getAll(gs: String) : List<weekstate>

    @Query("SELECT code FROM schedule")
    fun getCODE() : List<String>

    @Query("DELETE FROM schedule")
    fun deleteALL()

    @Query("DELETE FROM schedule where gs = :gs")
    fun delete(gs: String)


    @Query("DELETE FROM schedule WHERE name = :name AND gs = :gs")
    fun deletename(name: String?, gs: String?)

//    @Query("SELECT red, blue, green FROM schedule")
//    fun getCOLOR() : MutableList<weekcolor>

    @Insert
    fun insert(vararg weekstate: weekstate)
}

@Dao
interface gpDao {
    @Query("SELECT * FROM GP WHERE gs = :gs")
    fun getInfo(gs: String) : List<gpstate>

    @Query("DELETE FROM GP where gs = :gs")
    fun delete(gs: String)

    @Query("SELECT * FROM GP WHERE gs = :gs")

    @Insert
    fun insert(vararg gpastate: gpstate)
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

@Database(entities = [gpstate::class], version = 1, exportSchema = false)
abstract class GPDatabase : RoomDatabase() {
    abstract fun gpDao(): gpDao

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
