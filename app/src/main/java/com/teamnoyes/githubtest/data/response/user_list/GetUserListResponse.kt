package com.teamnoyes.githubtest.data.response.user_list


import com.google.gson.annotations.SerializedName

data class GetUserListResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("total_count")
    val totalCount: Int
)