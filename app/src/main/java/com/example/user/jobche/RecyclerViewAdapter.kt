package com.example.user.jobche

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
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
    private val ids: ArrayList<Int>,
    private val titles: ArrayList<String>,
    private val locations: ArrayList<Location>,
    private val dates: ArrayList<String>,
    private val time: ArrayList<String>,
    private val payments: ArrayList<Int>,
    private val numbersOfWorkers: ArrayList<Int>,
    private val descriptions: ArrayList<String>,
    private val creatorIds: ArrayList<Int>
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)
    private val userId = sharedPreferences.getInt("ID", 0)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.task_view, parent, false)
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
            if (creatorIds[position] == userId) {
                Toast.makeText(context, "Sa che ti pokaja", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(context, OpenedTaskActivity::class.java)
                intent.putExtra(
                    "Task", Task(
                        ids[position], titles[position],
                        locations[position], payments[position],
                        numbersOfWorkers[position], descriptions[position],
                        dates[position] + time[position],
                        creatorIds[position]
                    )
                )
                context.startActivity(intent)
            }
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