package com.dicoding.aplicationgithubuser.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.aplicationgithubuser.R
import com.dicoding.aplicationgithubuser.data.response.ItemsItem
import com.dicoding.aplicationgithubuser.databinding.ActivityMainBinding
import com.dicoding.aplicationgithubuser.ui.adapter.UserGithubAdapter
import com.dicoding.aplicationgithubuser.ui.model.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    showLoading(true)
                    searchBar.text = searchView.text
                    mainViewModel.searchUserGithub(searchView.text.toString())
                    searchView.hide()
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    false
                }
            showLoading(false)
        }
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        val layoutManager = LinearLayoutManager(this)
        binding.rvListGithub.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvListGithub.addItemDecoration(itemDecoration)

        mainViewModel.listGithub.observe(this){ items ->
            setGithubListData(items)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        binding.topAppBar.setOnMenuItemClickListener{ profile ->
            when(profile.itemId){
                R.id.menu2 -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun setGithubListData(githubList: List<ItemsItem>) {
        val adapter = UserGithubAdapter()
        adapter.submitList(githubList)
        binding.rvListGithub.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}