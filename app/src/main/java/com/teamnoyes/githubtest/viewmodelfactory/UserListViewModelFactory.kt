package com.teamnoyes.githubtest.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teamnoyes.githubtest.domain.usecase.GetUserListUseCase
import com.teamnoyes.githubtest.viewmodel.UserListViewModel

class UserListViewModelFactory(private val getUserListUseCase: GetUserListUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserListViewModel::class.java))
            return UserListViewModel(getUserListUseCase) as T
        throw IllegalArgumentException("UnKnown ViewModel Class")
    }
}