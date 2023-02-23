package com.sdssoft.myusergithub.ui.insert

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sdssoft.myusergithub.DataStore.SettingPreference
import kotlinx.coroutines.launch

class SettingViewModel(private val preference: SettingPreference):ViewModel() {
    fun getThemeSetting(): LiveData<Boolean> = preference.getThemeSetting().asLiveData()

    fun saveThemeSetting(isDarkModeActive: Boolean){
        viewModelScope.launch {
            preference.saveThemeSetting(isDarkModeActive)
        }
    }
}