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
import com.example.user.jobche.*
import com.example.user.jobche.UI.HomeActivity
import com.example.user.jobche.Adapters.TasksRecyclerViewAdapter
import com.example.user.jobche.databinding.FragmentFilteredTasksBinding


class FilteredTasksFragment : Fragment(), TasksRecyclerViewAdapter.OnTaskClickListener {

    private lateinit var recyclerView: RecyclerView
    private var page = 0
    private lateinit var filteredTasksViewModel: FilteredTasksViewModel
    private lateinit var filter: Filter
    private val newFragment = OpenedTaskFragment()
    private val bundle: Bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar!!.title = "Филтрирани Обяви"
            (activity as HomeActivity).showBackButton(true)
        }
        val sharedPreferences: SharedPreferences =
            activity!!.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)

        val bundle = arguments
        if (bundle != null) {
            filter = bundle.getParcelable("Filter")!!
        }

        val binding: FragmentFilteredTasksBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_filtered_tasks, container, false)

        filteredTasksViewModel = FilteredTasksViewModel()
        binding.viewModel = filteredTasksViewModel

        filteredTasksViewModel.setEmail(sharedPreferences.getString("EMAIL", "")!!)
        filteredTasksViewModel.setPassword(sharedPreferences.getString("PASSWORD", "")!!)

        recyclerView = binding.listOfFilteredTasks
        val layoutManager = LinearLayoutManager(activity!!)
        recyclerView.layoutManager = layoutManager

        filteredTasksViewModel.filterTasks(filter)

        filteredTasksViewModel.adapterEventLiveData.observe(this, Observer {
            recyclerView.adapter = TasksRecyclerViewAdapter(
                filteredTasksViewModel.tasks,
                this
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

    override fun onClick(position: Int) {
        bundle.putParcelable("Task", filteredTasksViewModel.tasks[position])
        newFragment.arguments = bundle
        activity!!.supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container, newFragment
        ).addToBackStack(null).commit()

    }
}
