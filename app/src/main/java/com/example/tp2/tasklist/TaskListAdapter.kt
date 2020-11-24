package com.example.tp2.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp2.R

class TaskListAdapter(private val taskList: List<String>) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {
    inner class TaskViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)  {
        fun bind(taskTitle: String) {
            itemView.apply { // `apply {}` permet d'éviter de répéter `itemView.*`
                // TODO: afficher les données et attacher les listeners aux différentes vues de notre [itemView]
                findViewById<TextView>(R.id.task_title).text = taskTitle
            }
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        val taskView = TaskViewHolder(itemView)
        return taskView
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        //bindViewHolder(holder,position)
        holder.bind(taskList[position])
    }

}
