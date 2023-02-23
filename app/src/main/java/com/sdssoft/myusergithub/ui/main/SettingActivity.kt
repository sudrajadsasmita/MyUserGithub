package com.sdssoft.myusergithub.ui.main

import android.content.Context
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.sdssoft.myusergithub.DataStore.SettingPreference
import com.sdssoft.myusergithub.databinding.ActivitySettingBinding
import com.sdssoft.myusergithub.helper.SettingViewModelFactory
import com.sdssoft.myusergithub.ui.insert.SettingViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Setting")

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setTitleTextColor(resources.getColor(android.R.color.white))
        setSupportActionBar(binding.toolbar)

        val preferences = SettingPreference.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(preferences)
        ).get(SettingViewModel::class.java)

        settingViewModel.getThemeSetting().observe(this, { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.swtDarkMode.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.swtDarkMode.isChecked = false
            }
        })

        binding.swtDarkMode.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }

        supportActionBar?.title = "Setting"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}