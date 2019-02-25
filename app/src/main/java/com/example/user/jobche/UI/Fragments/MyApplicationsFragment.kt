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
import com.example.user.jobche.UI.HomeActivity
import com.example.user.jobche.Adapters.TasksRecyclerViewAdapter
import com.example.user.jobche.databinding.FragmentMyApplicationsBinding


class MyApplicationsFragment : Fragment(), TasksRecyclerViewAdapter.OnTaskClickListener {


    private lateinit var recyclerView: RecyclerView
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var myApplicationsViewModel:MyApplicationsViewModel
    private val bundle: Bundle = Bundle()
    private lateinit var newFragment: Fragment

    private var page = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar!!.title = "Кандидаствания"
            (activity as HomeActivity).showBackButton(false)
        }

        val sharedPreferences: SharedPreferences =
            activity!!.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)

        email = sharedPreferences.getString("EMAIL", "")!!
        password = sharedPreferences.getString("PASSWORD", "")!!

        val binding: FragmentMyApplicationsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_applications, container, false)
        val view: View = binding.root
        myApplicationsViewModel = MyApplicationsViewModel()
        binding.viewModel = myApplicationsViewModel

        myApplicationsViewModel.setEmail(email)
        myApplicationsViewModel.setPassword(password)

        recyclerView = binding.listOfMyTasks
        val layoutManager = LinearLayoutManager(activity!!)
        recyclerView.layoutManager = layoutManager


        myApplicationsViewModel.getAppliedTasks()

        myApplicationsViewModel.adapterEventData.observe(this, Observer {

            recyclerView.adapter = TasksRecyclerViewAdapter(
                myApplicationsViewModel.tasks,
                this
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
    override fun onClick(position: Int) {
        newFragment = OpenedTaskFragment()
        bundle.putParcelable("Task", myApplicationsViewModel.tasks[position])
        newFragment.arguments = bundle
        activity!!.supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container, newFragment
        ).addToBackStack(null).commit()
    }
}
