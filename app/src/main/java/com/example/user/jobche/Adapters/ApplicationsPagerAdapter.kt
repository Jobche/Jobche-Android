package com.example.user.jobche.Adapters

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.user.jobche.Task
import com.example.user.jobche.UI.Fragments.MyAcceptedFragment
import com.example.user.jobche.UI.Fragments.MyApplicationsFragment
import com.example.user.jobche.UI.Fragments.TaskAppliedFragment

class ApplicationsPagerAdapter(fm: FragmentManager?): FragmentPagerAdapter(fm) {

    private val numOfPages = 2
    private val bundle: Bundle = Bundle()
    private lateinit var newFragment: Fragment


    override fun getItem(p0: Int): Fragment {
        return when (p0) {
            0 ->  goToFragment(MyAcceptedFragment())
            else -> goToFragment(MyApplicationsFragment())
        }
    }

    private fun goToFragment(fragment: Fragment): Fragment {
        newFragment = fragment
        newFragment.arguments = bundle
        return newFragment
    }

    override fun getCount(): Int {
        return numOfPages
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 ->  "Приет"
            else -> "Изчакващ"
        }
    }
}