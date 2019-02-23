package com.example.user.jobche

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.user.jobche.UI.Fragments.TaskAcceptedFragment
import com.example.user.jobche.UI.Fragments.TaskWorkersFragment

class ViewPagerAdapter(fm: FragmentManager?, val task: Task) :
    FragmentPagerAdapter(fm) {

    private val numOfPages = 2
    private val bundle: Bundle = Bundle()
    private lateinit var newFragment: Fragment


    override fun getItem(p0: Int): Fragment {
       return when (p0) {
            0 ->  goToTaskAccepted()
            else ->  TaskAppliedFragment()
        }
    }

    fun goToTaskAccepted(): Fragment {
        newFragment = TaskAcceptedFragment()
        bundle.putParcelable("Task", task)
        newFragment.arguments = bundle
        return newFragment
    }

    override fun getCount(): Int {
        return numOfPages
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 ->  "Приети"
            else -> "Желаещи"
        }
    }
}