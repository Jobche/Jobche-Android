package com.example.user.jobche.UI.Fragments

import android.arch.lifecycle.Observer
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.jobche.UI.RecylclerViewAdapters.AppliersRecyclerViewAdapter
import com.example.user.jobche.TaskAppliersViewModel
import com.example.user.jobche.R
import com.example.user.jobche.UI.HomeActivity
import com.example.user.jobche.databinding.FragmentTaskAppliersBinding


class TaskAppliersFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var email: String
    private lateinit var password: String
    private var page = 0
    private var taskId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)


        val sharedPreferences: SharedPreferences =
            activity!!.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)

        email = sharedPreferences.getString("EMAIL", "")!!
        password = sharedPreferences.getString("PASSWORD", "")!!


        val bundle = arguments
        if (bundle != null) {
            taskId = bundle.getInt("TaskId")
        }

        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar!!.title = "Желаещи"
            (activity as HomeActivity).showBackButton(true)
        }

        val binding: FragmentTaskAppliersBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_appliers, container, false)
        val taskAppliersViewModel = TaskAppliersViewModel()
        binding.viewModel = taskAppliersViewModel

        taskAppliersViewModel.setEmail(email)
        taskAppliersViewModel.setPassword(password)
        taskAppliersViewModel.setTaskId(taskId)

        recyclerView = binding.listOfAppliers
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager


        taskAppliersViewModel.getTaskAppliers()

        taskAppliersViewModel.adapterEventData.observe(this, Observer {
            recyclerView.adapter = AppliersRecyclerViewAdapter(
                this,
                taskAppliersViewModel.getApplications()
            )
        })


        taskAppliersViewModel.updateAdapterEventLiveData.observe(this, Observer {
            recyclerView.adapter!!.notifyDataSetChanged()

        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    page += 1
                    taskAppliersViewModel.setPage(page)
                    taskAppliersViewModel.getAppliers()
                }
            }
        })

        return binding.root
    }

}
