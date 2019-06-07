package com.example.user.jobche.UI.Fragments

import android.arch.lifecycle.Observer
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.user.jobche.Adapters.AppliersRecyclerViewAdapter
import com.example.user.jobche.TaskAcceptedViewModel
import com.example.user.jobche.R
import com.example.user.jobche.Task
import com.example.user.jobche.databinding.FragmentTaskAcceptedBinding


class TaskAcceptedFragment : Fragment(), AppliersRecyclerViewAdapter.OnApplierClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var email: String
    private lateinit var password: String
    private var started: Boolean = false
    private var workId: Long = 0
    private var startedTaskId: Long = 0
    private var page = 0
    private var task: Task = Task()
    private var bundle: Bundle = Bundle()
    private lateinit var newFragment: Fragment
    private lateinit var taskAcceptedViewModel: TaskAcceptedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        val sharedPreferences: SharedPreferences =
            activity!!.getSharedPreferences("SHARED_PREFS", AppCompatActivity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        email = sharedPreferences.getString("EMAIL", "")!!
        password = sharedPreferences.getString("PASSWORD", "")!!
        started = sharedPreferences.getBoolean("TASK_STARTED", false)
        workId = sharedPreferences.getLong("WORK_ID", 0)
        startedTaskId = sharedPreferences.getLong("TASK_STARTED_ID", 0)

        val bundle = arguments
        if (bundle != null) {
            task = bundle.getParcelable("Task")!!
        }

        val binding: FragmentTaskAcceptedBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_task_accepted, container, false)
        taskAcceptedViewModel = TaskAcceptedViewModel(task, email, password)
        binding.viewModel = taskAcceptedViewModel
        binding.task = task

        if (startedTaskId == task.id) {
            taskAcceptedViewModel.started = started
            taskAcceptedViewModel.workId = workId
        }
        recyclerView = binding.listOfAppliers
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager


        taskAcceptedViewModel.getTaskAppliers()

        taskAcceptedViewModel.adapterEventData.observe(this, Observer {
            recyclerView.adapter = AppliersRecyclerViewAdapter(
                taskAcceptedViewModel.acceptedAppliers,
                this
            )
        })
        taskAcceptedViewModel.onClickEventLiveData.observe(this, Observer {
            newFragment = OpenedTaskFragment()
            this.bundle.putParcelable("Task", task)
            newFragment.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container, newFragment
            ).addToBackStack(null).commit()

        })

        taskAcceptedViewModel.hasStartedEventLiveData.observe(this, Observer {
            editor.putBoolean("TASK_STARTED", taskAcceptedViewModel.started)
            editor.putLong("WORK_ID", taskAcceptedViewModel.workId)
            editor.apply()

        })

        taskAcceptedViewModel.onReviewsEventLiveData.observe(this, Observer {
            val newBundle = Bundle()
            newFragment = ReviewsFragment()
            newBundle.putLong("WorkId", taskAcceptedViewModel.workId)
            newFragment.arguments = newBundle
            activity!!.supportFragmentManager.beginTransaction().replace(
                com.example.user.jobche.R.id.fragment_container, newFragment
            ).commit()
        })

        taskAcceptedViewModel.onStartEventLiveData.observe(this, Observer {

            val stringArray = taskAcceptedViewModel.acceptedNames.toTypedArray()
            val booleanArray = BooleanArray(taskAcceptedViewModel.acceptedNames.size)
            for (i in taskAcceptedViewModel.acceptedNames.indices) {
                booleanArray[i] = false
            }
            val builder = AlertDialog.Builder(activity!!)

            if (taskAcceptedViewModel.acceptedAppliers.isEmpty()) {
                builder.setTitle("Няма одобрени кандидати за работата.")
                builder.setNeutralButton("Cancel") { _, _ -> }
            } else {

                builder.setTitle("Отбележете хората, които са дошли и ще работят.")

                builder.setMultiChoiceItems(stringArray, booleanArray) { _, which, isChecked ->
                    booleanArray[which] = isChecked
                }

                builder.setPositiveButton("Start") { _, _ ->
                    // Do something when click positive button
                    taskAcceptedViewModel.startWork(booleanArray)
                    editor.putLong("TASK_STARTED_ID", task.id)

                }


                builder.setNeutralButton("Cancel") { _, _ ->
                    // Do nothing when click the neutral button
                }

            }
                val dialog = builder.create()
                // Display the alert dialog on interface
                dialog.show()
        })

        taskAcceptedViewModel.updateAdapterEventLiveData.observe(this, Observer {
            recyclerView.adapter!!.notifyDataSetChanged()

        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    page += 1
                    taskAcceptedViewModel.page = page
                    taskAcceptedViewModel.getTaskAppliers()
                }
            }
        })

        return binding.root
    }

    override fun onClick(position: Int) {
        bundle = Bundle()
        newFragment = ApplierProfileFragment()
        bundle.putLong("ApplicationId", taskAcceptedViewModel.acceptedApplications[position].id)
        bundle.putLong("ApplierId", taskAcceptedViewModel.acceptedAppliers[position].id)
        bundle.putString("Name", taskAcceptedViewModel.acceptedAppliers[position].firstName)
        bundle.putParcelable("Task", taskAcceptedViewModel.task)
        newFragment.arguments = bundle
        activity!!.supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container, newFragment
        ).addToBackStack(null).commit()
    }
}
