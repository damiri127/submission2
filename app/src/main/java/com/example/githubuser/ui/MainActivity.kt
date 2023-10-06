package com.example.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.model.DarkModeModelFactory
import com.example.githubuser.model.DarkModeViewModel
import com.example.githubuser.model.UserViewModel
import com.example.githubuser.ui.detail.FavoriteUserActivity
import com.example.restaurantreview.R
import com.example.restaurantreview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<UserViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        val layoutManager = LinearLayoutManager(this)
        binding.rvGithubUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvGithubUser.addItemDecoration(itemDecoration)

        adapter = ListUserAdapter()

        adapter.notifyDataSetChanged()

        mainViewModel.listUser.observe(this){items ->
            setGithubUser(items)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }


        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{ textView, actionId, event->
                    searchBar.text = searchView.text
                    val id = searchBar.text.toString()
                    searchView.hide()
                    mainViewModel.getUser(id)
                    false
                }

            binding.searchBar.setOnMenuItemClickListener {menuItem ->
                when(menuItem.itemId){
                    R.id.menu1 -> {
                        val intent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.setting ->{
                        val intent = Intent(this@MainActivity, DarkModeActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }

            }
        }

        val pref = SettingPreferences.getInstance(application.dataStore)
        val darkViewModel = ViewModelProvider(this, DarkModeModelFactory(pref)).get(
            DarkModeViewModel::class.java
        )

        darkViewModel.getThemeSettings().observe(this){isDarkModeActive:Boolean ->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }

    private fun setGithubUser(itemsItem: List<ItemsItem>){
        val adapter = ListUserAdapter()
        adapter.submitList(itemsItem)
        binding.rvGithubUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }
}