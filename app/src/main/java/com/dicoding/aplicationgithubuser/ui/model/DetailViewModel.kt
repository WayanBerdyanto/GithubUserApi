package com.dicoding.aplicationgithubuser.ui.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.aplicationgithubuser.data.response.DetailUserResponse
import com.dicoding.aplicationgithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    companion object{
        private const val TAG = "DetailUserActivity"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailGithub = MutableLiveData<DetailUserResponse>()
    val detailGithub: LiveData<DetailUserResponse> = _detailGithub

    fun detailUserGithub(query: String){
        val client = ApiConfig.getApiService().getDetailUser(query)
        client.enqueue(object: Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _detailGithub.value = response.body()
                }
                else{
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