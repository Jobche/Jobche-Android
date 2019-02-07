package com.example.user.jobche.UI.Fragments

import android.arch.lifecycle.Observer
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.jobche.MyApplicationsViewModel
import com.example.user.jobche.R
import com.example.user.jobche.UI.RecylclerViewAdapters.TasksRecyclerViewAdapter
import com.example.user.jobche.databinding.FragmentMyApplicationsBinding


class MyApplicationsFragment : Fragment() {


    companion object {
        fun newInstance() = MyApplicationsFragment()
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

        val binding: FragmentMyApplicationsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_applications, container, false)
        val view: View = binding.root
        val myApplicationsViewModel = MyApplicationsViewModel()
        binding.viewModel = myApplicationsViewModel

        myApplicationsViewModel.setEmail(email)
        myApplicationsViewModel.setPassword(password)

        recyclerView = binding.listOfMyTasks
        val layoutManager = LinearLayoutManager(activity!!)
        recyclerView.layoutManager = layoutManager


        myApplicationsViewModel.getAppliedTasks()

        myApplicationsViewModel.adapterEventData.observe(this, Observer {
            Log.d("TASK ZA APPLY", myApplicationsViewModel.getTasks().toString())

            recyclerView.adapter = TasksRecyclerViewAdapter(
                this,
                myApplicationsViewModel.getTasks()
            )
        })


        myApplicationsViewModel.updateAdapterEventLiveData.observe(this, Observer {
            recyclerView.adapter!!.notifyDataSetChanged()

        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    page += 1
                    myApplicationsViewModel.setPage(page)
                    myApplicationsViewModel.getAppliedTasks()
                }
            }
        })

        return view
    }
}
