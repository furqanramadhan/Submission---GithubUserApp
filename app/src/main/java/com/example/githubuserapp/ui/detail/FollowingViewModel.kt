package com.example.githubuserapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.api.RetrofitClient
import com.example.githubuserapp.data.model.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<UserData>>()

    fun getListFollowing(): LiveData<ArrayList<UserData>>{
        return listFollowing
    }

    fun setListFollowing(username : String){
        RetrofitClient.apiInstance
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<UserData>>{
                override fun onResponse(
                    call: Call<ArrayList<UserData>>,
                    response: Response<ArrayList<UserData>>
                ) {
                    if (response.isSuccessful){
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(p0: Call<ArrayList<UserData>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }
}