package com.sdssoft.myusergithub.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sdssoft.myusergithub.ui.insert.SettingViewModel
import com.sdssoft.myusergithub.DataStore.SettingPreference

class SettingViewModelFactory(private val preference: SettingPreference) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(preference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}