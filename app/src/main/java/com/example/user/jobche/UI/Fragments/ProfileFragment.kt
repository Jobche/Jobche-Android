package com.example.user.jobche.UI.Fragments

import android.arch.lifecycle.Observer
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.jobche.ProfileViewModel
import com.example.user.jobche.R
import com.example.user.jobche.UI.HomeActivity
import com.example.user.jobche.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var email: String
    private lateinit var password: String
    private var userId: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar!!.title = "Профил"
            (activity as HomeActivity).showBackButton(false)
        }

        val sharedPreferences: SharedPreferences =
            this.activity!!.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)

        userId = sharedPreferences.getInt("ID", 0)
        email = sharedPreferences.getString("EMAIL", "")!!
        password = sharedPreferences.getString("PASSWORD", "")!!

        val binding: FragmentProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        val view: View = binding.root
        val profileViewModel = ProfileViewModel()
        binding.viewModel = profileViewModel
        profileViewModel.userId = userId
        profileViewModel.email = email
        profileViewModel.password = password
        profileViewModel.getUser()

        profileViewModel.acceptUserEventLiveData.observe(this, Observer {
            val bundle = Bundle()
            val newFragment = TaskAcceptedFragment()
            bundle.putParcelable("Task", profileViewModel.task)
            newFragment.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container, newFragment
            ).commit()
        })

        return view
    }
}
