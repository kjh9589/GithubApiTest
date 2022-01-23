package com.teamnoyes.githubtest.data.mapper

import com.teamnoyes.githubtest.data.response.user_event.GetUserEventResponseItem
import com.teamnoyes.githubtest.data.response.user_info.GetUserInfoResponse
import com.teamnoyes.githubtest.data.response.user_list.Item
import com.teamnoyes.githubtest.data.response.user_repo.GetUserRepoResponseItem
import com.teamnoyes.githubtest.domain.model.*
import java.util.*

object GithubMapper {
    fun mapperUserResponseToModelUser(userResponse: Item): ModelUser {
        return ModelUser(
            key = Date().time.toString(),
            userId = userResponse.login ?: "",
            userProfileUri = userResponse.avatarUrl ?: ""
        )
    }

    fun mapperUserInfoResponseToModelUserInfo(userInfoResponse: GetUserInfoResponse): ModelSealedUserDetail.ModelUserInfo {
        return ModelSealedUserDetail.ModelUserInfo(
            key = Date().time.toString(),
            userName = userInfoResponse.name ?: "",
            userBio = userInfoResponse.bio ?: "",
            userProfileUri = userInfoResponse.avatarUrl ?: ""
        )
    }

    fun mapperUserRepoResponseToModelUserRepo(userRepoResponse: GetUserRepoResponseItem): ModelSealedUserDetail.ModelUserRepo {
        return ModelSealedUserDetail.ModelUserRepo(
            key = Date().time.toString(),
            repoName = userRepoResponse.name ?: "",
            repoDescription = userRepoResponse.description ?: "",
            repoLang = userRepoResponse.language ?: "",
            repoStar = "${userRepoResponse.stargazersCount}",
        )
    }

    fun mapperUserEventResponseToModelUserEvent(userEventResponseItem: GetUserEventResponseItem): ModelSealedUserDetail.ModelUserEvent {
        return ModelSealedUserDetail.ModelUserEvent(
            key = Date().time.toString(),
            userName = userEventResponseItem.actor?.login ?: "",
            userProfileUri = userEventResponseItem.actor?.avatarUrl ?: "",
            eventType = userEventResponseItem.type ?: "",
            repoName = userEventResponseItem.repo?.name ?: ""
        )
    }
}