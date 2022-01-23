package com.teamnoyes.githubtest.data.response.user_repo


import com.google.gson.annotations.SerializedName

data class Permissions(
    @SerializedName("admin")
    val admin: Boolean?,
    @SerializedName("pull")
    val pull: Boolean?,
    @SerializedName("push")
    val push: Boolean?
)