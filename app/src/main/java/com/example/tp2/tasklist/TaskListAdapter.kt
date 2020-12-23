package com.example.tp2.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tp2.R
import com.example.tp2.Task

class TaskListAdapter() : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TaskDiffCallBack) {
     public var taskList: List<Task> = emptyList()

    // Déclaration de la variable lambda dans l'adapter:
    var onDeleteClickListener: ((Task) -> Unit)? = null
    var onEditClickListener: ((Task) -> Unit)? = null

    inner class TaskViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)  {
        fun bind(task: Task) {
            itemView.apply {
                // TODO: afficher les données et attacher les listeners aux différentes vues de notre [itemView]
                val title = itemView.findViewById<TextView>(R.id.task_title)
                val description = itemView.findViewById<TextView>(R.id.task_description)

                title.text = task.title
                description.text = task.description

                itemView.findViewById<ImageButton>(R.id.button_delete).setOnClickListener {
                    onDeleteClickListener?.invoke(task)
                }

                itemView.findViewById<ImageButton>(R.id.button_edit).setOnClickListener {
                    onEditClickListener?.invoke(task)
                }
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
        holder.bind(taskList[position])
    }

}
//Cette objet est obligatoire pour l'implementation de ListAdapter
object TaskDiffCallBack: DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id === newItem.id
    }
    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return (oldItem.description == newItem.description && oldItem.title == newItem.title && areItemsTheSame(oldItem,newItem))
    }
}
