package com.example.streaks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.streaks.databinding.ActivityStreakBinding

class StreakActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStreakBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStreakBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val title=intent.getStringExtra("title")
        val count=intent.getLongExtra("count",0)

        actionBar?.title = title
        binding.count.text = count.toString()
        if(count==1L)
        {
            binding.days.text = resources.getString(R.string.day)
        }

        binding.resetButton.setOnClickListener {
            // to do
        }

    }
}