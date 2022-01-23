package com.teamnoyes.githubtest.data.api

import com.teamnoyes.githubtest.data.response.user_event.GetUserEventResponse
import com.teamnoyes.githubtest.data.response.user_info.GetUserInfoResponse
import com.teamnoyes.githubtest.data.response.user_list.GetUserListResponse
import com.teamnoyes.githubtest.data.response.user_repo.GetUserRepoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET("/search/users")
    suspend fun getUserList(@Query("q") q: String, @Query("page") page: Int, @Query("per_page") perPage: Int = 50): GetUserListResponse

    @GET("/users/{username}")
    suspend fun getUserInfo(@Path("username") username: String): GetUserInfoResponse

    @GET("/users/{username}/repos")
    suspend fun getUserRepoList(@Path("username") username: String, @Query("per_page") perPage: Int = 3): GetUserRepoResponse

    @GET("/users/{username}/events")
    suspend fun getUserEvent(@Path("username") username: String, @Query("page") page: Int, @Query("per_page") perPage: Int = 50): GetUserEventResponse
}