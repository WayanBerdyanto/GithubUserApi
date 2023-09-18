package com.dicoding.aplicationgithubuser.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.aplicationgithubuser.ui.FollowerFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private var username: String): FragmentStateAdapter(activity) {

//    var username: String = ""

    override fun getItemCount(): Int {
        return 2
    }
    override fun createFragment(position: Int): Fragment {

        val fragment = FollowerFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowerFragment.ARG_POSITION, position + 1)
            putString(FollowerFragment.KEY_ID, username)
        }
        return fragment
    }
}