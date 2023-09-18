package com.dicoding.aplicationgithubuser.ui.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.aplicationgithubuser.data.response.GitHubResponse
import com.dicoding.aplicationgithubuser.data.response.ItemsItem
import com.dicoding.aplicationgithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listGithub = MutableLiveData<List<ItemsItem>>()
    val listGithub: LiveData<List<ItemsItem>> =  _listGithub

    companion object{
        private const val TAG = "MainActivity"
        private const val USER_ID = "s"
    }
    init{
        getUserGithub()
    }

    fun getUserGithub(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithub(USER_ID)
        client.enqueue(object: Callback<GitHubResponse> {
            override fun onResponse(
                call: Call<GitHubResponse>,
                response: Response<GitHubResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _listGithub.value =response.body()?.items
                }
                else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "onFailure ${t.message.toString()}")
            }

        })
    }

     fun searchUserGithub(q : String){
        val client = ApiConfig.getApiService().getGithub(q)
        client.enqueue(object: Callback<GitHubResponse>{
            override fun onResponse(
                call: Call<GitHubResponse>,
                response: Response<GitHubResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _listGithub.value = response.body()?.items
                }
                else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure ${t.message.toString()}")
            }

        })
    }
}