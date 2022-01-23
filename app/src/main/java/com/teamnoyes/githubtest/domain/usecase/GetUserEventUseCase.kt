package com.teamnoyes.githubtest.domain.usecase

import com.teamnoyes.githubtest.domain.model.ModelSealedUserDetail
import com.teamnoyes.githubtest.domain.repository.GithubRepository

class GetUserEventUseCase(private val repository: GithubRepository) {
    suspend fun invoke(username: String, page: Int): List<ModelSealedUserDetail.ModelUserEvent>{
        return repository.getUserEvent(username, page)
    }
}