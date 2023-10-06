package com.example.githubuser.model

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.repository.FavoriteRepository

class FavoriteUserViewModel(application: Application): ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    val favoriteUser = FavoriteUser()

    fun insert(favoriteUser: FavoriteUser){
        mFavoriteRepository.insert(favoriteUser)
    }

    fun getDataFavorite() = mFavoriteRepository.getDataFavorite()

    fun getUserFavorite() = mFavoriteRepository.getUserFavorite(favoriteUser.username)

    fun delete(favoriteUser: FavoriteUser){
        mFavoriteRepository.delete(favoriteUser)
    }

}