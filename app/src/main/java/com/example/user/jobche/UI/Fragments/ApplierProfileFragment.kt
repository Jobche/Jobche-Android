package com.example.user.jobche.UI.Fragments

import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.jobche.ProfileViewModel
import com.example.user.jobche.R
import com.example.user.jobche.Task
import com.example.user.jobche.UI.HomeActivity
import com.example.user.jobche.databinding.FragmentApplierProfileBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*

class ApplierProfileFragment : Fragment() {

    private lateinit var email: String
    private lateinit var password: String
    private var userId: Int = 0
    private var applicationId: Long = 0
    private var applierId: Long = 0
    private lateinit var name: String
    private lateinit var task: Task
    private lateinit var newFragment: Fragment
    private val bundleFragment: Bundle = Bundle()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedPreferences: SharedPreferences =
            this.activity!!.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)

        val bundle = arguments
        if (bundle != null) {
            userId = sharedPreferences.getInt("ID", 0)
            applicationId = bundle.getLong("ApplicationId")
            applierId = bundle.getLong("ApplierId")
            name = bundle.getString("Name")!!
            task = bundle.getParcelable("Task")!!
        }

        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar!!.title = "Профил на $name"
            (activity as HomeActivity).showBackButton(true)
        }

        email = sharedPreferences.getString("EMAIL", "")!!
        password = sharedPreferences.getString("PASSWORD", "")!!

        val binding: FragmentApplierProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_applier_profile, container, false)
        val profileViewModel = ProfileViewModel()
        profileViewModel.userId = applierId
        profileViewModel.applicationId = applicationId
        binding.viewModel = profileViewModel
        binding.profileInfo.viewModel = profileViewModel
        profileViewModel.email = email
        profileViewModel.password = password
        profileViewModel.getUser()

        if(userId != applierId.toInt()) {
            binding.profileInfo.callButton.visibility = View.VISIBLE
        }

        profileViewModel.getImageEventLiveData.observe(this, Observer {
            Picasso.get().load(profileViewModel.userProfile.profilePicture).resize(400, 400).centerCrop()
                .into(image_profile)
        })

        profileViewModel.onClickReviewsLiveData.observe(this, Observer {
            newFragment = ReviewsHistoryFragment()
            bundleFragment.putParcelableArrayList("Reviews", profileViewModel.userProfile.reviews)
            newFragment.arguments = bundleFragment
            activity!!.supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container, newFragment
            ).addToBackStack(null).commit()
        })

        profileViewModel.onCallEventLiveData.observe(this, Observer {
            val builder = AlertDialog.Builder(activity!!)
            builder.setTitle("Потвъртеде, че искате да позвъните на " + profileViewModel.userProfile.firstName)

            builder.setPositiveButton("Потвърди") { _, _ ->
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:" + profileViewModel.userProfile.phoneNum)
                startActivity(callIntent)
            }

            builder.setNeutralButton("Назад") { _, _ ->
                // Do nothing when click the neutral button
            }

            builder.show()

        })

        profileViewModel.acceptUserEventLiveData.observe(this, Observer {
            newFragment = TaskWorkersFragment()
            bundleFragment.putParcelable("Task", task)
            newFragment.arguments = bundleFragment
            activity!!.supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container, newFragment
            ).addToBackStack(null).commit()
        })

        return binding.root
    }
}
