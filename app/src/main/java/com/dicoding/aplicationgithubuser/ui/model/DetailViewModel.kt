package com.dicoding.aplicationgithubuser.ui.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.aplicationgithubuser.data.local.entity.FavoriteUserEntity
import com.dicoding.aplicationgithubuser.data.remote.response.DetailUserResponse
import com.dicoding.aplicationgithubuser.data.remote.retrofit.ApiConfig
import com.dicoding.aplicationgithubuser.repository.FavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "DetailUserActivity"

    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val detailGithub = MutableLiveData<DetailUserResponse?>()

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun detailUserGithub(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(query)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        detailGithub.postValue(responseBody)
                    }
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

    fun insert(favoriteUserEntity: FavoriteUserEntity) {
        mFavoriteRepository.insert(favoriteUserEntity)
    }

    fun getAllFavorite(): LiveData<List<FavoriteUserEntity>> {
        return mFavoriteRepository.getAllFavorite()

    }

    fun delete(favoriteUserEntity: FavoriteUserEntity) {
        mFavoriteRepository.delete(favoriteUserEntity)
    }

    fun getUserFavorite(username: String): LiveData<List<FavoriteUserEntity>> {
        return mFavoriteRepository.getFavoriteUserByUsername(username)
    }

}