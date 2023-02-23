package com.sdssoft.myusergithub.ui.insert

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sdssoft.myusergithub.api.ApiConfig
import com.sdssoft.myusergithub.model.ItemsItem
import com.sdssoft.myusergithub.model.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

        private val _isLoading = MutableLiveData<Boolean>()
        val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private val TAG = MainViewModel::class.java.simpleName

    }

    fun findUsername(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                try {
                    _isLoading.value = false
                    _listUser.value = response.body()?.items
                } catch (e: Exception) {
                    Log.e(TAG, e.message.toString())
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}