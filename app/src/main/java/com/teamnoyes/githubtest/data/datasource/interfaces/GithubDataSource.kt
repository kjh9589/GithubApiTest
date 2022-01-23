package com.teamnoyes.githubtest.data.datasource.interfaces

import com.teamnoyes.githubtest.data.response.user_event.GetUserEventResponse
import com.teamnoyes.githubtest.data.response.user_info.GetUserInfoResponse
import com.teamnoyes.githubtest.data.response.user_list.GetUserListResponse
import com.teamnoyes.githubtest.data.response.user_repo.GetUserRepoResponse

interface GithubDataSource {
    suspend fun getUserList(keyword: String, page: Int): GetUserListResponse

    suspend fun getUserInfo(username: String): GetUserInfoResponse

    suspend fun getUserRepo(username: String): GetUserRepoResponse

    suspend fun getUserEvent(username: String, page: Int): GetUserEventResponse
}