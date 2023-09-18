package com.dicoding.aplicationgithubuser.ui.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.aplicationgithubuser.data.response.ItemsItem
import com.dicoding.aplicationgithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFollowModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _getFollowers = MutableLiveData<List<ItemsItem>>()
    val getFollowers: LiveData<List<ItemsItem>> = _getFollowers

    companion object{
        private const val TAG = "FollowerFragment"
    }

    fun listFollowers(query: String){
        val client = ApiConfig.getApiService().getFollowers(query)
        client.enqueue(object: Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _getFollowers.value = response.body()
                }
                else{
                    Log.e(ListFollowModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(ListFollowModel.TAG, "onFailure ${t.message.toString()}")
            }
        })
    }

    fun listFollowings(query: String){
        val client = ApiConfig.getApiService().getFollowings(query)
        client.enqueue(object: Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _getFollowers.value = response.body()
                }
                else{
                    Log.e(ListFollowModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(ListFollowModel.TAG, "onFailure ${t.message.toString()}")
            }
        })
    }


}