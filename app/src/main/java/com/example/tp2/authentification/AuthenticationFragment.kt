package com.example.tp2.authentification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.tp2.MainActivity
import com.example.tp2.R
import com.example.tp2.network.Api

class AuthenticationFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.authentication_fragment, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!Api.INSTANCE.getToken().isNullOrEmpty()) {
            findNavController().navigate(R.id.action_authenticationFragment_to_fragmentTaskList)
        }
        view.findViewById<Button>(R.id.button_login).setOnClickListener {
            findNavController().navigate(R.id.action_authenticationFragment_to_loginFragment)
        }

        view.findViewById<Button>(R.id.button_signup).setOnClickListener {
            findNavController().navigate(R.id.action_authenticationFragment_to_signupFragment)
        }
    }
}