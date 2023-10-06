package com.example.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.database.FavoriteUserDao
import com.example.githubuser.database.UserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserDatabase.getDatabase(application)
        mUserDao = db.favoriteUserDao()
    }

    fun insert(favoriteUser: FavoriteUser){
        executorService.execute { mUserDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser){
        executorService.execute { mUserDao.delete(favoriteUser) }
    }

    fun getUserFavorite(username: String):LiveData<FavoriteUser?> {
        return mUserDao.getFavoriteUserByUsername(username)
    }

    fun getDataFavorite():LiveData<List<FavoriteUser>>{
        return mUserDao.getDataFavorite()
    }
}