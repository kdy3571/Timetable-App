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

@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val college : String?,
    val major : String?,
    val grade: String?,
    val semester: String?
)

data class weekstateminimal(
    val name : String?,
    val time  : String?
)

//data class weekcolor(
//    val red : Int,
//    val blue : Int,
//    val green : Int
//)

@Dao
interface weekDao {
    @Query("SELECT * FROM schedule WHERE gs = :gs")
    fun getAll(gs: String) : List<weekstate>

    @Query("SELECT code FROM schedule")
    fun getCODE() : List<String>

    @Query("SELECT name, time FROM schedule WHERE gs = :gs")
    fun getDATA(gs: String) : MutableList<weekstateminimal>

    @Query("DELETE FROM schedule")
    fun deleteALL()

    @Query("DELETE FROM schedule where gs = :gs")
    fun delete(gs: String)


    @Query("DELETE FROM schedule WHERE name = :name AND gs = :gs")
    fun deletename(name: String?, gs: String?)

//    @Query("UPDATE schedule SET id = 0: + 1")
//    fun resetID()

//    @Query("SELECT red, blue, green FROM schedule")
//    fun getCOLOR() : MutableList<weekcolor>

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
