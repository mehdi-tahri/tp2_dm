package com.example.tp2.authentification

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.example.tp2.MainActivity
import com.example.tp2.R
import com.example.tp2.SHARED_PREF_TOKEN_KEY
import com.example.tp2.network.Api
import kotlinx.coroutines.launch

class SignupFragment: Fragment()  {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.signup_fragment, container, false)
        return rootView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        view.findViewById<Button>(R.id.button_validate_sign_up).setOnClickListener {
            val firstname = view.findViewById<EditText>(R.id.firstname_signup).text.toString()
            val lastname = view.findViewById<EditText>(R.id.lastname_signup).text.toString()
            val email = view.findViewById<EditText>(R.id.email_signup).text.toString()
            val password = view.findViewById<EditText>(R.id.password_signup).text.toString()
            val password_confirm = view.findViewById<EditText>(R.id.password_confirm_signup).text.toString()

            // TODO : rendre le btn non clickable si les info ne sont pas remplie (pour enlever ce test)
            if(firstname.isBlank() || lastname.isBlank() || email.isBlank() || password.isBlank() || password_confirm.isBlank()){
                Toast.makeText(context, "Veuillez entrer toutes les informations demandées", Toast.LENGTH_LONG).show()
            }else if(password != password_confirm) {
                Toast.makeText(context, "Votre password et password confirm doivent être identique", Toast.LENGTH_LONG).show()
            } else{
                val loginForm =SignUpForm(firstname,lastname,email,password,password_confirm)
                lifecycleScope.launch {
                    val signupResponse =  Api.INSTANCE.userService.signUp(loginForm)
                    if(signupResponse.isSuccessful){
                        val token = signupResponse.body()!!.token
                        PreferenceManager.getDefaultSharedPreferences(context).edit {
                            putString(SHARED_PREF_TOKEN_KEY, token)
                        }
                        findNavController().navigate(R.id.action_signupFragment_to_fragmentTaskList)
                        //startActivity(Intent(activity, MainActivity::class.java))
                    }else{
                        Toast.makeText(context, "erreur lors de l'inscription, veuillez vérifier vos informations", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}