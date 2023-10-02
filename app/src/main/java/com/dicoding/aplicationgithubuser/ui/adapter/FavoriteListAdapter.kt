package com.dicoding.aplicationgithubuser.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.aplicationgithubuser.R
import com.dicoding.aplicationgithubuser.data.local.entity.FavoriteUserEntity
import com.dicoding.aplicationgithubuser.databinding.ItemRowGithubBinding
import com.dicoding.aplicationgithubuser.ui.main.DetailUserActivity

class FavoriteListAdapter :
    ListAdapter<FavoriteUserEntity, FavoriteListAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteListAdapter.ViewHolder {
        val binding =
            ItemRowGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteListAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteListAdapter.ViewHolder, position: Int) {
        val list = getItem(position)
        holder.bind(list)
    }

    class ViewHolder(val binding: ItemRowGithubBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(github: FavoriteUserEntity) {
            Glide.with(binding.root.context)
                .load(github.avatarUrl)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error)
                )
                .into(binding.imgItemPhoto)
            binding.tvUsername.text = github.username

            itemView.setOnClickListener {
                val intentDetail = Intent(itemView.context, DetailUserActivity::class.java)
                intentDetail.putExtra(UserGithubAdapter.USER_ID, github.username)
                intentDetail.putExtra(UserGithubAdapter.AVATAR, github.avatarUrl)
                itemView.context.startActivity(intentDetail)
            }
        }
    }

    companion object {
        const val KEY_ID = "key_id"
        const val AVATAR = "avatar"
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUserEntity>() {
            override fun areItemsTheSame(
                oldItem: FavoriteUserEntity,
                newItem: FavoriteUserEntity
            ): Boolean {
                return oldItem.username == newItem.username
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: FavoriteUserEntity,
                newItem: FavoriteUserEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}