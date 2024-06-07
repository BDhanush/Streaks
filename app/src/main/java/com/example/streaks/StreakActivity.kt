package com.example.streaks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.streaks.databinding.ActivityStreakBinding
import com.example.streaks.db.StreakDatabase
import com.example.streaks.model.Streak
import java.util.*

class StreakActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStreakBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStreakBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val title=intent.getStringExtra("title")
        setTitle(title)
        val count=intent.getLongExtra("count",0)
        val id=intent.getIntExtra("id",0)

        actionBar?.title = title
        binding.count.text = count.toString()
        binding.days.text = if(count==1L) resources.getString(R.string.day) else resources.getString(R.string.days)

        binding.resetButton.setOnClickListener {
            // to do
            val database =  Room.databaseBuilder(
                applicationContext,
                StreakDatabase::class.java,
                StreakDatabase.NAME
            ).allowMainThreadQueries().build()
            title?.let { it1 -> Streak(it1, Date().time,id) }?.let { it2 -> database.streakDao().insert(it2) }
            binding.count.text=0.toString()
        }

    }
}