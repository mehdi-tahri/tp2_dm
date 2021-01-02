package com.example.tp2.authentification

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.preference.PreferenceManager
import com.example.tp2.R
import com.example.tp2.preferences.PreferencesActivity
import com.example.tp2.preferences.UserPreferenceRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AuthenticationActivity: AppCompatActivity() {

    private val userManager = UserPreferenceRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        setContentView(R.layout.activity_authentification)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.preferences){
            startActivity(Intent(this,PreferencesActivity::class.java))
        }
        return true
    }

}