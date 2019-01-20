package com.example.user.jobche

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import android.widget.Toast
import com.example.user.jobche.Model.Location
import com.example.user.jobche.Model.Task
import com.example.user.jobche.UI.OpenedTaskActivity


class RecyclerViewAdapter(
    private val context: Context,
    private val titles: ArrayList<String>,
    private val locations: ArrayList<Location>,
    private val dates: ArrayList<String>,
    private val time: ArrayList<String>,
    private val payments: ArrayList<Int>,
    private val numbersOfWorkers: ArrayList<Int>,
    private val descriptions: ArrayList<String>
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {



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
        holder.location.text = locations[position].city
        holder.date.text = dates[position]
        holder.time.text = time[position]
        holder.payment.text = payments[position].toString()
        holder.numberOfWorkers.text = numbersOfWorkers[position].toString()

        holder.itemView.setOnClickListener {
            Toast.makeText(context,"clicked" + position.toString(),Toast.LENGTH_SHORT).show()
            val intent = Intent(context, OpenedTaskActivity::class.java)
            intent.putExtra("Task", Task(titles[position], locations[position], payments[position], numbersOfWorkers[position], descriptions[position], dates[position] + time[position]))
            context.startActivity(intent)
        }

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