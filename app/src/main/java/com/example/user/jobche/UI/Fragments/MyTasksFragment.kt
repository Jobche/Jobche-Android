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
import com.example.user.jobche.databinding.FragmentMyTasksBinding


class MyTasksFragment : Fragment() {

    companion object {
        fun newInstance() = MyTasksFragment()
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

        val binding: FragmentMyTasksBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_tasks, container, false)
        val view: View = binding.root
        val homeViewModel = HomeViewModel()
        binding.viewModel = homeViewModel

        homeViewModel.setEmail(email)
        homeViewModel.setPassword(password)

        recyclerView = binding.listOfMyTasks
        val layoutManager = LinearLayoutManager(activity!!)
        recyclerView.layoutManager = layoutManager


        homeViewModel.generateTasks(homeViewModel.getCallMyTasks())

        homeViewModel.adapterEventData.observe(this, Observer {
            recyclerView.adapter = TasksRecyclerViewAdapter(
                this,
                homeViewModel.getTasks()
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
