package com.example.streaks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.streaks.databinding.ActivityStreakBinding
import com.example.streaks.db.StreakDatabase
import com.example.streaks.model.Streak
import java.util.*
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit

class StreakActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStreakBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStreakBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val title=intent.getStringExtra("title")
        setTitle(title)
        var startTime=intent.getLongExtra("startTime",0)
        val id=intent.getLongExtra("id",0)


        binding.daysToggle.setOnClickListener {
            val dif = getDif(startTime)
            val count = getDifInDays(dif)
            binding.daysAndHours.text = if(count==1L) resources.getString(R.string.day) else resources.getString(R.string.days)
            binding.count.text = count.toString()
        }
        binding.hoursToggle.setOnClickListener {
            val dif = getDif(startTime)
            val count = getDifInHours(dif)
            binding.daysAndHours.text = if(count==1L) resources.getString(R.string.hour) else resources.getString(R.string.hours)
            binding.count.text = count.toString()
        }

        binding.daysToggle.performClick()
        binding.resetButton.setOnClickListener {
            // to do
            val database =  Room.databaseBuilder(
                applicationContext,
                StreakDatabase::class.java,
                StreakDatabase.NAME
            ).allowMainThreadQueries().build()
            title?.let { it1 -> Streak(it1, Date().time,id) }?.let { it2 -> database.streakDao().insert(it2) }
            startTime = Date().time
            if(binding.toggleButtons.checkedButtonId==R.id.daysToggle)
            {
                binding.daysToggle.performClick()
            }else if(binding.toggleButtons.checkedButtonId==R.id.hoursToggle)
            {
                binding.hoursToggle.performClick()
            }
        }


    }

    fun getDif(startTime:Long):Long
    {
        return Date().time-startTime
    }

    fun getDifInDays(dif:Long):Long
    {
        return dif.milliseconds.toLong(DurationUnit.DAYS)
    }

    fun getDifInHours(dif:Long):Long
    {
        return dif.milliseconds.toLong(DurationUnit.HOURS)
    }
}
