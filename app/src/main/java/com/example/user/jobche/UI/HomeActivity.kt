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
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.user.jobche.UI.Fragments.MyApplicationsFragment
import com.example.user.jobche.UI.Fragments.MyTasksFragment
import com.example.user.jobche.UI.Fragments.ProfileFragment
import com.example.user.jobche.UI.RecylclerViewAdapters.TasksRecyclerViewAdapter
import com.example.user.jobche.databinding.ActivityHomeBinding
import android.support.v4.app.FragmentTransaction
import com.example.user.jobche.UI.Fragments.HomeFragment


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var email: String
    private lateinit var password: String
    private var isLoaded: Boolean = false
    private var page = 0

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

        supportFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, HomeFragment()).commit()

    }


    @SuppressLint("CommitTransaction")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                HomeFragment()
            ).commit()


            R.id.nav_profile -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                ProfileFragment()
            ).commit()

            R.id.nav_my_tasks -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                MyTasksFragment()
            ).commit()


            R.id.nav_applied_for -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                MyApplicationsFragment()
            ).commit()

            R.id.nav_log_out -> startActivity(Intent(this, LoginActivity::class.java))
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

}
