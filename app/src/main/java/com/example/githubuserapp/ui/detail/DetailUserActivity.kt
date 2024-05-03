package com.example.githubuserapp.ui.detail

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.ActivityDetailUserBinding


class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailUserBinding
    private lateinit var viewModel : DetailUserViewModel

    companion object{
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = Color.parseColor("#256656")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            setTheme(android.R.style.Theme_DeviceDefault_DayNight)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.white)
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
        }

        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)
        val bundle = Bundle()

        val username = intent.getStringExtra(EXTRA_USERNAME) ?: throw IllegalArgumentException("Username must not be null")
        viewModel.setUserDetail(username)
        bundle.putString(EXTRA_USERNAME, username)



        viewModel.getUserDetail().observe(this, { user ->
            user ?: return@observe // Keluar dari fungsi jika user null
            binding.apply {
                tvName.text = user.name
                tvUsername.text = user.login
                tvFollowers.text = "${user.followers} Followers"
                tvFollowing.text = "${user.following} Following"
                Glide.with(this@DetailUserActivity)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivProfile)
            }
            binding.pbProgressbar.visibility = View.GONE
        })

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }
}