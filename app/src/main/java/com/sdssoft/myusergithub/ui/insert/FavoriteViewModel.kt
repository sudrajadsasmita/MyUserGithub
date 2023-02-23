package com.sdssoft.myusergithub.ui.insert

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sdssoft.myusergithub.database.User
import com.sdssoft.myusergithub.repository.UserRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)

    fun getAllUsers(): LiveData<List<User>> = mUserRepository.getAllUsers()
}