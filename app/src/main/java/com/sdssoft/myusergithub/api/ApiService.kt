package com.sdssoft.myusergithub.api

import com.sdssoft.myusergithub.model.DetailResponse
import com.sdssoft.myusergithub.model.FollowerResponseItem
import com.sdssoft.myusergithub.model.FollowingResponseItem
import com.sdssoft.myusergithub.model.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getUser(@Query("q") username: String): Call<UsersResponse>

    @GET("users/{username}/followers")
    fun getFollower(@Path("username") username: String): Call<List<FollowerResponseItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<FollowingResponseItem>>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailResponse>
}