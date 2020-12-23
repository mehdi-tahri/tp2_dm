package com.example.tp2.task

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.tp2.R
import com.example.tp2.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class TaskActivity  : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_edit)

        findViewById<Button>(R.id.button_validate).setOnClickListener {
            val task = intent.getSerializableExtra(TASK_KEY.toString()) as? Task
            val UUID = task?.id ?: UUID.randomUUID().toString()
            val newTitle = this.findViewById<EditText>(R.id.edit_title).text.toString()
            val newDescription = this.findViewById<EditText>(R.id.edit_desc).text.toString()
            val newTask = Task(id = UUID, title = newTitle, description = newDescription)
            intent?.putExtra(TASK_KEY.toString(),newTask)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    companion object {
        const val ADD_TASK_REQUEST_CODE = 777
        const val TASK_KEY = 646
        const val EDIT_TASK_REQUEST_CODE = 767
    }
}