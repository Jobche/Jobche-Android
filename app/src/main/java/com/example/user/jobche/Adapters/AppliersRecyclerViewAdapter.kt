package com.example.user.jobche.Adapters

import android.content.ContentValues.TAG
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import com.example.user.jobche.Model.DateOfBirth
import com.example.user.jobche.R
import org.joda.time.LocalDate
import org.joda.time.Years

import com.example.user.jobche.Model.UserProfile
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.header.*

class AppliersRecyclerViewAdapter(
    private val appliers: ArrayList<UserProfile>,
    private val onApplierClickListener: OnApplierClickListener
) : RecyclerView.Adapter<AppliersRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_opened_task, parent, false)
        return ViewHolder(view, onApplierClickListener)
    }

    fun dateTimeToYears(dateOfBirth: DateOfBirth): String {
        val now = LocalDate()
        val localDate = LocalDate(dateOfBirth.year, dateOfBirth.month, dateOfBirth.day)
        val age = Years.yearsBetween(localDate, now).toString()
        return age.substring(1, age.length - 1)

    }

    override fun getItemCount(): Int {
        return appliers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called.")
        holder.firstname.text = appliers[position].firstName
        holder.lastname.text = appliers[position].lastName
        holder.years.text = dateTimeToYears(appliers[position].dateOfBirth!!)
        if (appliers[position].profilePicture != null) {
            Picasso.get().load(appliers[position].profilePicture).resize(400, 400).centerCrop().into(holder.image)
        }
    }

    class ViewHolder(itemView: View, val onАpplierClickListener: OnApplierClickListener) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        override fun onClick(v: View?) {
            onАpplierClickListener.onClick(adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }

        val firstname: TextView = itemView.findViewById(R.id.user_firstname)
        val lastname: TextView = itemView.findViewById(R.id.user_lastname)
        val years: TextView = itemView.findViewById(R.id.user_years)
        var image: ImageView = itemView.findViewById(R.id.image_person)
    }

    interface OnApplierClickListener {
        fun onClick(position: Int)
    }
}