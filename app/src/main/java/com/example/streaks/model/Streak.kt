package com.example.streaks.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Streak(var title:String, val startTime:Long, @PrimaryKey(autoGenerate = true) var id:Long = 0)
{

}