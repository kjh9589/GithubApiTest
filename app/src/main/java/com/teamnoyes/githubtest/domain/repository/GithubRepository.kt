package com.teamnoyes.githubtest.domain.repository

import com.teamnoyes.githubtest.domain.model.*

interface GithubRepository {
    suspend fun getUserList(keyword: String, page: Int): List<ModelUser>

    suspend fun getUserInfo(username: String): ModelSealedUserDetail.ModelUserInfo

    suspend fun getUserRepo(username: String): List<ModelSealedUserDetail.ModelUserRepo>

    suspend fun getUserEvent(username: String, page: Int): List<ModelSealedUserDetail.ModelUserEvent>
}