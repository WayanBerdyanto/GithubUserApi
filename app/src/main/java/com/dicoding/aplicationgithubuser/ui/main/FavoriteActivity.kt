package com.dicoding.aplicationgithubuser.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.aplicationgithubuser.data.local.entity.FavoriteUserEntity
import com.dicoding.aplicationgithubuser.databinding.ActivityFavoriteBinding
import com.dicoding.aplicationgithubuser.helper.ViewModelFactory
import com.dicoding.aplicationgithubuser.ui.adapter.FavoriteListAdapter
import com.dicoding.aplicationgithubuser.ui.model.DetailViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val viewModel by viewModels<DetailViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isLoading.observe(this, this::showLoading)


        viewModel.getAllFavorite().observe(this) {
            showLoading(true)
            setListFavorite(it)
            showLoading(false)
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setListFavorite(list: List<FavoriteUserEntity>) {
        val adapter = FavoriteListAdapter()
        val layoutManager = LinearLayoutManager(this)
        adapter.submitList(list)
        binding.rvListGithub.layoutManager = layoutManager
        binding.rvListGithub.adapter = adapter

    }
}