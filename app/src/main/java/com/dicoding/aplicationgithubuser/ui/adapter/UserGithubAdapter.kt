package com.dicoding.aplicationgithubuser.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.aplicationgithubuser.R
import com.dicoding.aplicationgithubuser.data.remote.response.ItemsItem
import com.dicoding.aplicationgithubuser.databinding.ItemRowGithubBinding
import com.dicoding.aplicationgithubuser.ui.main.DetailUserActivity

class UserGithubAdapter : ListAdapter<ItemsItem, UserGithubAdapter.MyViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemRowGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = getItem(position)
        if (list == null) {
            holder.setIsInTheMiddle(true)
        } else {
            holder.setIsInTheMiddle(false)
            holder.bind(list)
        }
    }

    class MyViewHolder(val binding: ItemRowGithubBinding) : RecyclerView.ViewHolder(binding.root) {
        private var mIsInTheMiddle = false
        fun bind(github: ItemsItem) {
            Glide.with(binding.root.context)
                .load(github.avatarUrl)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error)
                )
                .into(binding.imgItemPhoto)
            binding.tvUsername.text = github.login

            itemView.setOnClickListener {
                val intentDetail = Intent(itemView.context, DetailUserActivity::class.java)
                intentDetail.putExtra(USER_ID, github.login)
                intentDetail.putExtra(AVATAR, github.avatarUrl)
                itemView.context.startActivity(intentDetail)
            }
        }

        fun getIsInTheMiddle(): Boolean {
            return mIsInTheMiddle
        }

        fun setIsInTheMiddle(isInTheMiddle: Boolean) {
            mIsInTheMiddle = isInTheMiddle
        }
    }


    companion object {
        const val USER_ID = "key_id"
        const val AVATAR = "avatar"
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