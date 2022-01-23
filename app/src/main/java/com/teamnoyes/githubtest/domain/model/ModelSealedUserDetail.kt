package com.teamnoyes.githubtest.domain.model

sealed class ModelSealedUserDetail{
    abstract val mKey: String

    data class ModelUserInfo(
        val key: String,
        val userName: String,
        val userBio: String,
        val userProfileUri: String
    ): ModelSealedUserDetail() {
        override val mKey = key
    }

    data class ModelUserRepo(
        val key: String,
        val repoName: String,
        val repoDescription: String,
        val repoLang: String,
        val repoStar: String
    ): ModelSealedUserDetail() {
        override val mKey = key
    }

    data class ModelNoUserRepo(
        val key: String,
        ): ModelSealedUserDetail() {
        override val mKey = key
    }

    data class ModelUserEvent(
        val key: String,
        val userName: String,
        val userProfileUri: String,
        val eventType: String,
        val repoName: String
    ): ModelSealedUserDetail() {
        override val mKey = key
    }

    data class ModelNoUserEvent(
        val key: String,
        ): ModelSealedUserDetail() {
        override val mKey = key
    }

    data class ModelUserDataDivider(
        val key: String,
        val title: String
    ): ModelSealedUserDetail() {
        override val mKey = key
    }
}
