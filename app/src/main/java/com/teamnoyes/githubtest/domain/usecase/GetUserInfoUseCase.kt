package com.teamnoyes.githubtest.domain.usecase

import com.teamnoyes.githubtest.domain.model.ModelSealedUserDetail
import com.teamnoyes.githubtest.domain.repository.GithubRepository

class GetUserInfoUseCase(private val repository: GithubRepository) {
    suspend fun invoke(username: String): ModelSealedUserDetail.ModelUserInfo {
        return repository.getUserInfo(username)
    }
}