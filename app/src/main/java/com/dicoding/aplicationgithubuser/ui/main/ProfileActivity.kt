package com.dicoding.aplicationgithubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.aplicationgithubuser.R
import com.dicoding.aplicationgithubuser.databinding.ActivityProfileBinding
import com.dicoding.aplicationgithubuser.ui.adapter.SectionsPagerAdapter
import com.dicoding.aplicationgithubuser.ui.model.ProfileViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private val profile by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profile.myProfile.observe(this) {
            showLoading(true)
            Glide.with(this)
                .load(it.avatarUrl)
                .into(binding.imgProfileUser)
            with(binding) {
                tvNameProfil.text = it.name
                tvUsername.text = it.login
                tvBioUser.text = it.bio
                tvCountFollower.text = "${it.followers} Followers"
                tvCountFollowing.text = "${it.following} Followings"
            }
            showLoading(false)
        }
        val sectionsPagerAdapter = SectionsPagerAdapter(this, profile.USER_ID)
        val viewPager: ViewPager2 = binding.viewPagerProfile
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        profile.isLoading.observe(this, this::showLoading)
        supportActionBar?.elevation = 0f

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_share -> {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/type"
                    val shareText = "https://github.com/${binding.tvUsername.text}"
                    intent.putExtra(Intent.EXTRA_TEXT, shareText)
                    startActivity(Intent.createChooser(intent, "Bagikan melalui"))
                    true
                }

                else -> false
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following
        )
    }
}