package com.example.tp2.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tp2.R
import com.example.tp2.Task
import com.example.tp2.tasklist.TaskListViewModel
import java.util.*

class TaskFragment  : Fragment(){

    private val viewModelTaskList: TaskListViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.task_edit, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        view.findViewById<Button>(R.id.button_validate).setOnClickListener {
            val res = findNavController().previousBackStackEntry?.savedStateHandle?.get<Int>("code")
            val task = findNavController().previousBackStackEntry?.savedStateHandle?.get<Task>("task")

            val UUID = task?.id ?: UUID.randomUUID().toString()
            val newTitle = view.findViewById<EditText>(R.id.edit_title).text.toString()
            val newDescription = view.findViewById<EditText>(R.id.edit_desc).text.toString()
            val newTask = Task(id = UUID, title = newTitle, description = newDescription)

            if(res == EDIT_TASK_REQUEST_CODE){
                viewModelTaskList.editTask(newTask)
            }else if(res == ADD_TASK_REQUEST_CODE){
                viewModelTaskList.addTask(newTask)
            }

            findNavController().navigate(R.id.action_fragmentTaskEdit_to_fragmentTaskList)
        }
    }

    companion object {
        const val ADD_TASK_REQUEST_CODE = 777
        const val TASK_KEY = 646
        const val EDIT_TASK_REQUEST_CODE = 767
    }
}