package com.example.user.jobche.UI.Fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.jobche.HomeViewModel
import com.example.user.jobche.MyTasksViewModel
import com.example.user.jobche.R
import com.example.user.jobche.RecyclerViewAdapter
import com.example.user.jobche.UI.AddTaskActivity
import com.example.user.jobche.UI.LoginActivity
import com.example.user.jobche.databinding.ActivityHomeBinding
import com.example.user.jobche.databinding.MyTasksFragmentBinding


class MyTasks : Fragment() {

    companion object {
        fun newInstance() = MyTasks()
    }


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

        val binding: MyTasksFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.my_tasks_fragment, container, false)
        val view: View = binding.root
        val homeViewModel = HomeViewModel()
        binding.viewModel = homeViewModel

        homeViewModel.setEmail(email)
        homeViewModel.setPassword(password)

        recyclerView = binding.listOfMyTasks
        val layoutManager = LinearLayoutManager(activity!!)
        recyclerView.layoutManager = layoutManager


        homeViewModel.generateTasks(homeViewModel.getCallMyTasks())

        val recyclerViewAdapter = RecyclerViewAdapter(
            activity!!,
            homeViewModel.getIds(),
            homeViewModel.getTitles(),
            homeViewModel.getLocations(),
            homeViewModel.getDate(),
            homeViewModel.getTime(),
            homeViewModel.getPayments(),
            homeViewModel.getNumberOfWorkers(),
            homeViewModel.getDescriptions(),
            homeViewModel.getCreatorIds()
        )

        homeViewModel.adapterEventData.observe(this, Observer {
            recyclerView.adapter = recyclerViewAdapter
        })


        homeViewModel.updateAdapterEventLiveData.observe(this, Observer {
            recyclerViewAdapter.notifyDataSetChanged()

        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    page += 1
                    homeViewModel.setPage(page)
                    homeViewModel.generateTasks(homeViewModel.getCallMyTasks())
                }
            }
        })
        return view
    }
}


//override fun onActivityCreated(savedInstanceState: Bundle?) {
//    super.onActivityCreated(savedInstanceState)
//    viewModel = ViewModelProviders.of(this).get(MyTasksViewModel::class.java)
//    // TODO: Use the ViewModel
//}

//}
