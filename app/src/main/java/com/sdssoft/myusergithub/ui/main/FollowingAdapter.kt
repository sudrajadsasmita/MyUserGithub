package com.sdssoft.myusergithub.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sdssoft.myusergithub.model.FollowingResponseItem
import com.sdssoft.myusergithub.R
import com.sdssoft.myusergithub.databinding.ItemFollowingBinding

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    private val mData = ArrayList<FollowingResponseItem>()

    fun setData(item: List<FollowingResponseItem>) {
        mData.clear()
        mData.addAll(item)
        notifyDataSetChanged()
    }

    fun onItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFollowingBinding.bind(itemView)
        fun binding(followingResponseItem: FollowingResponseItem) {
            binding.tvUsername.text = followingResponseItem.login
            Glide.with(itemView.context)
                .load(followingResponseItem.avatarUrl)
                .into(binding.imgProfile)
            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(followingResponseItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_following, parent, false)
        return FollowingViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.binding(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    interface OnItemClickCallback {
        fun onItemClicked(followingResponseItem: FollowingResponseItem)
    }
}