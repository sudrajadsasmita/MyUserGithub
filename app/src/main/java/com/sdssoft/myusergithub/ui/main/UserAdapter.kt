package com.sdssoft.myusergithub.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sdssoft.myusergithub.model.ItemsItem
import com.sdssoft.myusergithub.R
import com.sdssoft.myusergithub.databinding.ItemUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    private val mData= ArrayList<ItemsItem>()

    fun setData(item:List<ItemsItem> ){
        mData.clear()
        mData.addAll(item)
        notifyDataSetChanged()
    }

    fun onItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)
        fun bind(itemsItem: ItemsItem) {
            binding.tvUsername.text = itemsItem.login
            Glide.with(itemView.context)
                .load(itemsItem.avatarUrl)
                .into(binding.imgProfile)
            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(itemsItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    interface OnItemClickCallback {
        fun onItemClicked(itemsItem: ItemsItem)
    }
}