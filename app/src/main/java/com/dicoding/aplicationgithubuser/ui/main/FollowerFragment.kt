package com.dicoding.aplicationgithubuser.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.aplicationgithubuser.data.remote.response.ItemsItem
import com.dicoding.aplicationgithubuser.databinding.FragmentFollowerBinding
import com.dicoding.aplicationgithubuser.ui.adapter.UserGithubAdapter
import com.dicoding.aplicationgithubuser.ui.model.ListFollowModel


class FollowerFragment : Fragment() {

    private lateinit var binding: FragmentFollowerBinding
    private var username: String? = null

    private val listFollowModel by viewModels<ListFollowModel>()

    var index: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            index = it.getInt(ARG_POSITION)
            username = it.getString(KEY_ID)
        }

        showLoading(true)
        binding.apply {
            if (index == 1) {
                username?.let { listFollowModel.listFollowers(it) }
                listFollowModel.getFollowers.observe(viewLifecycleOwner) {
                    Log.d("String", "$it")
                    showListFollow(it)
                    showLoading(false)

                }
            } else {
                username?.let { listFollowModel.listFollowings(it) }
                listFollowModel.getFollowers.observe(viewLifecycleOwner) {
                    showListFollow(it)
                    showLoading(false)
                }
            }
        }
    }

    private fun showListFollow(listFollow: List<ItemsItem>) {
        binding.rvListFollowers.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = UserGithubAdapter()
        adapter.submitList(listFollow)
        binding.rvListFollowers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION = "position"
        const val KEY_ID = "key_id"
    }

}