package com.example.githubuserapp.ui.main

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuserapp.data.model.UserData
import com.example.githubuserapp.databinding.ItemUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){
    private var onItemClickCallback: OnItemClickCallback? = null
    private val list = ArrayList<UserData>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users : ArrayList<UserData>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }
    inner class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(userData: UserData){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClick(userData)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(userData.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(shImageview)
                tvUsername.text = userData.login
                tvTypeuser.text = userData.type
            }

            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClick(userData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
       holder.bind(list[position])
    }

    fun getUserList(): ArrayList<UserData> {
        return list
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClick(userData: UserData)
    }

}