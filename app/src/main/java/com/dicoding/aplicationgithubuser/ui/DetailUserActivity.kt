package com.dicoding.aplicationgithubuser.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.aplicationgithubuser.R
import com.dicoding.aplicationgithubuser.databinding.ActivityDetailUserBinding
import com.dicoding.aplicationgithubuser.ui.adapter.SectionsPagerAdapter
import com.dicoding.aplicationgithubuser.ui.model.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private  lateinit var detailBinding: ActivityDetailUserBinding

    private val detailViewModel by viewModels<DetailViewModel>()

    companion object{
        const val KEY_ID = "key_id"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        val user = intent.getStringExtra(KEY_ID)!!

        detailViewModel.detailUserGithub(user)
        detailViewModel.detailGithub.observe(this){
            showLoading(true)
            Glide.with(this)
                .load(it.avatarUrl)
                .into(detailBinding.imgProfileUser)
            with(detailBinding){
                tvNameProfil.text = it.name
                tvUsername.text = it.login
                tvCountFollower.text = "${it.followers} Followers"
                tvCountFollowing.text = "${it.following} Following"
            }
            showLoading(false)

        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, user)
        val viewPager: ViewPager2 = detailBinding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = detailBinding.tabs
        TabLayoutMediator(tabs, viewPager){tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.isLoading.observe(this, this::showLoading)
        supportActionBar?.elevation = 0f
    }
    private fun showLoading(isLoading: Boolean) {
        detailBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}