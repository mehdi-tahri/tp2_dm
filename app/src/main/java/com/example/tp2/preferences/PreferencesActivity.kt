package com.example.tp2.preferences

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.example.tp2.R

class PreferencesActivity: AppCompatActivity() {
    private val userManager = UserPreferenceRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager
                .beginTransaction()
                .replace(android.R.id.content, PreferenceFragment())
                .commit()

        userManager.colorTextFlow.asLiveData().observe(this) {
            if(it != null && it != ""){
                when(it){
                    "Default" -> getTheme().applyStyle(R.style.DefaulTheme,true)
                    "Black" -> getTheme().applyStyle(R.style.BlackTheme,true)
                    "Purple" -> getTheme().applyStyle(R.style.PurpleTheme,true)
                    "Cyan" -> getTheme().applyStyle(R.style.CyanTheme,true)
                }
            }
        }
        userManager.userAppNameFlow.asLiveData().observe(this) {
            title = it
        }

        userManager.colorAppBarFlow.asLiveData().observe(this) {
            if(it != null && it != ""){
                //La ListPreference ne fonctionne pas quand on lui donne des couleurs avec # donc on le rajoute ici
                var color = "#$it"
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(color)))
            }
        }
    }

}