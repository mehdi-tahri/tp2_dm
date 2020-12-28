package com.example.tp2.tasklist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp2.R
import com.example.tp2.task.TaskFragment
import com.example.tp2.task.TaskFragment.Companion.ADD_TASK_REQUEST_CODE
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import coil.load
import com.example.tp2.SHARED_PREF_TOKEN_KEY
import com.example.tp2.userinfo.UserInfoActivity
import com.example.tp2.userinfo.UserInfoViewModel
import com.mikhaellopez.circularimageview.CircularImageView

class TaskListFragment: Fragment()  {

    private val viewModel: TaskListViewModel by activityViewModels()
    private val viewModelUser: UserInfoViewModel by viewModels()
    private val adapter = TaskListAdapter()

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
            findNavController().currentBackStackEntry ?.savedStateHandle?.set("code", ADD_TASK_REQUEST_CODE)
            findNavController().navigate(R.id.action_fragmentTaskList_to_fragmentTaskEdit)
        }

        //Pas besoin de start une activity on suprimme juste de la liste et on refresh en notifiant l'adapter (TaskListAdapter)
        adapter.onDeleteClickListener = { task ->
            viewModel.deleteTask(task)
            adapter.notifyDataSetChanged()
        }

        //On envoie le code edit avec la task
        adapter.onEditClickListener = { task ->
            findNavController().currentBackStackEntry?.savedStateHandle?.set("code", TaskFragment.EDIT_TASK_REQUEST_CODE)
            findNavController().currentBackStackEntry?.savedStateHandle?.set("task", task)
            findNavController().navigate(R.id.action_fragmentTaskList_to_fragmentTaskEdit)
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
                findNavController().navigate(R.id.action_fragmentTaskList_to_authenticationFragment)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModelUser.loadInfo()
            viewModel.loadTasks()
        }

        view?.findViewById<CircularImageView>(R.id.imageView)?.setOnClickListener {
            //findNavController().navigate(R.id.action_fragmentTaskList_to_fragmentUserInfo)
            startActivity(Intent(activity, UserInfoActivity::class.java))
        }

        view?.findViewById<TextView>(R.id.textView_fragment)?.setOnClickListener {
           // findNavController().navigate(R.id.action_fragmentTaskList_to_fragmentUserInfo)
            startActivity(Intent(activity, UserInfoActivity::class.java))
        }

    }

}