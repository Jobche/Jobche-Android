package com.example.user.jobche.UI

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.example.user.jobche.*
import android.databinding.DataBindingUtil
import android.os.Handler
import android.support.design.widget.NavigationView
import com.example.user.jobche.databinding.ActivityHomeBinding
import android.view.*
import android.widget.Toast
import com.example.user.jobche.UI.Fragments.*
import com.example.user.jobche.databinding.HeaderBinding


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private var isLoaded: Boolean = false
    private var userId: Int = 0
    private lateinit var email: String
    private lateinit var password: String
    private var mToolBarNavigationListenerIsRegistered = false
    private lateinit var toggle: ActionBarDrawerToggle
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sharedPreferences: SharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)
        isLoaded = sharedPreferences.getBoolean("IS_LOGGED", false)
        userId = sharedPreferences.getInt("ID", 0)
        email = sharedPreferences.getString("EMAIL", "")!!
        password = sharedPreferences.getString("PASSWORD", "")!!

        if (!isLoaded) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val binding: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        val headerBinding = HeaderBinding.bind(binding.navView.getHeaderView(0))

        val profileViewModel = ProfileViewModel()
        headerBinding.viewModel = profileViewModel
        profileViewModel.email = email
        profileViewModel.password = password
        profileViewModel.userId = userId.toLong()
        profileViewModel.getUser()

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
            ).addToBackStack(null).commit()

            R.id.nav_my_tasks -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                MyTasksFragment()
            ).addToBackStack(null).commit()


            R.id.nav_applied_for -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                MyApplicationsFragment()
            ).addToBackStack(null).commit()

            R.id.nav_log_out -> startActivity(Intent(this, LoginActivity::class.java))
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
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

    //when back button is pressed and drawer is open, close the drawer
    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        }else if (doubleBackToExitPressedOnce || supportFragmentManager.backStackEntryCount != 0) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Моля, натиснете НАЗАД, за да излезете", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}
