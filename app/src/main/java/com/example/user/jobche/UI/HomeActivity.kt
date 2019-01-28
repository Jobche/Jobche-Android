package com.example.user.jobche.UI

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import android.support.design.widget.NavigationView
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.user.jobche.databinding.ActivityHomeBinding



class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var email:String
    private lateinit var password:String
    private var isLoaded: Boolean = false
    private var page = 0
    private val size = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        loadData()

        val binding: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        val homeViewModel = HomeViewModel()
        binding.viewModel = homeViewModel

        homeViewModel.setEmail(email)
        homeViewModel.setPassword(password)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = binding.drawerLayout

        val navigation = binding.navView
        navigation.setNavigationItemSelectedListener(this)

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

    fun loadData() {
        val sharedPreferences : SharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)
        isLoaded = sharedPreferences.getBoolean("IS_LOGGED", false)
         if(isLoaded) {
            email = sharedPreferences.getString("EMAIL", "")!!
             password = sharedPreferences.getString("PASSWORD", "")!!
         }else {
             startActivity(Intent(this, LoginActivity::class.java))
             Toast.makeText(this, isLoaded.toString(), Toast.LENGTH_SHORT).show()
         }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_add_task -> startActivity(Intent(this, AddTaskActivity::class.java))
            R.id.nav_log_out -> startActivity(Intent(this, LoginActivity::class.java))
        }
        return true
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
