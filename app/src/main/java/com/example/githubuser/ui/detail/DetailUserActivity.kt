package com.example.githubuser.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.model.DetailUserViewModel
import com.example.githubuser.model.ViewModelFactory
import com.example.restaurantreview.R
import com.example.restaurantreview.databinding.ActivityDetailUserBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding

    private val viewModel by viewModels<DetailUserViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private val favoriteUser = FavoriteUser()

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val AVATAR_URL = "avatarUrl"
    }

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tabs_1, R.string.tabs_2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val avatar = intent.getStringExtra(AVATAR_URL)

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        // set favorite user
        binding.fab.setOnClickListener {
            viewModel.insert(favoriteUser)
        }

        if (username != null) {
            viewModel.setUserDetail(username)
        }
        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = resources.getString(R.string.follower, it.followers)
                    tvFollowing.text = resources.getString(R.string.following, it.following)
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatarUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivProfile)
                }
            }

            val sectionPagerAdapter = SectionPagerAdapter(this)
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = sectionPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tabs)
            sectionPagerAdapter.username = username.toString()
            TabLayoutMediator(tabs, viewPager){tab, position->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

        }
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }
}