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
import com.example.user.jobche.Model.Task
import com.example.user.jobche.UI.TaskAppliersActivity
import com.example.user.jobche.UI.OpenedTaskActivity




class TasksRecyclerViewAdapter(
    private val context: Context,
    private val tasks: ArrayList<Task>
) : RecyclerView.Adapter<TasksRecyclerViewAdapter.ViewHolder>() {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)
    private val userId = sharedPreferences.getInt("ID", 0)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.task_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called.")
        holder.title.text = tasks[position].title
        holder.location.text = tasks[position].location.city
        holder.date.text = ((tasks[position].dateTime).substring(8, 10) + "." + (tasks[position].dateTime).substring(5, 7))
        holder.time.text = (tasks[position].dateTime).substring(11, 16)
        holder.payment.text = tasks[position].payment.toString()
        holder.numberOfWorkers.text = tasks[position].numberOfWorkers.toString()

        holder.itemView.setOnClickListener {
            if (tasks[position].creatorId == userId) {
                val intent = Intent(context, TaskAppliersActivity::class.java)
                intent.putExtra("TaskId", tasks[position].id)
                context.startActivity(intent)

//                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
//                    TaskAppliersActivity()
//                ).commit()

            } else {
                val intent = Intent(context, OpenedTaskActivity::class.java)
                intent.putExtra("Task", tasks[position])
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