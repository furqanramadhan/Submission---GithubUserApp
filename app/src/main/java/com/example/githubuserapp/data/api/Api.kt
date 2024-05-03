package com.example.githubuserapp.data.api

import com.example.githubuserapp.data.model.UserData
import com.example.githubuserapp.data.model.UserDetailResponse
import com.example.githubuserapp.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    // Tambahkan enpoint /search/users?q={username}
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): retrofit2.Call<UserResponse>

    // Tambahkan endpoint users/{username}
    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUsers(
        @Path("username") username : String
    ): retrofit2.Call<UserDetailResponse>

    // Tambahkan endpoint users/{username}/followers
    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<UserData>>

    // Tambahkan endpoint users/{username}/following
    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<UserData>>
}