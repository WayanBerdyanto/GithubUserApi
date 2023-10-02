package com.dicoding.aplicationgithubuser.ui.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.aplicationgithubuser.data.remote.response.DetailUserResponse
import com.dicoding.aplicationgithubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    companion object {
        private const val TAG = "DetailUserActivity"
    }

    val USER_ID = "wayanberdyanto"


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _myProfile = MutableLiveData<DetailUserResponse>()
    val myProfile: LiveData<DetailUserResponse> = _myProfile

    init {
        myUserGithub()
    }

    private fun myUserGithub() {
        val client = ApiConfig.getApiService().getDetailUser(USER_ID)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _myProfile.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure ${t.message.toString()}")
            }
        })
    }
}