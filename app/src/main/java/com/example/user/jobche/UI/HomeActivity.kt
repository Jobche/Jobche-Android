package com.example.user.jobche.UI

import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.example.user.jobche.*
import com.example.user.jobche.HomeViewModel
import com.example.user.jobche.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar = findViewById<Toolbar>(R.id.custom_toolbar)
        val drawer = findViewById<DrawerLayout>(R.id.nav_drawer)
        toolbar.title = "Love toy"
        setSupportActionBar(toolbar)

        val binding: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        val homeViewModel = HomeViewModel()
        binding.viewModel = homeViewModel

        val toggle  = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        toggle.isDrawerIndicatorEnabled = true
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val recyclerView = findViewById<RecyclerView>(R.id.listOfTasks)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager

        homeViewModel.generateTasks()

        homeViewModel.adapterEventData.observe(this, Observer {
        recyclerView.adapter = RecyclerViewAdapter(this,
                                                    homeViewModel.getTitles(),
                                                    homeViewModel.getLocations(),
                                                    homeViewModel.getDate(),
                                                    homeViewModel.getTime(),
                                                    homeViewModel.getPayments(),
                                                    homeViewModel.getNumberOfWorkers(),
                                                    homeViewModel.getDescriptions())
        })



        homeViewModel.fabEventLiveData.observe(this, Observer {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        })

    }

//    override fun onBackPressed() {
//        if(drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START)
//        }else{
//            super.onBackPressed()
//        }
//
//
//    }
}
