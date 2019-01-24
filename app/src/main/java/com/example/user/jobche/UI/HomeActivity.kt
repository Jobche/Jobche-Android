package com.example.user.jobche.UI

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.example.user.jobche.*
import com.example.user.jobche.HomeViewModel
import android.databinding.DataBindingUtil
import android.view.View
import android.widget.Toast
import com.example.user.jobche.databinding.ActivityHomeBinding



class HomeActivity : AppCompatActivity() {

    private lateinit var drawer: DrawerLayout
    private lateinit var recyclerView: RecyclerView
    private var page = 0
    private val size = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val binding: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        val homeViewModel = HomeViewModel()
        binding.viewModel = homeViewModel


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = binding.drawerLayout

        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        recyclerView = binding.listOfTasks
        val layoutManager = LinearLayoutManager(this)
//        layoutManager.reverseLayout = true
//        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager


        homeViewModel.generateTasks(page, size)

        val recyclerViewAdapter = RecyclerViewAdapter(this,
                                                        homeViewModel.getIds(),
                                                        homeViewModel.getTitles(),
                                                        homeViewModel.getLocations(),
                                                        homeViewModel.getDate(),
                                                        homeViewModel.getTime(),
                                                        homeViewModel.getPayments(),
                                                        homeViewModel.getNumberOfWorkers(),
                                                        homeViewModel.getDescriptions(),
                                                        homeViewModel.getCreatorIds())

        homeViewModel.adapterEventData.observe(this, Observer {
            recyclerView.adapter = recyclerViewAdapter
        })


        homeViewModel.updateAdapterEventLiveData.observe(this, Observer {
            recyclerViewAdapter.notifyDataSetChanged()

        })

        homeViewModel.fabEventLiveData.observe(this, Observer {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    page += 1
                    homeViewModel.generateTasks(page, size)
                }
            }
        })

    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
