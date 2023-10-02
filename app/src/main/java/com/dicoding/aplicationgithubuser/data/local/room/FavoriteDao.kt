package com.dicoding.aplicationgithubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.aplicationgithubuser.data.local.entity.FavoriteUserEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favoriteUser")
    fun getAllFavorite(): LiveData<List<FavoriteUserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favorite: FavoriteUserEntity)

    @Delete
    fun deleteFavorite(favorite: FavoriteUserEntity)

    @Query("SELECT * FROM favoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<List<FavoriteUserEntity>>

}