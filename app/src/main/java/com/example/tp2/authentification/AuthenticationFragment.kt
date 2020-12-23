package com.example.tp2.authentification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tp2.R

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

        view.findViewById<Button>(R.id.button_login).setOnClickListener {
            findNavController().navigate(R.id.action_authenticationFragment_to_loginFragment)
        }

        view.findViewById<Button>(R.id.button_signup).setOnClickListener {
            findNavController().navigate(R.id.action_authenticationFragment_to_signupFragment)
        }
    }
}