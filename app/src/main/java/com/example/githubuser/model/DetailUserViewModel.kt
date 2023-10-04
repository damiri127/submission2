package com.example.githubuser.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.api.ApiConfig
import com.example.githubuser.data.response.DetailUserResponse
import com.example.githubuser.database.FavoriteUserDao
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.database.UserDatabase
import com.example.githubuser.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : ViewModel() {
    private val user = MutableLiveData<DetailUserResponse>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    val isLoading: LiveData<Boolean> = _isLoading

    fun insert(favoriteUser: FavoriteUser){
        mFavoriteRepository.insert(favoriteUser)
    }

    fun getAllData(): LiveData<List<FavoriteUser>> = mFavoriteRepository.getAllData()

    companion object {
        const val TAG = "DetailUserModel"
    }

    fun setUserDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    user.postValue(response.body())
                } else {
                    val errorBody = response.errorBody()
                    Log.e(TAG, "errornya ${errorBody.toString()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                // tampilkan pesan error ke pengguna (jangan menggagunakan log)
                //Toast.makeText(this@DetailUserViewModel,"${t.message.toString()}", Toast.LENGTH_LONG)
            }

        })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }
}