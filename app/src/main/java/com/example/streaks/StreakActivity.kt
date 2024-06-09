package com.example.streaks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.streaks.databinding.ActivityStreakBinding
import com.example.streaks.model.Streak
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

        refreshCount()
        binding.resetButton.setOnClickListener {
            // to do
            val alertDialog = MaterialAlertDialogBuilder(this)
                .setTitle("Reset Streak")
                .setMessage("Your streak will be reset, you'll start from 0")
                .setPositiveButton("Reset") { dialog, which ->

                    val streakDao = MainActivity.database.streakDao()
                    title?.let { it1 -> Streak(it1, Date().time,id) }?.let { it2 -> streakDao.insert(it2) }
                    startTime = Date().time
                    refreshCount()
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .create()
            alertDialog.show()
        }


    }

    fun refreshCount()
    {
        if(binding.toggleButtons.checkedButtonId==R.id.daysToggle)
        {
            binding.daysToggle.performClick()
        }else if(binding.toggleButtons.checkedButtonId==R.id.hoursToggle)
        {
            binding.hoursToggle.performClick()
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
