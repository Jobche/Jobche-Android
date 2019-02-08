package com.example.user.jobche.UI.RecylclerViewAdapters

import android.content.ContentValues.TAG
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import com.example.user.jobche.Model.DateOfBirth
import com.example.user.jobche.Model.UserProfile
import com.example.user.jobche.R
import org.joda.time.LocalDate
import org.joda.time.Years
import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.user.jobche.UI.Fragments.ApplierProfileFragment

class AppliersRecyclerViewAdapter(
    private val fragment: Fragment,
    private val appliers: ArrayList<UserProfile>
) : RecyclerView.Adapter<AppliersRecyclerViewAdapter.ViewHolder>() {

    private val bundle:Bundle = Bundle()
    private val newFragment = ApplierProfileFragment()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_opened_task, parent, false)
        return ViewHolder(view)
    }

    fun dateTimeToYears(dateOfBirth: DateOfBirth): String {
        val birthDate = LocalDate(dateOfBirth.year, dateOfBirth.month, dateOfBirth.day)
        val now = LocalDate()
        val age = Years.yearsBetween(birthDate, now).toString()
        return age.substring(1, age.length - 1)

    }

    override fun getItemCount(): Int {
        return appliers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called.")
        holder.firstname.text = appliers[position].firstName
        holder.lastname.text = appliers[position].lastName
        holder.years.text = dateTimeToYears(appliers[position].dateOfBirth)

        holder.itemView.setOnClickListener {
            bundle.putInt("UserId", appliers[position].id)
            newFragment.arguments = bundle
            fragment.activity!!.supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container, newFragment
            ).commit()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var firstname: TextView = itemView.findViewById(R.id.user_firstname)
        var lastname: TextView = itemView.findViewById(R.id.user_lastname)
        var years: TextView = itemView.findViewById(R.id.user_years)
    }
}