package com.sdssoft.myusergithub.helper

import androidx.recyclerview.widget.DiffUtil
import com.sdssoft.myusergithub.database.User

class UserDiffCallback(private val mOldUserList: List<User>, private val mNewUserList: List<User>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = mOldUserList.size

    override fun getNewListSize(): Int = mNewUserList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        mOldUserList[oldItemPosition].id == mNewUserList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldUserList[oldItemPosition]
        val newEmployee = mNewUserList[newItemPosition]
        return oldEmployee.login == newEmployee.login && oldEmployee.avatar == newEmployee.avatar
    }
}