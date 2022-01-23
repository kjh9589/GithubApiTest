package com.teamnoyes.githubtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamnoyes.githubtest.databinding.ItemUserBinding
import com.teamnoyes.githubtest.domain.model.ModelUser
import com.teamnoyes.githubtest.utils.setOnSingleClickListener

class UserListAdapter(private val itemClicked: (ModelUser) -> Unit) :
    ListAdapter<ModelUser, UserListAdapter.ViewHolder>(diffUtil) {
    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<ModelUser>() {
            override fun areItemsTheSame(oldItem: ModelUser, newItem: ModelUser): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areContentsTheSame(oldItem: ModelUser, newItem: ModelUser): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModelUser) = with(binding) {
            initViews()

            Glide.with(this.root.context)
                .load(item.userProfileUri)
                .centerCrop()
                .into(userProfileImageView)

            binding.modelUser = item
            binding.executePendingBindings()

            binding.root.setOnSingleClickListener {
                itemClicked(item)
            }

        }

        private fun initViews() = with(binding) {
            userNameTextView.text = ""
            userProfileImageView.setImageDrawable(null)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}