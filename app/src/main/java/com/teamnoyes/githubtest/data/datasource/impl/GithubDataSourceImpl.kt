package com.teamnoyes.githubtest.data.datasource.impl

import com.teamnoyes.githubtest.data.api.GithubService
import com.teamnoyes.githubtest.data.datasource.interfaces.GithubDataSource
import com.teamnoyes.githubtest.data.response.user_event.GetUserEventResponse
import com.teamnoyes.githubtest.data.response.user_info.GetUserInfoResponse
import com.teamnoyes.githubtest.data.response.user_list.GetUserListResponse
import com.teamnoyes.githubtest.data.response.user_repo.GetUserRepoResponse

class GithubDataSourceImpl(private val api: GithubService): GithubDataSource {
    override suspend fun getUserList(keyword: String, page: Int): GetUserListResponse {
        return api.getUserList(keyword, page)
    }

    override suspend fun getUserInfo(username: String): GetUserInfoResponse {
        return api.getUserInfo(username)
    }

    override suspend fun getUserRepo(username: String): GetUserRepoResponse {
        return api.getUserRepoList(username)
    }

    override suspend fun getUserEvent(username: String, page: Int): GetUserEventResponse {
        return api.getUserEvent(username, page)
    }
}