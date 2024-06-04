package com.example.streaks.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "streak_table")
class Streak(val title:String, val startTime:Long) {
    @PrimaryKey(autoGenerate = true) var key:Int = 0
}