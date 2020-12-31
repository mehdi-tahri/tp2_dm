package com.example.tp2.userinfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp2.network.UserInfo
import com.example.tp2.network.UserInfoRepository
import kotlinx.coroutines.launch

class UserInfoViewModel: ViewModel() {

    private val repository = UserInfoRepository()
    private val _user = MutableLiveData<UserInfo>()
    public val user: MutableLiveData<UserInfo> = _user


    fun loadInfo() {
        viewModelScope.launch {
            _user.value = repository.loadInfo()
        }
    }

    fun updateUserInfo(userInfo: UserInfo){
        viewModelScope.launch {
            repository.updateUserInfo(userInfo)?.let { userInfo ->
                _user.value  = repository.updateUserInfo(userInfo)
            }
        }
    }

}