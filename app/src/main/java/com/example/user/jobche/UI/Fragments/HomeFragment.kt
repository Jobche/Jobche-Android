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
import android.view.*
import com.example.user.jobche.HomeViewModel
import com.example.user.jobche.R
import com.example.user.jobche.UI.HomeActivity
import com.example.user.jobche.Adapters.TasksRecyclerViewAdapter
import com.example.user.jobche.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), TasksRecyclerViewAdapter.OnTaskClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var email: String
    private lateinit var password: String
    private var userId: Int = 0
    private lateinit var homeViewModel: HomeViewModel
    private val bundle: Bundle = Bundle()
    private lateinit var newFragment: Fragment

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        //protects from duplicating buttons
        menu!!.clear()
        inflater!!.inflate(R.menu.menu_toolbar_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //because of the custom menu for toolbar
        setHasOptionsMenu(true)

        val sharedPreferences: SharedPreferences =
            activity!!.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)

        email = sharedPreferences.getString("EMAIL", "")!!
        password = sharedPreferences.getString("PASSWORD", "")!!
        userId = sharedPreferences.getInt("ID", 0)

        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar!!.title = "Обяви"
            (activity as HomeActivity).showBackButton(false)
        }

        //clear all backStacks
        fragmentManager!!.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding.viewModel = homeViewModel


        homeViewModel.email = email
        homeViewModel.password = password

        recyclerView = binding.listOfTasks
        val layoutManager = LinearLayoutManager(activity!!)
        recyclerView.layoutManager = layoutManager

        homeViewModel.generateTasks(homeViewModel.getCallAllTasks())

        homeViewModel.adapterEventLiveData.observe(this, Observer {
            recyclerView.adapter = TasksRecyclerViewAdapter(
                homeViewModel.tasks,
                this
            )
        })


        homeViewModel.updateAdapterEventLiveData.observe(this, Observer {
            recyclerView.adapter!!.notifyDataSetChanged()

        })

        homeViewModel.fabEventLiveData.observe(this, Observer {
            activity!!.supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                AddTaskFragment()
            ).addToBackStack(null).commit()
        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    homeViewModel.page += 1
                    homeViewModel.generateTasks(homeViewModel.getCallAllTasks())
                }
            }
        })

        return binding.root
    }


    override fun onClick(position: Int) {
        newFragment = OpenedTaskFragment()
        bundle.putParcelable("Task", homeViewModel.tasks[position])
        Log.d("Task", homeViewModel.tasks[position].toString())
        newFragment.arguments = bundle
        activity!!.supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container, newFragment
        ).addToBackStack(null).commit()

    }
}

