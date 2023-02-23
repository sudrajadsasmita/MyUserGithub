package com.sdssoft.myusergithub.ui.insert

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sdssoft.myusergithub.api.ApiConfig
import com.sdssoft.myusergithub.model.FollowingResponseItem
import com.sdssoft.myusergithub.ui.main.FollowerFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel:ViewModel() {
    private val _listUser = MutableLiveData<List<FollowingResponseItem>>()
    val listUser: LiveData<List<FollowingResponseItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private val TAG = FollowerFragment::class.java.simpleName

    }

    fun showFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<FollowingResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowingResponseItem>>,
                response: Response<List<FollowingResponseItem>>
            ) {
                try {
                    _isLoading.value = false
                    _listUser.value = response.body()
                } catch (e: Exception) {
                    Log.e(TAG, e.message.toString())
                }
            }
            override fun onFailure(call: Call<List<FollowingResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}