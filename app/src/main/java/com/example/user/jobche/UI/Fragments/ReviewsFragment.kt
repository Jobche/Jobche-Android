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
import com.example.user.jobche.databinding.FragmentTaskAppliedBinding


class ReviewsFragment : Fragment(), AppliersRecyclerViewAdapter.OnApplierClickListener {


    private lateinit var recyclerView: RecyclerView
    private lateinit var email: String
    private lateinit var password: String
    private var workId:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar!!.title = "Оценяване"
            (activity as HomeActivity).showBackButton(true)
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
        val reviewsViewModel = ViewModelProviders.of(this).get(ReviewsViewModel::class.java)
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

        return binding.root
    }

    override fun onClick(position: Int) {
        Toast.makeText(activity, "Shte go reitvam toz" + position.toString(), Toast.LENGTH_SHORT).show()
    }
}
