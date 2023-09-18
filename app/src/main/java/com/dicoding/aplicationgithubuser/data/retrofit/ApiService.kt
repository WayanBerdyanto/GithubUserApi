package com.dicoding.aplicationgithubuser.data.retrofit

import com.dicoding.aplicationgithubuser.data.response.DetailUserResponse
import com.dicoding.aplicationgithubuser.data.response.GitHubResponse
import com.dicoding.aplicationgithubuser.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getGithub(
        @Query("q") id : String
    ): Call<GitHubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowings(
        @Path("username") username: String
    ): Call<List<ItemsItem>>


}
