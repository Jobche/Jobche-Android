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
import android.arch.lifecycle.ViewModelProviders




class HomeActivity : AppCompatActivity() {

    private lateinit var drawer:DrawerLayout
//    private lateinit var toggle:ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)


        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

       val homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)


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
    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }
}
