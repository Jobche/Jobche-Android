package com.example.user.jobche.Adapters

import android.content.ContentValues.TAG
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import com.example.user.jobche.Task
import com.example.user.jobche.R

@Suppress("UNUSED_EXPRESSION")
class TasksRecyclerViewAdapter(
    private val tasks: ArrayList<Task>,
    private val onTaskClickListener: OnTaskClickListener
) : RecyclerView.Adapter<TasksRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.task_view, parent, false)
        return ViewHolder(view, onTaskClickListener)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called.")
        holder.title.text = tasks[position].title
        holder.location.text = tasks[position].location.city
        holder.date.text =
            ((tasks[position].dateTime).substring(8, 10) + "." + (tasks[position].dateTime).substring(5, 7))
        holder.time.text = (tasks[position].dateTime).substring(11, 16)
        holder.payment.text = tasks[position].payment
        holder.numberOfWorkers.text = tasks[position].numberOfWorkers
        holder.acceptedWorkersCount.text = tasks[position].safeAcceptedWorkersCount.toString()
    }

    class ViewHolder(itemView: View, var onTaskClickListener: OnTaskClickListener) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        override fun onClick(v: View?) {
            onTaskClickListener.onClick(adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }

        var title: TextView = itemView.findViewById(R.id.task_title)
        var location: TextView = itemView.findViewById(R.id.task_location)
        var date: TextView = itemView.findViewById(R.id.task_date)
        var time: TextView = itemView.findViewById(R.id.task_start_time)
        var payment: TextView = itemView.findViewById(R.id.task_payment)
        var numberOfWorkers: TextView = itemView.findViewById(R.id.task_numberOfWorkers)
        var acceptedWorkersCount: TextView = itemView.findViewById(R.id.task_numberOfAccepted)
    }

    interface OnTaskClickListener {
        fun onClick(position: Int)
    }
}
