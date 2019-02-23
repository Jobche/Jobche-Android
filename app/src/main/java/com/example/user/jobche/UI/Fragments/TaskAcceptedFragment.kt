package com.example.user.jobche.UI.Fragments

import android.arch.lifecycle.Observer
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.jobche.UI.RecylclerViewAdapters.AppliersRecyclerViewAdapter
import com.example.user.jobche.TaskAcceptedViewModel
import com.example.user.jobche.R
import com.example.user.jobche.Task
import com.example.user.jobche.UI.HomeActivity
import com.example.user.jobche.ViewPagerAdapter
import com.example.user.jobche.databinding.FragmentTaskAcceptedBinding


class TaskAcceptedFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var email: String
    private lateinit var password: String
    private var page = 0
    private var task: Task = Task()
    private val bundle: Bundle = Bundle()
    private lateinit var newFragment: Fragment

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
            task = bundle.getParcelable("Task")!!
        }

        val binding: FragmentTaskAcceptedBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_accepted, container, false)
        val taskAppliersViewModel = TaskAcceptedViewModel(task, email, password)
        binding.viewModel = taskAppliersViewModel
        binding.task = task


//
//        binding.viewPager.adapter = ViewPagerAdapter(activity!!.supportFragmentManager)
//        binding.tabLayout.setupWithViewPager(binding.viewPager)

        recyclerView = binding.listOfAppliers
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager


        taskAppliersViewModel.getTaskAppliers()

        taskAppliersViewModel.adapterEventData.observe(this, Observer {
            recyclerView.adapter = AppliersRecyclerViewAdapter(
                this,
                taskAppliersViewModel.applications
            )
        })
        taskAppliersViewModel.onClickEventLiveData.observe(this, Observer {
            newFragment = OpenedTaskFragment()
            this.bundle.putParcelable("Task", task)
            newFragment.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container, newFragment
            ).addToBackStack(null).commit()

        })



        taskAppliersViewModel.updateAdapterEventLiveData.observe(this, Observer {
            recyclerView.adapter!!.notifyDataSetChanged()

        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    page += 1
                    taskAppliersViewModel.page = page
                    taskAppliersViewModel.appliers
                }
            }
        })

        return binding.root
    }

}
