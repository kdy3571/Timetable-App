package kr.ac.kumoh.s20170419.mygradecalculator

import android.content.Context
import androidx.core.graphics.scaleMatrix
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
    @Query("SELECT * FROM schedule")
    fun getAll() : List<weekstate>

    @Query("SELECT code FROM schedule")
    fun getCODE() : List<String>

    @Query("SELECT name, time FROM schedule WHERE gs = :gs")
    fun getDATA(gs: String) : MutableList<weekstateminimal>

    @Query("DELETE FROM schedule")
    fun deleteALL()

    @Query("DELETE FROM schedule where gs = :gs")
    fun delete(gs: String)

//    @Query("UPDATE schedule SET id = 0: + 1")
//    fun resetID()

//    @Query("SELECT red, blue, green FROM schedule")
//    fun getCOLOR() : MutableList<weekcolor>

    @Insert
    fun insert(vararg weekstate: weekstate)
}

@Dao
interface UserDao{
    @Insert
    fun insert(vararg User : User)

    @Query("SELECT * FROM User")
    fun getALL() : List<User>
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

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun UserDao() : UserDao

    companion object {
        private var database: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase? {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "User.db"
                ).fallbackToDestructiveMigration().build()
            }
            return database
        }
    }
}
