package com.example.tp2.network

import com.example.tp2.Task

class UserInfoRepository {
    private val userWebService = Api.INSTANCE.userService

    suspend fun updateUserInfo(user: UserInfo): UserInfo? {
        val response = userWebService.update(user)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun loadInfo(): UserInfo? {
        val response = userWebService.getInfo()
        return if (response.isSuccessful) response.body() else null
    }
}