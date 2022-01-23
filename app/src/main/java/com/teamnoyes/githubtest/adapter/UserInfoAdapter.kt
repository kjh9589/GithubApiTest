package com.teamnoyes.githubtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamnoyes.githubtest.databinding.*
import com.teamnoyes.githubtest.domain.model.ModelSealedUserDetail

class UserInfoAdapter : ListAdapter<ModelSealedUserDetail, RecyclerView.ViewHolder>(diffUtil) {
    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<ModelSealedUserDetail>() {
            override fun areItemsTheSame(
                oldItem: ModelSealedUserDetail,
                newItem: ModelSealedUserDetail
            ): Boolean {
                return oldItem.mKey == newItem.mKey
            }

            override fun areContentsTheSame(
                oldItem: ModelSealedUserDetail,
                newItem: ModelSealedUserDetail
            ): Boolean {
                return oldItem == newItem
            }
        }

        private const val ITEM_USER_INFO = 0
        private const val ITEM_USER_REPO = 1
        private const val ITEM_USER_NO_REPO = 2
        private const val ITEM_USER_EVENT = 3
        private const val ITEM_USER_NO_EVENT = 4
        private const val ITEM_USER_INFO_DIVIDER = 5
    }

    inner class UserInfoViewHolder(private val binding: ItemUserInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModelSealedUserDetail.ModelUserInfo) = with(binding) {
            initViews()

            Glide.with(this.root.context)
                .load(item.userProfileUri)
                .centerCrop()
                .into(userProfileImageView)

            userProfileImageView.clipToOutline = true
            binding.modelInfo = item
            binding.executePendingBindings()
        }

        private fun initViews() = with(binding) {
            userProfileImageView.setImageDrawable(null)
            userNameTextView.text = ""
            userIntroductionTextView.text = ""
        }
    }

    inner class UserRepoViewHolder(private val binding: ItemUserRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModelSealedUserDetail.ModelUserRepo) = with(binding) {
            initViews()
            binding.modelRepo = item
            binding.executePendingBindings()
        }

        private fun initViews() = with(binding) {
            userRepoNameTextView.text = ""
            userRepoDescriptionTextView.text = ""
            userRepoLangTextView.text = ""
            userStarCountTextView.text = ""
        }
    }

    inner class UserNoRepoViewHolder(private val binding: ItemNoUserRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModelSealedUserDetail.ModelNoUserRepo) = with(binding) {

        }

        private fun initViews() = with(binding) {

        }
    }

    inner class UserEventViewHolder(private val binding: ItemUserRecentEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModelSealedUserDetail.ModelUserEvent) = with(binding) {
            initViews()

            Glide.with(this.root.context)
                .load(item.userProfileUri)
                .centerCrop()
                .into(userEventProfileImageView)

            binding.modelEvent = item
            binding.executePendingBindings()
        }

        private fun initViews() = with(binding) {
            userEventProfileImageView.setImageDrawable(null)
            userEventNameTextView.text = ""
            userEventTypeTextView.text = ""
            userEventRepoNameTextView.text = ""
        }
    }

    inner class UserNoEventViewHolder(private val binding: ItemNoUserRecentEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModelSealedUserDetail.ModelNoUserEvent) = with(binding) {

        }

        private fun initViews() = with(binding) {

        }
    }

    inner class UserInfoDividerViewHolder(private val binding: ItemUserInfoDividerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModelSealedUserDetail.ModelUserDataDivider) = with(binding) {
            initViews()

            binding.modelUserInfoDivider = item
            binding.executePendingBindings()
        }

        private fun initViews() = with(binding) {
            userInfoDividerTextView.text = ""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_USER_INFO -> {
                UserInfoViewHolder(
                    ItemUserInfoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ITEM_USER_REPO -> {
                UserRepoViewHolder(
                    ItemUserRepoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ITEM_USER_NO_REPO -> {
                UserNoRepoViewHolder(
                    ItemNoUserRepoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ITEM_USER_EVENT -> {
                UserEventViewHolder(
                    ItemUserRecentEventBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ITEM_USER_NO_EVENT -> {
                UserNoEventViewHolder(
                    ItemNoUserRecentEventBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ITEM_USER_INFO_DIVIDER -> {
                UserInfoDividerViewHolder(
                    ItemUserInfoDividerBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                throw ClassCastException("UnKnown")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserInfoViewHolder -> {
                val item = currentList[position] as ModelSealedUserDetail.ModelUserInfo
                holder.bind(item)
            }
            is UserRepoViewHolder -> {
                val item = currentList[position] as ModelSealedUserDetail.ModelUserRepo
                holder.bind(item)
            }
            is UserNoRepoViewHolder -> {
                val item = currentList[position] as ModelSealedUserDetail.ModelNoUserRepo
                holder.bind(item)
            }
            is UserEventViewHolder -> {
                val item = currentList[position] as ModelSealedUserDetail.ModelUserEvent
                holder.bind(item)
            }
            is UserNoEventViewHolder -> {
                val item = currentList[position] as ModelSealedUserDetail.ModelNoUserEvent
                holder.bind(item)
            }
            is UserInfoDividerViewHolder -> {
                val item = currentList[position] as ModelSealedUserDetail.ModelUserDataDivider
                holder.bind(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ModelSealedUserDetail.ModelUserInfo -> {
                ITEM_USER_INFO
            }
            is ModelSealedUserDetail.ModelUserRepo -> {
                ITEM_USER_REPO
            }
            is ModelSealedUserDetail.ModelNoUserRepo -> {
                ITEM_USER_NO_REPO
            }
            is ModelSealedUserDetail.ModelUserEvent -> {
                ITEM_USER_EVENT
            }
            is ModelSealedUserDetail.ModelNoUserEvent -> {
                ITEM_USER_NO_EVENT
            }
            is ModelSealedUserDetail.ModelUserDataDivider -> {
                ITEM_USER_INFO_DIVIDER
            }
        }
    }
}