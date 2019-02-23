package com.example.user.jobche.UI.Fragments

import android.content.Context
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.user.jobche.R
import com.example.user.jobche.Task
import com.example.user.jobche.UI.HomeActivity
import com.example.user.jobche.ViewPagerAdapter
import com.example.user.jobche.databinding.FragmentTaskAcceptedBinding
import com.example.user.jobche.databinding.FragmentTaskWorkersBinding
import kotlinx.android.synthetic.main.fragment_task_workers.*

class TaskWorkersFragment : Fragment() {

    private var task: Task = Task()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar!!.title = "Желаещи"
            (activity as HomeActivity).showBackButton(true)
        }
        val binding: FragmentTaskWorkersBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_task_workers, container, false)

        val bundle = arguments
        if (bundle != null) {
            task = bundle.getParcelable("Task")!!
        }

        binding.viewPager.adapter = ViewPagerAdapter(childFragmentManager, task)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        return binding.root
    }

}
