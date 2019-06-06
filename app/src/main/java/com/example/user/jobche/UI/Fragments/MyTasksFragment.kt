package com.example.user.jobche.UI.Fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.jobche.HomeViewModel
import com.example.user.jobche.R
import com.example.user.jobche.UI.HomeActivity
import com.example.user.jobche.Adapters.TasksRecyclerViewAdapter
import com.example.user.jobche.LoginViewModel
import com.example.user.jobche.databinding.FragmentMyTasksBinding


class MyTasksFragment : Fragment(), TasksRecyclerViewAdapter.OnTaskClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var homeViewModel: HomeViewModel
    private val bundle: Bundle = Bundle()
    private lateinit var newFragment: Fragment
    private var page = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar!!.title = "Моите Обяви"
            (activity as HomeActivity).showBackButton(false)
        }

        val sharedPreferences: SharedPreferences =
            activity!!.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)

        email = sharedPreferences.getString("EMAIL", "")!!
        password = sharedPreferences.getString("PASSWORD", "")!!

        val binding: FragmentMyTasksBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_tasks, container, false)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        binding.viewModel = homeViewModel

        homeViewModel.email = email
        homeViewModel.password = password

        recyclerView = binding.listOfMyTasks
        val layoutManager = LinearLayoutManager(activity!!)
        recyclerView.layoutManager = layoutManager


        homeViewModel.generateTasks(homeViewModel.getCallMyTasks())

        homeViewModel.adapterEventLiveData.observe(this, Observer {
            recyclerView.adapter = TasksRecyclerViewAdapter(
                homeViewModel.tasks,
                this
            )
        })


        homeViewModel.updateAdapterEventLiveData.observe(this, Observer {
            recyclerView.adapter!!.notifyDataSetChanged()

        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    page += 1
                    homeViewModel.page += 1
                    homeViewModel.generateTasks(homeViewModel.getCallMyTasks())
                }
            }
        })
        return binding.root
    }
    override fun onClick(position: Int) {
        newFragment = TaskWorkersFragment()
        bundle.putParcelable("Task", homeViewModel.tasks[position])
        newFragment.arguments = bundle
        activity!!.supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container, newFragment
        ).addToBackStack(null).commit()
    }
}


