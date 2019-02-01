package com.example.user.jobche.UI

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
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
import com.example.user.jobche.Model.Tasks
import com.example.user.jobche.UI.Fragments.Profile
import com.example.user.jobche.databinding.ActivityHomeBinding
import okhttp3.Credentials
import retrofit2.Call


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var email: String
    private lateinit var password: String
    private var isLoaded: Boolean = false
    private var page = 0

    private lateinit var call: Call<Tasks>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sharedPreferences: SharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)
        isLoaded = sharedPreferences.getBoolean("IS_LOGGED", false)
        if (isLoaded) {
            email = sharedPreferences.getString("EMAIL", "")!!
            password = sharedPreferences.getString("PASSWORD", "")!!
        }
        else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

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
        navigation.menu.getItem(0).isChecked = true

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


        homeViewModel.generateTasks(homeViewModel.getCallAllTasks())

        val recyclerViewAdapter = RecyclerViewAdapter(
            this,
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

        homeViewModel.fabEventLiveData.observe(this, Observer {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    page += 1
                    homeViewModel.setPage(page)
                    homeViewModel.generateTasks(homeViewModel.getCallAllTasks())
                }
            }
        })

    }

    @SuppressLint("CommitTransaction")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> startActivity(Intent(this, HomeActivity::class.java))


            R.id.nav_profile -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                Profile()
            ).commit()


            R.id.nav_log_out -> startActivity(Intent(this, LoginActivity::class.java))
        }
        drawer.closeDrawer(GravityCompat.START)
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
