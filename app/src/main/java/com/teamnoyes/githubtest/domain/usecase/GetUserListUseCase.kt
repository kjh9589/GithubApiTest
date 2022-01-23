package com.teamnoyes.githubtest.domain.usecase

import com.teamnoyes.githubtest.domain.model.ModelUser
import com.teamnoyes.githubtest.domain.repository.GithubRepository

class GetUserListUseCase(private val repository: GithubRepository) {
    suspend fun invoke(keyword: String, page: Int): List<ModelUser> {
        return repository.getUserList(keyword, page)
    }
}