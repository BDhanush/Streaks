package com.example.streaks.db
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.streaks.model.Streak

@Dao
interface StreakDao {
    @Query("SELECT * FROM streak")
    fun getAll(): LiveData<List<Streak>>

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
//
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(streak: Streak):Long

    @Delete
    fun delete(streak: Streak)
}