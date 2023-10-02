package com.dicoding.aplicationgithubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.aplicationgithubuser.R
import com.dicoding.aplicationgithubuser.data.local.entity.FavoriteUserEntity
import com.dicoding.aplicationgithubuser.databinding.ActivityDetailUserBinding
import com.dicoding.aplicationgithubuser.helper.ViewModelFactory
import com.dicoding.aplicationgithubuser.ui.adapter.SectionsPagerAdapter
import com.dicoding.aplicationgithubuser.ui.model.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailUserBinding

    private val detailViewModel by viewModels<DetailViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    private var favoriteUserEntity: FavoriteUserEntity = FavoriteUserEntity(USER_ID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        val user = intent.getStringExtra(USER_ID).toString()
        val avatar = intent.getStringExtra(AVATAR)

        detailViewModel.detailUserGithub(user)
        detailViewModel.detailGithub.observe(this) {
            showLoading(true)
            Glide.with(this)
                .load(it?.avatarUrl)
                .into(detailBinding.imgProfileUser)
            with(detailBinding) {
                tvNameProfil.text = it?.name
                tvUsername.text = it?.login
                tvCountFollower.text = "${it?.followers} Followers"
                tvCountFollowing.text = "${it?.following} Following"
            }
            showLoading(false)

        }

        var isLike: Boolean = false
        detailViewModel.getUserFavorite(user).observe(this) { users ->
            isLike = users.isNotEmpty()
            if (isLike) {
                val fabAdd = detailBinding.fabAdd
                fabAdd.setImageDrawable(
                    ContextCompat.getDrawable(
                        fabAdd.context,
                        R.drawable.baseline_favorite_24
                    )
                )
                favoriteUserEntity = users[0]
            } else {
                val fabAdd = detailBinding.fabAdd
                fabAdd.setImageDrawable(
                    ContextCompat.getDrawable(
                        fabAdd.context,
                        R.drawable.baseline_favorite_border_24
                    )
                )
            }
        }

        detailBinding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_share -> {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/type"
                    val shareText = "https://github.com/${user}"
                    intent.putExtra(Intent.EXTRA_TEXT, shareText)
                    startActivity(Intent.createChooser(intent, "Bagikan melalui"))
                    true
                }

                else -> false
            }
        }

        detailBinding.fabAdd.setOnClickListener {
            if (isLike) {
                detailViewModel.delete(favoriteUserEntity)
            } else {
                val userData = FavoriteUserEntity(username = user, avatarUrl = avatar)
                detailViewModel.insert(userData)
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, user)
        val viewPager: ViewPager2 = detailBinding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = detailBinding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.isLoading.observe(this, this::showLoading)
        supportActionBar?.elevation = 0f
    }

    private fun showLoading(isLoading: Boolean) {
        detailBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val USER_ID = "key_id"
        const val AVATAR = "avatar"


        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following
        )
    }


}