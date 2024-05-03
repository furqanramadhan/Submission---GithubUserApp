package com.example.githubuserapp.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.data.model.UserData
import com.example.githubuserapp.ui.detail.DetailUserActivity
import com.example.githubuserapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    @SuppressLint("NotifyDataSetChanged")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.parseColor("#256656")
        actionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            setTheme(android.R.style.Theme_DeviceDefault_DayNight)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.white)
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClick(data: UserData) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    startActivity(it)
                }
            }

        })

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        viewModel.getSearchUsers().observe(this) { userList ->
            if (userList != null) {
                adapter.setList(userList)
                binding.pbProgressbar.visibility = View.GONE

                if (userList.isEmpty()) {
                    binding.tvTextviewempty.visibility = View.VISIBLE
                    binding.tvTextviewempty.text = getString(R.string.user_empty)
                } else {
                    binding.tvTextviewempty.visibility = View.GONE
                }
            }
        }

        with(binding){
            rvRecycleview.layoutManager = LinearLayoutManager(this@MainActivity)
            rvRecycleview.setHasFixedSize(true)
            rvRecycleview.adapter = adapter

            svSearchview.setupWithSearchBar(sbSearchbar)
            svSearchview
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    sbSearchbar.setText(svSearchview.text)
                    svSearchview.hide()
                    val query = svSearchview.text.toString().trim()
                    if(query.isNotEmpty()){
                        binding.tvTextviewempty.visibility= View.GONE
                        binding.pbProgressbar.visibility=View.VISIBLE
                        viewModel.setSearchUsers(query)
                    } else {
                        binding.tvTextviewempty.visibility=View.VISIBLE
                        adapter.getUserList().clear()
                    }
                    false
                }
        }
        viewModel.getSearchUsers().observe(this){
            if(it != null){
                adapter.setList(it)
                binding.pbProgressbar.visibility = View.GONE

                if(adapter.getUserList().isEmpty()){
                    binding.tvTextviewempty.visibility= View.VISIBLE
                } else {
                    binding.tvTextviewempty.visibility=View.GONE
                }
            }
        }
    }
    private fun showLoading(state: Boolean){
        if(state){
            binding.pbProgressbar.visibility=View.VISIBLE
        }
        else{
            binding.pbProgressbar.visibility=View.GONE
        }
    }
}