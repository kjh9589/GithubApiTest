package com.teamnoyes.githubtest.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teamnoyes.githubtest.domain.usecase.GetUserEventUseCase
import com.teamnoyes.githubtest.domain.usecase.GetUserInfoUseCase
import com.teamnoyes.githubtest.domain.usecase.GetUserRepoUseCase
import com.teamnoyes.githubtest.viewmodel.UserInfoViewModel

class UserInfoViewModelFactory(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getUserRepoUseCase: GetUserRepoUseCase,
    private val getUserEventUseCase: GetUserEventUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserInfoViewModel::class.java))
            return UserInfoViewModel(getUserInfoUseCase, getUserRepoUseCase, getUserEventUseCase) as T
        throw IllegalArgumentException("UnKnown ViewModel Class")
    }
}