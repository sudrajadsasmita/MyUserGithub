package com.sdssoft.myusergithub.ui.insert

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sdssoft.myusergithub.database.User
import com.sdssoft.myusergithub.repository.UserRepository

class FavoriteAddDeleteViewModel(application: Application) : ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)

    fun getUserByLogin(user: String): LiveData<User> = mUserRepository.getUserByLogin(user)

    fun insert(user: User) {
        mUserRepository.insert(user)
    }

    fun delete(user: User) {
        mUserRepository.delete(user)
    }
}