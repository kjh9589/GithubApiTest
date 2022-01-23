package com.teamnoyes.githubtest.domain.usecase

import com.teamnoyes.githubtest.domain.model.ModelSealedUserDetail
import com.teamnoyes.githubtest.domain.repository.GithubRepository

class GetUserRepoUseCase(private val repository: GithubRepository) {
    suspend fun invoke(username: String): List<ModelSealedUserDetail.ModelUserRepo> {
        return repository.getUserRepo(username)
    }
}