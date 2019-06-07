package com.example.user.jobche.Adapters

import android.content.ContentValues
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.user.jobche.Model.Review
import com.example.user.jobche.R

class ReviewsRecyclerViewAdapter(
    private val reviews: ArrayList<Review>): RecyclerView.Adapter<ReviewsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.reviews_history_element, parent, false)
        return ReviewsRecyclerViewAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(ContentValues.TAG, "onBindViewHolder: called.")
        holder.rating.text = (reviews[position].reviewGrade.ordinal + 1).toString()
        holder.comment.text = reviews[position].comment
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val comment: TextView = itemView.findViewById(R.id.review_element_comment)
        val rating: TextView = itemView.findViewById(R.id.review_element_rating)
    }
}