package com.example.githubuserapp.data.model

data class UserDetailResponse(
    val login : String,
    val id : String,
    val avatar_url : String,
    val followers_url : String,
    val following_url : String,
    val name : String,
    val followers : Int,
    val following : Int
)
