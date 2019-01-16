package com.example.user.jobche

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater


class RecyclerViewAdapter(private val titles: ArrayList<String>,
                          private val locations: ArrayList<String>,
                          private val dates: ArrayList<String>,
                          private val time: ArrayList<String>,
                          private val payments: ArrayList<String>,
                          private val numbersOfWorkers: ArrayList<String>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.task_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called.")
        holder.title.text = titles[position]
        holder.location.text = locations[position]
        holder.date.text = dates[position]
        holder.time.text = time[position]
        holder.payment.text = payments[position]
        holder.numberOfWorkers.text = numbersOfWorkers[position]

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.task_title)
        var location: TextView = itemView.findViewById(R.id.task_location)
        var date: TextView = itemView.findViewById(R.id.task_date)
        var time: TextView = itemView.findViewById(R.id.task_start_time)
        var payment: TextView = itemView.findViewById(R.id.task_payment)
        var numberOfWorkers: TextView = itemView.findViewById(R.id.task_numberOfWorkers)

    }
}