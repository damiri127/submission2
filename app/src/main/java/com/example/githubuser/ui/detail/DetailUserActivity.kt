package com.example.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuser.model.DetailUserViewModel
import com.example.githubuser.model.FavoriteUserViewModel
import com.example.githubuser.model.ViewModelFactory
import com.example.restaurantreview.R
import com.example.restaurantreview.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailUserViewModel: DetailUserViewModel

    private val favoriteUserViewModel by viewModels<FavoriteUserViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private val viewModel by viewModels<DetailUserViewModel> {
        ViewModelFactory.getInstance(application)
    }


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

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        // menghubungkan viewModel dengan activity

        detailUserViewModel = obtainViewModel(this@DetailUserActivity)


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

                    favoriteUserViewModel.favoriteUser.username = it.login
                    favoriteUserViewModel.favoriteUser.avatarUrl = it.avatarUrl

                    favoriteUserViewModel.getUserFavorite().observe(this@DetailUserActivity){
                        binding.fab.isChecked = it?.username != null

                        binding.fab.setOnClickListener {fav ->
                            if(it?.username != null){
                                favoriteUserViewModel.delete(viewModel.favUser)
                            }else{
                                favoriteUserViewModel.insert(viewModel.favUser)
                            }
                        }
                    }
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

    private fun obtainViewModel(activity: AppCompatActivity): DetailUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailUserViewModel::class.java)
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }
}