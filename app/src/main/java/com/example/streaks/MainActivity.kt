package com.example.streaks

import AppDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.streaks.adapter.StreakItemAdapter
import com.example.streaks.databinding.ActivityMainBinding
import com.example.streaks.databinding.AddStreakBinding
import com.example.streaks.model.Streak
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val applicationScope = CoroutineScope(SupervisorJob())

        // Using by lazy so the database and the repository are only created when they're needed
        // rather than when the application starts
        val database by lazy { AppDatabase.getDatabase(this) }

        val streakDao = database.streakDao()
        val dataset:List<Streak> = listOf()
        // to do
        val adapter = StreakItemAdapter(dataset)
        binding.streakRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.streakRecyclerView.adapter = adapter

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
                    if(streakDao!=null)
                    {
                        streakDao.insert(Streak(title,curTime))
                    }else{
                        Toast.makeText(applicationContext,"Adding Streak unsuccessful",Toast.LENGTH_LONG).show()
                    }
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .create()
            alertDialog.show()
        }

    }
}