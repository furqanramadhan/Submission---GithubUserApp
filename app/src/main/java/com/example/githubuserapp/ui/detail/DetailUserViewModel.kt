package com.example.githubuserapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.api.RetrofitClient
import com.example.githubuserapp.data.model.UserDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel:ViewModel() {
    val user = MutableLiveData<UserDetailResponse>()

    fun setUserDetail(username: String){
        RetrofitClient.apiInstance
            .getDetailUsers(username)
            .enqueue(object : Callback<UserDetailResponse>{
                override fun onResponse(
                    call: Call<UserDetailResponse>,
                    response: Response<UserDetailResponse>
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }
    fun getUserDetail(): LiveData<UserDetailResponse>{
        return user
    }
}