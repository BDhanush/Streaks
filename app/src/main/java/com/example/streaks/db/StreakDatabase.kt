package com.example.streaks.db
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.streaks.model.Streak

@Database(entities = [Streak::class], version = 1)
abstract class StreakDatabase : RoomDatabase() {
    companion object{
        val NAME = "StreakDatabase"
    }
    abstract fun streakDao(): StreakDao

}