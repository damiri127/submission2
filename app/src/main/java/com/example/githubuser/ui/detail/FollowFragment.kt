package com.example.githubuser.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.model.FollowViewModel
import com.example.restaurantreview.R
import com.example.restaurantreview.databinding.FragmentFollowBinding

class FollowFragment: Fragment(R.layout.fragment_follow) {
    private lateinit var binding: FragmentFollowBinding
    private lateinit var adapter: FollowersAdapter

    var username: String = ""
    var position: Int = 0

    companion object{
        const val ARG_POSTION = "position"
        const val ARG_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowBinding.bind(view)

        val followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowViewModel::class.java)

        val layoutManager = LinearLayoutManager(context)
        binding.rvFollowers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvFollowers.addItemDecoration(itemDecoration)

        adapter = FollowersAdapter()
        adapter.notifyDataSetChanged()

        followViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        arguments?.let {
            position = it.getInt(ARG_POSTION)
            username = it.getString(ARG_USERNAME).toString()
        }
        if (position == 1){
            followViewModel.getListFollowers(username)
            followViewModel.listFollowers.observe(viewLifecycleOwner){
                setData(it)
            }
        }else{
            followViewModel.getListFollowing(username)
            followViewModel.listFollowing.observe(viewLifecycleOwner){
                setData(it)
            }
        }
    }


    private fun setData(items: List<ItemsItem>) {
        val adapter = FollowersAdapter()
        adapter.submitList(items)
        binding.rvFollowers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }
}