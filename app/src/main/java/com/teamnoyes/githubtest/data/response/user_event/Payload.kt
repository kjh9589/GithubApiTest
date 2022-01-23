package com.teamnoyes.githubtest.data.response.user_event


import com.google.gson.annotations.SerializedName

data class Payload(
    @SerializedName("forkee")
    val forkee: Forkee?
)