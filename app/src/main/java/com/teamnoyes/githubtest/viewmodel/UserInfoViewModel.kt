package com.teamnoyes.githubtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamnoyes.githubtest.domain.model.ModelSealedUserDetail
import com.teamnoyes.githubtest.domain.usecase.GetUserEventUseCase
import com.teamnoyes.githubtest.domain.usecase.GetUserInfoUseCase
import com.teamnoyes.githubtest.domain.usecase.GetUserRepoUseCase
import com.teamnoyes.githubtest.utils.ConstValues
import com.teamnoyes.githubtest.utils.TNLog
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class UserInfoViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getUserRepoUseCase: GetUserRepoUseCase,
    private val getUserEventUseCase: GetUserEventUseCase
) : ViewModel() {
    private val itemList = ArrayList<ModelSealedUserDetail>()
    private var currentPage = 1
    private var infoUserName = ""
    private var userInfoHeadText = ""
    private var userRepoHeadText = ""
    private var userEventHeadText = ""

    private val _resetState = MutableLiveData<Boolean>()
    val resetState: LiveData<Boolean>
        get() = _resetState

    private val _networkState = MutableLiveData<String>("")
    val networkState: LiveData<String>
        get() = _networkState

    private val _userDataList = MutableLiveData<List<ModelSealedUserDetail>>()
    val userDataList: LiveData<List<ModelSealedUserDetail>>
        get() = _userDataList

    private val _emptyState = MutableLiveData<String>("")
    val emptyState: LiveData<String>
        get() = _emptyState

    fun getUserData(userName: String, userInfoHead: String, userRepoHead: String, userEventHead: String) {
        viewModelScope.launch {
            infoUserName = userName
            userInfoHeadText = userInfoHead
            userRepoHeadText = userRepoHead
            userEventHeadText = userEventHead
            _networkState.value = ConstValues.NETWORK_LOADING

            try {
                addItems(ModelSealedUserDetail.ModelUserDataDivider(Date().time.toString(), userInfoHead))
                val tempInfo = getUserInfoUseCase.invoke(userName)
                addItems(tempInfo)
                addItems(ModelSealedUserDetail.ModelUserDataDivider(Date().time.toString(), userRepoHead))
                val tempRepo = getUserRepoUseCase.invoke(userName)
                if (tempRepo.isEmpty()) {
                    addItems(ModelSealedUserDetail.ModelNoUserRepo(Date().time.toString()))
                } else {
                    addAllItems(tempRepo)
                }
                addItems(ModelSealedUserDetail.ModelUserDataDivider(Date().time.toString(), userEventHead))
                val tempEvent = getUserEventUseCase.invoke(userName, 1)
                if (tempEvent.isEmpty()) {
                    addItems(ModelSealedUserDetail.ModelNoUserEvent(Date().time.toString()))
                } else {
                    addAllItems(tempEvent)
                    currentPage += 1
                }
                _userDataList.value = getSubmitList()
                _networkState.value = ""
            } catch (e: Exception) {
                e.printStackTrace()
                _networkState.value = ConstValues.NETWORK_ERROR
            }

        }
    }

    fun getNextEventData() {
        viewModelScope.launch {
            _networkState.value = ConstValues.NETWORK_LOADING
            try {
                val temp = getUserEventUseCase.invoke(infoUserName, currentPage)

                if (temp.isEmpty()) {
                    _emptyState.value = ConstValues.RESULT_EMPTY
                }
                addAllItems(temp)
                _userDataList.value = getSubmitList()
                currentPage += 1

                _networkState.value = ""
            } catch (e: Exception) {
                TNLog.d("${e.message}")
                e.printStackTrace()
                if (e.message.toString().trim() == ConstValues.NETWORK_ERROR_422) {
                    _emptyState.value = ConstValues.RESULT_EMPTY
                    _networkState.value = ""
                } else {
                    _networkState.value = ConstValues.NETWORK_ERROR
                }
            }

        }
    }

    fun getResetUserData() {
        viewModelScope.launch {
            _networkState.value = ConstValues.RESET_LOADING
            _resetState.value = false

            try {
                clearList()
                addItems(ModelSealedUserDetail.ModelUserDataDivider(Date().time.toString(), userInfoHeadText))
                val tempInfo = getUserInfoUseCase.invoke(infoUserName)
                addItems(tempInfo)
                addItems(ModelSealedUserDetail.ModelUserDataDivider(Date().time.toString(), userRepoHeadText))
                val tempRepo = getUserRepoUseCase.invoke(infoUserName)
                if (tempRepo.isEmpty()) {
                    addItems(ModelSealedUserDetail.ModelNoUserRepo(Date().time.toString()))
                } else {
                    addAllItems(tempRepo)
                }
                addItems(ModelSealedUserDetail.ModelUserDataDivider(Date().time.toString(), userEventHeadText))
                val tempEvent = getUserEventUseCase.invoke(infoUserName, 1)
                if (tempEvent.isEmpty()) {
                    addItems(ModelSealedUserDetail.ModelNoUserEvent(Date().time.toString()))
                } else {
                    addAllItems(tempEvent)
                    currentPage += 1
                }
                _userDataList.value = getSubmitList()

                _networkState.value = ""
            } catch (e: Exception) {
                e.printStackTrace()
                _networkState.value = ConstValues.NETWORK_ERROR
            } finally {
                _resetState.value = true
            }

        }
    }

    fun clearData() {
        clearList()
    }

    private fun addItems(item: ModelSealedUserDetail) {
        itemList.add(item)
    }

    private fun addAllItems(list: List<ModelSealedUserDetail>) {
        itemList.addAll(list)
    }

    private fun clearList() {
        itemList.clear()
    }

    private fun getSubmitList() = itemList.map {
        when (it) {
            is ModelSealedUserDetail.ModelUserInfo -> {
                val newItem = it.copy()
                newItem
            }
            is ModelSealedUserDetail.ModelUserRepo -> {
                val newItem = it.copy()
                newItem
            }
            is ModelSealedUserDetail.ModelNoUserRepo -> {
                val newItem = it.copy()
                newItem
            }
            is ModelSealedUserDetail.ModelUserEvent -> {
                val newItem = it.copy()
                newItem
            }
            is ModelSealedUserDetail.ModelNoUserEvent -> {
                val newItem = it.copy()
                newItem
            }
            is ModelSealedUserDetail.ModelUserDataDivider -> {
                val newItem = it.copy()
                newItem
            }
        }
    }

    fun resetCurrentPage() {
        currentPage = 1
        _emptyState.value = ""
    }
}