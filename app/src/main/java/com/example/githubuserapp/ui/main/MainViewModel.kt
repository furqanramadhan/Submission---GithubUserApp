package com.example.githubuserapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.api.RetrofitClient
import com.example.githubuserapp.data.model.UserData
import com.example.githubuserapp.data.model.UserResponse
import retrofit2.Call
import retrofit2.Response

class MainViewModel : ViewModel(){
    val userList = MutableLiveData<ArrayList<UserData>>()
    fun setSearchUsers(query: String){
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : retrofit2.Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if(response.isSuccessful){
                        userList.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })

    }
    fun getSearchUsers(): LiveData<ArrayList<UserData>>{
        return userList
    }
}