package com.example.user.jobche.UI.Fragments

import android.arch.lifecycle.Observer
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.example.user.jobche.HomeViewModel
import com.example.user.jobche.R
import com.example.user.jobche.UI.HomeActivity
import com.example.user.jobche.UI.RecylclerViewAdapters.TasksRecyclerViewAdapter
import com.example.user.jobche.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var email: String
    private lateinit var password: String
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sharedPreferences: SharedPreferences =
            activity!!.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)

        email = sharedPreferences.getString("EMAIL", "")!!
        password = sharedPreferences.getString("PASSWORD", "")!!

        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar!!.title = "Обяви"
            (activity as HomeActivity).showBackButton(false)

        }

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

        homeViewModel.adapterEventLiveData.observe(this, Observer {
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
            ).addToBackStack(null).commit()
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
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_toolbar_home, menu)
        super.onCreateOptionsMenu(menu,inflater)

    }
}