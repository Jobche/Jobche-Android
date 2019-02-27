package com.example.user.jobche.Adapters

import android.content.ContentValues.TAG
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import com.example.user.jobche.Model.DateOfBirth
import com.example.user.jobche.R
import org.joda.time.LocalDate
import org.joda.time.Years
import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.user.jobche.Model.Application
import com.example.user.jobche.UI.Fragments.ApplierProfileFragment

class AppliersRecyclerViewAdapter(
    private val fragment: Fragment,
    private val applications: ArrayList<Application>
) : RecyclerView.Adapter<AppliersRecyclerViewAdapter.ViewHolder>() {

    private val bundle:Bundle = Bundle()
    private val newFragment = ApplierProfileFragment()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_opened_task, parent, false)
        return ViewHolder(view)
    }

    fun dateTimeToYears(dateOfBirth: DateOfBirth): String {
        val now = LocalDate()
        val localDate = LocalDate(dateOfBirth.year, dateOfBirth.month, dateOfBirth.day)
        val age = Years.yearsBetween(localDate, now).toString()
        return age.substring(1, age.length - 1)

    }

    override fun getItemCount(): Int {
        return applications.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called.")
        holder.firstname.text = applications[position].applicant.firstName
        holder.lastname.text = applications[position].applicant.lastName
        holder.years.text = dateTimeToYears(applications[position].applicant.dateOfBirth!!)

        holder.itemView.setOnClickListener {
            bundle.putInt("ApplicationId", applications[position].id)
            bundle.putInt("ApplierId", applications[position].applicant.id)
            bundle.putString("Name", applications[position].applicant.firstName)
            newFragment.arguments = bundle
            fragment.activity!!.supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container, newFragment
            ).addToBackStack(null).commit()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var firstname: TextView = itemView.findViewById(R.id.user_firstname)
        var lastname: TextView = itemView.findViewById(R.id.user_lastname)
        var years: TextView = itemView.findViewById(R.id.user_years)
    }
}