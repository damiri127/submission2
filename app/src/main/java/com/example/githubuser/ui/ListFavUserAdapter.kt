package com.example.githubuser.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.ui.detail.DetailUserActivity
import com.example.restaurantreview.databinding.UserRowBinding

class ListFavUserAdapter: ListAdapter<FavoriteUser, ListFavUserAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFavUserAdapter.ListViewHolder {
        val binding = UserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        holder.itemView.setOnClickListener {
            val intentFavoriteUser = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intentFavoriteUser.putExtra(DetailUserActivity.EXTRA_USERNAME, data.username)
            holder.itemView.context.startActivity(intentFavoriteUser)
        }
    }

    inner class ListViewHolder(val binding: UserRowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: FavoriteUser){
            binding.tvItemName.text = user.username
            binding.apply {
                Glide.with(itemView.context)
                    .load("${user.avatarUrl}")
                    .into(imgItemPhoto)
            }
        }

    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUser>() {
            override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }
        }
    }
}