package com.example.streaks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.streaks.adapter.StreakItemAdapter
import com.example.streaks.databinding.ActivityMainBinding
import com.example.streaks.databinding.AddStreakBinding
import com.example.streaks.model.Streak
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dataset:Array<Streak> = arrayOf()
        // to do
        val adapter = StreakItemAdapter(dataset)
        binding.streakRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.streakRecyclerView.adapter = adapter

        binding.addButton.setOnClickListener {
            val addStreakView = AddStreakBinding.inflate(layoutInflater).root
            val alertDialog = MaterialAlertDialogBuilder(this)
                .setTitle("New Streak")
                .setView(addStreakView)
                .setPositiveButton("Add") { dialog, which ->
                    val curTime = Date().time
                    // to do
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .create()
            alertDialog.show()
        }

    }
}