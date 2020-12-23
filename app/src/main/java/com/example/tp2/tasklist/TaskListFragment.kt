package com.example.tp2.tasklist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp2.R
import com.example.tp2.Task
import com.example.tp2.network.Api
import com.example.tp2.task.TaskActivity
import com.example.tp2.task.TaskActivity.Companion.ADD_TASK_REQUEST_CODE
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import coil.load
import com.example.tp2.MainActivity
import com.example.tp2.SHARED_PREF_TOKEN_KEY
import com.example.tp2.authentification.AuthenticationActivity
import com.example.tp2.userinfo.UserInfoActivity
import com.example.tp2.userinfo.UserInfoViewModel
import com.mikhaellopez.circularimageview.CircularImageView

class TaskListFragment: Fragment()  {

    private val viewModel: TaskListViewModel by viewModels()
    private val viewModelUser: UserInfoViewModel by viewModels()
    private val adapter = TaskListAdapter()
    //private val tasksRepository = TasksRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            val intent = Intent(activity, TaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }

        //Pas besoin de start une activity on suprimme juste de la liste et on refresh en notifiant l'adapter (TaskListAdapter)
        adapter.onDeleteClickListener = { task ->
            viewModel.deleteTask(task)
            adapter.notifyDataSetChanged()
        }

        adapter.onEditClickListener = { task ->
            val intent = Intent(activity, TaskActivity::class.java)
            intent.putExtra(TaskActivity.TASK_KEY.toString(), task)
            startActivityForResult(intent, TaskActivity.EDIT_TASK_REQUEST_CODE)
        }

        viewModel.taskList.observe(viewLifecycleOwner, Observer {
            adapter.taskList = it.toMutableList()
            adapter.notifyDataSetChanged();
        })

        //Quand il y a du changement sur le contenue de l'utilisateur on met a jour ces info
        viewModelUser.user.observe(viewLifecycleOwner, Observer {
            view?.findViewById<TextView>(R.id.textView_fragment)?.text = it.firstName +" "+ it.lastName
            view?.findViewById<CircularImageView>(R.id.imageView)?.load(it.avatar)
            adapter.notifyDataSetChanged();
        })

        // btn de déconnexion
        view.findViewById<FloatingActionButton>(R.id.action_button_exitapp).setOnClickListener {
            PreferenceManager.getDefaultSharedPreferences(context).edit {
                putString(SHARED_PREF_TOKEN_KEY, "")
                startActivity(Intent(activity, AuthenticationActivity::class.java))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if( resultCode == Activity.RESULT_OK){
            val task = data!!.getSerializableExtra(TaskActivity.TASK_KEY.toString()) as Task
            if (requestCode == TaskActivity.ADD_TASK_REQUEST_CODE ) {
                viewModel.addTask(task)
            } else if (requestCode == TaskActivity.EDIT_TASK_REQUEST_CODE ) {
                viewModel.editTask(task)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            /*val userInfo = Api.userService.getInfo().body()!!
            view?.findViewById<TextView>(R.id.textView_fragment)?.text   = "${userInfo.firstName} ${userInfo.lastName}"
            view?.findViewById<ImageView>(R.id.imageView)?.load(userInfo.avatar)
             */
            viewModelUser.loadInfo()
            viewModel.loadTasks()
        }

        //Image de base
        //imageView?.load("https://image.freepik.com/vecteurs-libre/urss-marteau-faucille_125371-91.jpg")
        view?.findViewById<CircularImageView>(R.id.imageView)?.setOnClickListener {
            val intent = Intent(activity, UserInfoActivity::class.java)
            startActivity(intent)
        }

        view?.findViewById<TextView>(R.id.textView_fragment)?.setOnClickListener {
            val intent = Intent(activity, UserInfoActivity::class.java)
            startActivity(intent)
        }

    }

}