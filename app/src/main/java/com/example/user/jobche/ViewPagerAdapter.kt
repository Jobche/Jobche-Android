package com.example.user.jobche

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.user.jobche.UI.Fragments.TaskAcceptedFragment
import com.example.user.jobche.UI.Fragments.TaskAppliedFragment

class ViewPagerAdapter(fm: FragmentManager?, val task: Task) :
    FragmentPagerAdapter(fm) {

    private val numOfPages = 2
    private val bundle: Bundle = Bundle()
    private lateinit var newFragment: Fragment


    override fun getItem(p0: Int): Fragment {
       return when (p0) {
            0 ->  goToFragment(TaskAcceptedFragment())
            else -> goToFragment(TaskAppliedFragment())
        }
    }

    fun goToFragment(fragment: Fragment): Fragment {
        newFragment = fragment
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