package com.sdssoft.myusergithub.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT*FROM user ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT*FROM user WHERE login = :userLogin")
    fun loadUserByLogin(userLogin: String): LiveData<User>
}