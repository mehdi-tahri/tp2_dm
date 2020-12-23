package com.example.tp2.authentification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.example.tp2.MainActivity
import com.example.tp2.R
import com.example.tp2.SHARED_PREF_TOKEN_KEY
import com.example.tp2.network.Api
import kotlinx.coroutines.launch

class LoginFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.login_fragment, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        view.findViewById<Button>(R.id.button_validate_login).setOnClickListener {
            val email = view.findViewById<EditText>(R.id.email_login).text.toString()
            val passwd = view.findViewById<EditText>(R.id.password_login).text.toString()

            // TODO : rendre le btn non clickable si email et password ne sont pas remplie (pour enlever ce test)
            if(email.isBlank() || passwd.isBlank() ){
                Toast.makeText(context, "Veuillez entrer toute les informations demandez", Toast.LENGTH_LONG).show()
            }else{
                val loginForm = LoginForm(email,passwd)
                lifecycleScope.launch {
                    val loginResponse =  Api.INSTANCE.userService.login(loginForm)
                    if(loginResponse.isSuccessful){
                        val token = loginResponse.body()!!.token
                        PreferenceManager.getDefaultSharedPreferences(context).edit {
                            putString(SHARED_PREF_TOKEN_KEY, token)
                        }
                        startActivity(Intent(activity, MainActivity::class.java))
                    }else{
                        val message = loginResponse.errorBody()?.string() ?: ""
                        Log.e("SIGNUP ", loginResponse.errorBody().toString())
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                        //Toast.makeText(context, "erreur lors de l'authentification, veuillez vérifier vos informations", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}