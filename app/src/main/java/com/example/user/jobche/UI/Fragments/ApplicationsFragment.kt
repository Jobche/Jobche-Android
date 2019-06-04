package com.example.user.jobche.UI.Fragments

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.jobche.Adapters.ApplicationsPagerAdapter
import com.example.user.jobche.R
import com.example.user.jobche.Task
import com.example.user.jobche.UI.HomeActivity
import com.example.user.jobche.databinding.FragmentViewpagerBinding

class ApplicationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar!!.title = "Кандидатствaния"
            (activity as HomeActivity).showBackButton(true)
        }
        val binding: FragmentViewpagerBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_viewpager, container, false)

        binding.viewPager.adapter = ApplicationsPagerAdapter(childFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        return binding.root
    }

}
