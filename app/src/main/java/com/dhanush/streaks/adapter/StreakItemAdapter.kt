package com.dhanush.streaks.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dhanush.streaks.MainActivity
import com.dhanush.streaks.R
import com.dhanush.streaks.StreakActivity
import com.dhanush.streaks.model.Streak
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit

class StreakItemAdapter(private var dataSet: List<Streak>) :
    RecyclerView.Adapter<StreakItemAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.title)
        val countTextView: TextView = view.findViewById(R.id.count)
        val streakCard:MaterialCardView = view.findViewById(R.id.streakCard)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.streak_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.titleTextView.text = dataSet[position].title
        val startTime = dataSet[position].startTime
        val curTime = Date().time
        val dif = curTime - startTime
        val difInDays = dif.milliseconds.toLong(DurationUnit.DAYS)
        viewHolder.countTextView.text = difInDays.toString()
        viewHolder.streakCard.setOnClickListener {
            val intent = Intent(viewHolder.itemView.context, StreakActivity::class.java)
            intent.putExtra("title", dataSet[position].title)
            intent.putExtra("startTime", startTime)
            intent.putExtra("id", dataSet[position].id)
            viewHolder.itemView.context.startActivity(intent)
        }


        viewHolder.streakCard.setOnLongClickListener {
            val alertDialog = MaterialAlertDialogBuilder(viewHolder.itemView.context)
                .setTitle("Delete streak: ${dataSet[position].title}")
                .setMessage("Streak will be lost")
                .setPositiveButton("Delete") { dialog, which ->
                    val streakDao = MainActivity.database.streakDao()
                    deleteStreak(position,viewHolder.itemView.context)
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .create()
            alertDialog.show()
            return@setOnLongClickListener true
        }

    }

    fun addStreak(streak: Streak,context: Context)
    {
        streak.title = streak.title.trim()
        if(streak.title.isEmpty())
        {
            val alertDialog = MaterialAlertDialogBuilder(context)
                .setTitle("Invalid Streak")
                .setMessage("Title cannot be empty")
                .create()
            alertDialog.show()
            return
        }
        streak.id = MainActivity.database.streakDao().insert(streak)
//        dataSet.add(streak)
//        notifyItemInserted(dataSet.size-1)
        Toast.makeText(context,"Adding Streak successful",Toast.LENGTH_LONG).show()
    }
    fun deleteStreak(position: Int,context: Context)
    {
        MainActivity.database.streakDao().delete(dataSet[position])
//        dataSet.removeAt(position)
//        notifyItemRemoved(position)
//        notifyItemRangeChanged(position,dataSet.size)
        Toast.makeText(context,"Streak Deleted", Toast.LENGTH_LONG).show()
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun updateDataset(dataSet:List<Streak>)
    {
        this.dataSet=dataSet
        notifyDataSetChanged()
    }

}
