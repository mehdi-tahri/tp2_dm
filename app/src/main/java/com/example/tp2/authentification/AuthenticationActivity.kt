package com.example.tp2.authentification

import android.content.Intent
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

    lateinit var userManager : UserPreferenceRepository
    var app_name ="Default Value test"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentification)

        userManager = UserPreferenceRepository(this)

        observeData()

        /*val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val appName = pref.getString("edit_text_preference_1","")
        this.setTitle(appName)

         */

    }

    private fun observeData(){
        userManager.userAppNameFlow.asLiveData().observe(this,{
            app_name = it
            Log.e(" TEST", it)
            this.setTitle(app_name)
        })

        GlobalScope.launch{
            userManager.updateShowCompleted(app_name)
        }
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