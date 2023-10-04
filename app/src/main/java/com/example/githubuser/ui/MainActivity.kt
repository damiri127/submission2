package com.example.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.model.UserViewModel
import com.example.restaurantreview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<UserViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListUserAdapter

    companion object{
        private const val TAG = "MainActivity"
    }

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