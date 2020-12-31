package com.example.tp2.tasklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp2.task.Task
import com.example.tp2.network.TasksRepository
import kotlinx.coroutines.launch

class TaskListViewModel: ViewModel()  {

    private val repository = TasksRepository()
    private val _taskList = MutableLiveData<List<Task>>()
    public val taskList: LiveData<List<Task>> = _taskList

    fun loadTasks() {
        viewModelScope.launch {
            _taskList.value = repository.loadTasks()
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            if (repository.deleteTask(task)) {
                val editableList = _taskList.value.orEmpty().toMutableList()
                editableList.remove(task)
                _taskList.value = editableList
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.createTask(task)?.let { task ->
                val editableList = _taskList.value.orEmpty().toMutableList()
                editableList.add(task)
                _taskList.value = editableList
            }
        }
    }

    fun editTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)?.let { task ->
                val editableList = _taskList.value.orEmpty().toMutableList()
                val position = editableList.indexOfFirst { task.id == it.id }
                editableList[position] = task
                _taskList.value = editableList
            }
        }
    }

}