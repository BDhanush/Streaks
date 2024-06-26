package com.dhanush.streaks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.dhanush.streaks.adapter.StreakItemAdapter
import com.dhanush.streaks.databinding.ActivityMainBinding
import com.dhanush.streaks.databinding.AddStreakBinding
import com.dhanush.streaks.db.StreakDao
import com.dhanush.streaks.db.StreakDatabase
import com.dhanush.streaks.model.Streak
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

        val adapter = StreakItemAdapter(listOf())
        binding.streakRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.streakRecyclerView.adapter = adapter

        streakDao.getAll().observe(this) { dataset ->
            adapter.updateDataset(dataset)
        }

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