package com.example.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.model.FavoriteUserViewModel
import com.example.githubuser.model.ViewModelFactory
import com.example.githubuser.ui.ListFavUserAdapter
import com.example.restaurantreview.databinding.ActivityFavoriteUserBinding

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding
    private val favoriteUserViewModel by viewModels<FavoriteUserViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorite.addItemDecoration(itemDecoration)

        favoriteUserViewModel.getDataFavorite().observe(this){
            setFavoriteUser(it)
        }
    }

    private fun setFavoriteUser(favoriteUser: List<FavoriteUser>){
        val adapter = ListFavUserAdapter()
        adapter.submitList(favoriteUser)
        binding.rvFavorite.adapter = adapter
    }
}