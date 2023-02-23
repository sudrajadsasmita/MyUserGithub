package com.sdssoft.myusergithub.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sdssoft.myusergithub.model.FollowerResponseItem
import com.sdssoft.myusergithub.R
import com.sdssoft.myusergithub.databinding.ItemFollowerBinding

class FollowerAdapter : RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    private val mData = ArrayList<FollowerResponseItem>()

    fun setData(item: List<FollowerResponseItem>) {
        mData.clear()
        mData.addAll(item)
        notifyDataSetChanged()
    }

    fun onItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class FollowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFollowerBinding.bind(itemView)
        fun binding(followerResponseItem: FollowerResponseItem) {
            binding.tvUsername.text = followerResponseItem.login
            Glide.with(itemView.context)
                .load(followerResponseItem.avatarUrl)
                .into(binding.imgProfile)
            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(followerResponseItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_follower, parent, false)
        return FollowerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.binding(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    interface OnItemClickCallback {
        fun onItemClicked(followerResponseItem: FollowerResponseItem)
    }
}