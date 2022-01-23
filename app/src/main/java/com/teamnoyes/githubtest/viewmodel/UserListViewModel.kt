package com.teamnoyes.githubtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamnoyes.githubtest.domain.model.ModelUser
import com.teamnoyes.githubtest.domain.usecase.GetUserListUseCase
import com.teamnoyes.githubtest.utils.ConstValues
import com.teamnoyes.githubtest.utils.ConstValues.NETWORK_ERROR
import com.teamnoyes.githubtest.utils.ConstValues.NETWORK_LOADING
import com.teamnoyes.githubtest.utils.ConstValues.RESET_LOADING
import kotlinx.coroutines.launch

class UserListViewModel(private val getUserListUseCase: GetUserListUseCase) : ViewModel() {
    private val itemList = ArrayList<ModelUser>()

    private val _userList = MutableLiveData<List<ModelUser>>()
    val userList: LiveData<List<ModelUser>>
        get() = _userList

    private val _resetState = MutableLiveData<Boolean>()
    val resetState: LiveData<Boolean>
        get() = _resetState

    private val _networkState = MutableLiveData<String>("")
    val networkState: LiveData<String>
        get() = _networkState

    private val _emptyState = MutableLiveData<String>("")
    val emptyState: LiveData<String>
        get() = _emptyState

    private var currentPage = 1
    private var userKeyword = ""

    fun getUserList(keyword: String) {
        viewModelScope.launch {
            _networkState.value = NETWORK_LOADING
            try {
                val temp = getUserListUseCase.invoke(keyword, 1)
                userKeyword = keyword

                addAllItems(temp)
                _userList.value = getSubmitList()
                currentPage += 1

                _networkState.value = ""
            } catch (e: Exception) {
                e.printStackTrace()
                _networkState.value = NETWORK_ERROR
            }

        }
    }

    fun getNextUserList() {
        viewModelScope.launch {
            _networkState.value = NETWORK_LOADING
            try {
                val temp = getUserListUseCase.invoke(userKeyword, currentPage)

                if (temp.isEmpty()) {
                    _emptyState.value = ConstValues.RESULT_EMPTY
                }
                addAllItems(temp)
                _userList.value = getSubmitList()
                currentPage += 1

                _networkState.value = ""

            } catch (e: Exception) {
                e.printStackTrace()
                if (e.message.toString().trim() == ConstValues.NETWORK_ERROR_422) {
                    _emptyState.value = ConstValues.RESULT_EMPTY
                    _networkState.value = ""
                } else {
                    _networkState.value = NETWORK_ERROR
                }
            }
        }
    }

    fun getResetUserList() {
        viewModelScope.launch {
            _networkState.value = RESET_LOADING
            _resetState.value = false
            try {
                val temp = getUserListUseCase.invoke(userKeyword, 1)
                clearList()

                addAllItems(temp)
                _userList.value = getSubmitList()
                currentPage += 1

                _networkState.value = ""
            } catch (e: Exception) {
                e.printStackTrace()
                _networkState.value = NETWORK_ERROR
            } finally {
                _resetState.value = true
            }
        }
    }

    fun clearData() {
        clearList()
    }

    private fun addAllItems(list: List<ModelUser>) {
        itemList.addAll(list)
    }

    private fun clearList() {
        itemList.clear()
    }

    private fun getSubmitList() = itemList.map {
        val newItem = it.copy()
        newItem
    }

    fun resetCurrentPage() {
        currentPage = 1
        _emptyState.value = ""
    }
}