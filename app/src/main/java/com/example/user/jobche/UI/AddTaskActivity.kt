package com.example.user.jobche.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.user.jobche.R
import kotlinx.android.synthetic.main.toolbar.*

class AddTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        toolbar_title.text="Add Task"

        val mAddTask = findViewById<Button>(R.id.add_task_btn)
        mAddTask.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}
