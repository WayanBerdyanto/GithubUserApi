package com.dicoding.aplicationgithubuser.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.aplicationgithubuser.data.response.ItemsItem
import com.dicoding.aplicationgithubuser.databinding.ItemRowGithubBinding
import com.dicoding.aplicationgithubuser.ui.DetailUserActivity

class ListFollowersAdapter: ListAdapter<ItemsItem, ListFollowersAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemRowGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = getItem(position)
        holder.bind(list)
    }
    class MyViewHolder(val binding: ItemRowGithubBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(github: ItemsItem){
            Glide.with(binding.root.context)
                .load(github.avatarUrl)
                .into(binding.imgItemPhoto)
            binding.tvUsername.text = "${github.login}"

            itemView.setOnClickListener {
                val intentDetail = Intent(itemView.context, DetailUserActivity::class.java)
                intentDetail.putExtra("key_id", github.login)
                itemView.context.startActivity(intentDetail)
            }
        }

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}