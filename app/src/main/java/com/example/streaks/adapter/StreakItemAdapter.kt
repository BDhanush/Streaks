package com.example.streaks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.streaks.R
import com.example.streaks.model.Streak
import java.util.*
import kotlin.time.Duration.Companion.days

class StreakItemAdapter(private val dataSet: Array<Streak>) :
    RecyclerView.Adapter<StreakItemAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.title)
        val countTextView: TextView = view.findViewById(R.id.count)
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
        val dif = startTime - curTime
        val difInDays = dif.days
        viewHolder.titleTextView.text = difInDays.toString()

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
