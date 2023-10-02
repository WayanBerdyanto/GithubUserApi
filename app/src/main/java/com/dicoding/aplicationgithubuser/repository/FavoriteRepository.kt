package com.dicoding.aplicationgithubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.aplicationgithubuser.data.local.entity.FavoriteUserEntity
import com.dicoding.aplicationgithubuser.data.local.room.FavoriteDao
import com.dicoding.aplicationgithubuser.data.local.room.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavDao = db.getFavoriteDao()
    }

    fun getAllFavorite(): LiveData<List<FavoriteUserEntity>> = mFavDao.getAllFavorite()

    fun insert(fav: FavoriteUserEntity) {
        executorService.execute { mFavDao.insertFavorite(fav) }
    }

    fun delete(fav: FavoriteUserEntity) {
        executorService.execute { mFavDao.deleteFavorite(fav) }
    }

    fun getFavoriteUserByUsername(q: String): LiveData<List<FavoriteUserEntity>> {
        return mFavDao.getFavoriteUserByUsername(q)
    }
}