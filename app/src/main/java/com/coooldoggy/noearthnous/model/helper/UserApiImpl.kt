package com.coooldoggy.noearthnous.model.helper

import com.coooldoggy.noearthnous.model.data.UserSaveRequestDto
import com.coooldoggy.noearthnous.model.service.UserApiService
import javax.inject.Inject

class UserApiImpl @Inject constructor(private val userApiService: UserApiService) : UserApiHelper {
    override suspend fun saveUser(userData: UserSaveRequestDto) = userApiService.saveUser(userData)
}