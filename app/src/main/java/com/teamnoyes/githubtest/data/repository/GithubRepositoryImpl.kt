package com.teamnoyes.githubtest.data.repository

import com.teamnoyes.githubtest.data.datasource.interfaces.GithubDataSource
import com.teamnoyes.githubtest.data.mapper.GithubMapper
import com.teamnoyes.githubtest.domain.model.*
import com.teamnoyes.githubtest.domain.repository.GithubRepository

class GithubRepositoryImpl(private val datasource: GithubDataSource): GithubRepository {
    override suspend fun getUserList(keyword: String, page: Int): List<ModelUser> {
        return datasource.getUserList(keyword, page).items.map {
            GithubMapper.mapperUserResponseToModelUser(it)
        }
    }

    override suspend fun getUserInfo(username: String): ModelSealedUserDetail.ModelUserInfo {
        return GithubMapper.mapperUserInfoResponseToModelUserInfo(datasource.getUserInfo(username))
    }

    override suspend fun getUserRepo(username: String): List<ModelSealedUserDetail.ModelUserRepo> {
        return datasource.getUserRepo(username).map {
            GithubMapper.mapperUserRepoResponseToModelUserRepo(it)
        }
    }

    override suspend fun getUserEvent(username: String, page: Int): List<ModelSealedUserDetail.ModelUserEvent> {
        return datasource.getUserEvent(username, page).map {
            GithubMapper.mapperUserEventResponseToModelUserEvent(it)
        }
    }
}