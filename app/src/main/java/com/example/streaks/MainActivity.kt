package com.example.streaks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.streaks.adapter.StreakItemAdapter
import com.example.streaks.databinding.ActivityMainBinding
import com.example.streaks.databinding.AddStreakBinding
import com.example.streaks.db.StreakDao
import com.example.streaks.db.StreakDatabase
import com.example.streaks.model.Streak
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var database: StreakDatabase
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        database =  Room.databaseBuilder(
            applicationContext,
            StreakDatabase::class.java,
            StreakDatabase.NAME
        ).allowMainThreadQueries().build()

        val streakDao: StreakDao = database.streakDao()

        val dataset:MutableList<Streak> = streakDao.getAll().toMutableList()
        val adapter = StreakItemAdapter(dataset)
        binding.streakRecyclerView.adapter = adapter

        // to do
        binding.streakRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.addButton.setOnClickListener {
            val addStreakBinding = AddStreakBinding.inflate(layoutInflater)
            val addStreakView = addStreakBinding.root
            val alertDialog = MaterialAlertDialogBuilder(this)
                .setTitle("New Streak")
                .setView(addStreakView)
                .setPositiveButton("Add") { dialog, which ->
                    val curTime = Date().time
                    // to do
                    val title = addStreakBinding.title.text.toString()
                    adapter.addStreak(Streak(title,curTime),this)
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .create()
            alertDialog.show()
        }

    }
}