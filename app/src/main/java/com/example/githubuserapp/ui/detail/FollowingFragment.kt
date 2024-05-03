package com.example.githubuserapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.FragmentFollowingBinding
import com.example.githubuserapp.ui.main.UserAdapter

class FollowingFragment:Fragment(R.layout.fragment_following) {


    private var _binding : FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var username: String
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FollowingViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowingBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvRecycleview.setHasFixedSize(true)
            rvRecycleview.layoutManager = LinearLayoutManager(activity)
            rvRecycleview.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner, {
            if (it!=null){
                adapter.setList(it)
                showLoading(false)
            }
        })
    }
    private fun showLoading(state: Boolean){
        if(state){
            binding.pbProgressbar.visibility= View.VISIBLE
        }
        else{
            binding.pbProgressbar.visibility= View.GONE
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}