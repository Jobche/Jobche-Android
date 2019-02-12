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
import com.example.user.jobche.FilteredTasksViewModel
import com.example.user.jobche.Model.Filter
import com.example.user.jobche.R
import com.example.user.jobche.UI.RecylclerViewAdapters.TasksRecyclerViewAdapter
import com.example.user.jobche.databinding.FragmentFilteredTasksBinding


class FilteredTasksFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var email: String
    private lateinit var password: String
    private var page = 0

    private lateinit var filter: Filter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sharedPreferences: SharedPreferences =
            activity!!.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)

        email = sharedPreferences.getString("EMAIL", "")!!
        password = sharedPreferences.getString("PASSWORD", "")!!

        val bundle = arguments
        if (bundle != null) {
            filter = bundle.getParcelable("Filter")!!
        }

        val binding: FragmentFilteredTasksBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_filtered_tasks, container, false)

        val filteredTasksViewModel = FilteredTasksViewModel()
        binding.viewModel = filteredTasksViewModel

        filteredTasksViewModel.setEmail(email)
        filteredTasksViewModel.setPassword(password)

        recyclerView = binding.listOfFilteredTasks
        val layoutManager = LinearLayoutManager(activity!!)
        recyclerView.layoutManager = layoutManager

        filteredTasksViewModel.filterTasks(filter)

        filteredTasksViewModel.adapterEventLiveData.observe(this, Observer {
            recyclerView.adapter = TasksRecyclerViewAdapter(
                this,
                filteredTasksViewModel.getTasks()
            )
        })

        filteredTasksViewModel.updateAdapterEventLiveData.observe(this, Observer {
            recyclerView.adapter!!.notifyDataSetChanged()

        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    page += 1
                    filteredTasksViewModel.setPage(page)
                    filteredTasksViewModel.filterTasks(filter)
                }
            }
        })

        return binding.root
    }

}
