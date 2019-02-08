package com.example.user.jobche.UI.Fragments

import android.arch.lifecycle.Observer
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.jobche.HomeViewModel
import com.example.user.jobche.R
import com.example.user.jobche.UI.RecylclerViewAdapters.TasksRecyclerViewAdapter
import com.example.user.jobche.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var email: String
    private lateinit var password: String
    private var page = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sharedPreferences: SharedPreferences =
            activity!!.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)

        email = sharedPreferences.getString("EMAIL", "")!!
        password = sharedPreferences.getString("PASSWORD", "")!!


        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val homeViewModel = HomeViewModel()
        binding.viewModel = homeViewModel

        homeViewModel.setEmail(email)
        homeViewModel.setPassword(password)

        recyclerView = binding.listOfTasks
        val layoutManager = LinearLayoutManager(activity!!)
        recyclerView.layoutManager = layoutManager

        homeViewModel.generateTasks(homeViewModel.getCallAllTasks())

        homeViewModel.adapterEventData.observe(this, Observer {
            recyclerView.adapter = TasksRecyclerViewAdapter(
                this,
                homeViewModel.getTasks()
            )
        })


        homeViewModel.updateAdapterEventLiveData.observe(this, Observer {
            recyclerView.adapter!!.notifyDataSetChanged()

        })

        homeViewModel.fabEventLiveData.observe(this, Observer {
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                AddTaskFragment()
            ).commit()
        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    page += 1
                    homeViewModel.setPage(page)
                    homeViewModel.generateTasks(homeViewModel.getCallAllTasks())
                }
            }
        })
        return binding.root
    }
}