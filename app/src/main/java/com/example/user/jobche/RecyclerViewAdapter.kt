package com.example.user.jobche

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater


class RecyclerViewAdapter(private val context: Context,
                          private val titles: ArrayList<String>,
                          private val dates: ArrayList<String>,
                          private val time: ArrayList<String>,
                          private val payments: ArrayList<String>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

//    private val titles: ArrayList<String> = ArrayList()
//    private val dates: ArrayList<String> = ArrayList()
//    private val time: ArrayList<String> = ArrayList()
//    private val payments: ArrayList<String> = ArrayList()


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
        holder.date.text = dates[position]
        holder.time.text = time[position]
        holder.payment.text = payments[position]

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.task_title)
        //            var location: TextView = itemView.findViewById(R.id.task_location)
        var date: TextView = itemView.findViewById(R.id.task_date)
        var time: TextView = itemView.findViewById(R.id.task_start_time)
        var payment: TextView = itemView.findViewById(R.id.task_payment)
//
//        init {
//            var title: TextView = itemView.findViewById(R.id.task_title)
////            var location: TextView = itemView.findViewById(R.id.task_location)
//            var date: TextView = itemView.findViewById(R.id.task_date)
//            var time: TextView = itemView.findViewById(R.id.task_start_time)
//            var payment: TextView = itemView.findViewById(R.id.task_payment)
////            var rating: TextView = itemView.findViewById(R.id.task_rating)
////            var accepted: TextView = itemView.findViewById(R.id.task_accepted)
//        }
    }
}