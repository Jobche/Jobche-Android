package com.example.user.jobche.UI.Fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.user.jobche.Adapters.AppliersRecyclerViewAdapter
import com.example.user.jobche.Model.UserProfile
import com.example.user.jobche.R
import com.example.user.jobche.ReviewsViewModel
import com.example.user.jobche.UI.HomeActivity
import com.example.user.jobche.databinding.FragmentReviewsBinding
import android.support.v7.app.AlertDialog
import com.example.user.jobche.databinding.RatingDialogBinding
import android.content.DialogInterface
import android.support.v4.app.FragmentManager
import android.support.v4.widget.DrawerLayout
import android.widget.RatingBar
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.rating_dialog.view.*


class ReviewsFragment : Fragment(), AppliersRecyclerViewAdapter.OnApplierClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var email: String
    private lateinit var password: String
    private var workId: Long = 0
    private lateinit var reviewsViewModel: ReviewsViewModel
    private lateinit var newFragment:Fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar!!.title = "Оценяване"
            (activity as HomeActivity).showBackButton(false)
        }

        val sharedPreferences: SharedPreferences =
            activity!!.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)

        email = sharedPreferences.getString("EMAIL", "")!!
        password = sharedPreferences.getString("PASSWORD", "")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bundle = arguments
        if (bundle != null) {
            workId = bundle.getLong("WorkId")
        }

        val binding: FragmentReviewsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_reviews, container, false)
        reviewsViewModel = ViewModelProviders.of(this).get(ReviewsViewModel::class.java)
        reviewsViewModel.workId = workId
        reviewsViewModel.email = email
        reviewsViewModel.password = password

        recyclerView = binding.listOfWorkers
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        reviewsViewModel.getWork()

        reviewsViewModel.adapterEventData.observe(this, Observer {
            recyclerView.adapter = AppliersRecyclerViewAdapter(
                reviewsViewModel.workers,
                this
            )
        })

        reviewsViewModel.onClickEventLiveData.observe(this, Observer {
            newFragment = HomeFragment()
            activity!!.supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container, newFragment
            ).addToBackStack(null).commit()
        })

        return binding.root
    }

    override fun onClick(position: Int) {
        val builder = AlertDialog.Builder(activity!!)
        val inflater = layoutInflater
        builder.setTitle("Оценете " + reviewsViewModel.workers[position].firstName + " " + reviewsViewModel.workers[position].lastName)
        val dialogLayout = inflater.inflate(R.layout.rating_dialog, null)
        val ratingBar = dialogLayout.ratingBar
        builder.setView(dialogLayout)

        builder.setPositiveButton("Избери") { _, _ ->
            reviewsViewModel.reviewUser(ratingBar.rating.toInt(), reviewsViewModel.workers[position].id, workId)
        }

        builder.setNeutralButton("Cancel") { _, _ ->
            // Do nothing when click the neutral button
        }

        builder.show()
    }
}
