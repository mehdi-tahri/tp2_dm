package com.example.tp2.network

import com.example.tp2.task.Task

class TasksRepository {
    private val tasksWebService = Api.INSTANCE.tasksWebService

    suspend fun loadTasks(): List<Task>? {
        val response = tasksWebService.getTasks()
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun updateTask(task: Task): Task? {
        val response = tasksWebService.updateTask(task)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun createTask(task: Task) : Task? {
        val response = tasksWebService.createTask(task)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun deleteTask(task: Task) : Boolean {
        val isNull = task.id != null
        return if(isNull){
            val response = tasksWebService.deleteTask(task.id)
            response.isSuccessful
        }else{
            isNull
        }
    }
}