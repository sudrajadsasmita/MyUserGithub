package com.sdssoft.myusergithub.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.sdssoft.myusergithub.database.User
import com.sdssoft.myusergithub.database.UserDao
import com.sdssoft.myusergithub.database.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun getAllUsers(): LiveData<List<User>> = mUserDao.getAllUsers()

    fun getUserByLogin(user: String): LiveData<User> = mUserDao.loadUserByLogin(user)

    fun insert(user: User) {
        executorService.execute { mUserDao.insert(user) }
    }

    fun delete(user: User) {
        executorService.execute { mUserDao.delete(user) }
    }
}