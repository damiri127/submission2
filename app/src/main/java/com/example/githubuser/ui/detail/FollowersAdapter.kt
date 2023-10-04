package com.example.githubuser.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuser.data.response.ItemsItem
import com.example.restaurantreview.databinding.UserRowBinding

class FollowersAdapter: ListAdapter<ItemsItem, FollowersAdapter.ViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersAdapter.ViewHolder {
        val binding = UserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowersAdapter.ViewHolder, position: Int) {
        val follower = getItem(position)
        holder.bind(follower)
    }

    class ViewHolder(val binding: UserRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(follower: ItemsItem){
            binding.apply {
                Glide.with(itemView)
                    .load(follower.avatarUrl)
                    .centerCrop()
                    .into(imgItemPhoto)
            }
            binding.tvItemName.text = follower.login

        }
    }
    companion object{
        val DiffCallback = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}