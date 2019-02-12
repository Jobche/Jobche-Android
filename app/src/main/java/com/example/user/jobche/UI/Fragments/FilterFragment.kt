package com.example.user.jobche.UI.Fragments

import android.arch.lifecycle.Observer
import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.jobche.FilterViewModel
import com.example.user.jobche.Model.DateOfBirth
import com.example.user.jobche.R
import com.example.user.jobche.databinding.FragmentFilterBinding
import org.joda.time.DateTime
import java.util.*


class FilterFragment : Fragment() {

    private val c = Calendar.getInstance()
    private val year = c.get(Calendar.YEAR)
    private val month = c.get(Calendar.MONTH)
    private val day = c.get(Calendar.DAY_OF_MONTH)
    private lateinit var email: String
    private lateinit var password: String
    private val bundle:Bundle = Bundle()
    private lateinit var newFragment:Fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sharedPreferences: SharedPreferences =
            activity!!.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)

        email = sharedPreferences.getString("EMAIL", "")!!
        password = sharedPreferences.getString("PASSWORD", "")!!

        val binding: FragmentFilterBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_filter, container, false)
        val filterViewModel = FilterViewModel()
        binding.viewModel = filterViewModel

        filterViewModel.setEmail(email)
        filterViewModel.setPassword(password)

        filterViewModel.beginDateEventLiveData.observe(activity!!, Observer {
            val dpd = DatePickerDialog(
                activity!!,
                DatePickerDialog.OnDateSetListener { _, yearCalendar, monthOfYear, dayOfMonth ->
                    val dateTime = DateTime(yearCalendar, monthOfYear + 1, dayOfMonth, 0, 0)
                    filterViewModel.setDateStart(dateTime.toString())
                    filterViewModel.setFormattedDateStart(dateTime)
                },
                year,
                month,
                day
            )
            dpd.show()
        })

        filterViewModel.endDateEventLiveData.observe(activity!!, Observer {
            val dpd = DatePickerDialog(
                activity!!,
                DatePickerDialog.OnDateSetListener { _, yearCalendar, monthOfYear, dayOfMonth ->
                    val dateTime = DateTime(yearCalendar, monthOfYear + 1, dayOfMonth, 0, 0)
                    filterViewModel.setDateEnd(dateTime.toString())
                    filterViewModel.setFormattedDateEnd(dateTime)
                },
                year,
                month,
                day
            )
            dpd.show()
        })

        filterViewModel.searchTasksEventLiveData.observe(activity!!, Observer {
            bundle.putParcelable("Filter", filterViewModel.getFilter())
            newFragment = FilteredTasksFragment()
            newFragment.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container, newFragment
            ).commit()
        })

        return binding.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(FilterViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}
