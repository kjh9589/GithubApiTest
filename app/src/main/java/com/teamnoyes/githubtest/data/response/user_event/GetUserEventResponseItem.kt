package com.teamnoyes.githubtest.data.response.user_event


import com.google.gson.annotations.SerializedName

data class GetUserEventResponseItem(
    @SerializedName("actor")
    val actor: Actor?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("org")
    val org: Org?,
    @SerializedName("payload")
    val payload: Payload?,
    @SerializedName("public")
    val `public`: Boolean?,
    @SerializedName("repo")
    val repo: Repo?,
    @SerializedName("type")
    val type: String?
)