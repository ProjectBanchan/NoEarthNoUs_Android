package com.coooldoggy.noearthnous.model.repository

import com.coooldoggy.noearthnous.model.data.UserSaveRequestDto
import com.coooldoggy.noearthnous.model.helper.UserApiHelper
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApiHelper: UserApiHelper) {
    suspend fun saveUser(userData: UserSaveRequestDto) = userApiHelper.saveUser(userData)
}