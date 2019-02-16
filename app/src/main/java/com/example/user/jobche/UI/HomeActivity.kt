package com.example.user.jobche.UI

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.example.user.jobche.*
import com.example.user.jobche.HomeViewModel
import android.databinding.DataBindingUtil
import android.support.design.widget.NavigationView
import com.example.user.jobche.databinding.ActivityHomeBinding
import android.util.Log
import android.view.*
import com.example.user.jobche.UI.Fragments.*


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var email: String
    private lateinit var password: String
    private var isLoaded: Boolean = false
    private var mToolBarNavigationListenerIsRegistered = false
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sharedPreferences: SharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)
        isLoaded = sharedPreferences.getBoolean("IS_LOGGED", false)
        if (isLoaded) {
            email = sharedPreferences.getString("EMAIL", "")!!
            password = sharedPreferences.getString("PASSWORD", "")!!
        } else {
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

        toggle = ActionBarDrawerToggle(
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

            R.id.nav_home -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                HomeFragment()
            ).commit()


            R.id.nav_profile -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                ProfileFragment()
            ).commit()

            R.id.nav_my_tasks -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                MyTasksFragment()
            ).commit()


            R.id.nav_applied_for -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                MyApplicationsFragment()
            ).commit()

            R.id.nav_log_out -> startActivity(Intent(this, LoginActivity::class.java))
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    fun changeToolbar(title: String) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = title
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FilterFragment()).addToBackStack(null).commit()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun showBackButton(show: Boolean) {
        //lock swipe for drawer
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        if (show) {
            // Remove hamburger
            toggle.isDrawerIndicatorEnabled = false
            // Show back button
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            if (!mToolBarNavigationListenerIsRegistered) {
                toggle.toolbarNavigationClickListener = View.OnClickListener {
                    // Doesn't have to be onBackPressed
                    onBackPressed()
                }

                mToolBarNavigationListenerIsRegistered = true
            }

        } else {
//        unlock swipe for drawer
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
//            Remove back button
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
//            Show hamburger
            toggle.isDrawerIndicatorEnabled = true
            toggle.toolbarNavigationClickListener = null
            mToolBarNavigationListenerIsRegistered = false
        }
    }
}
