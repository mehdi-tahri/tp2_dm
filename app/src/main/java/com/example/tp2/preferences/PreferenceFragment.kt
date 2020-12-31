package com.example.tp2.preferences

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.tp2.R

class PreferenceFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_preference,rootKey)
    }
}