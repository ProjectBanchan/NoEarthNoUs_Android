package com.coooldoggy.noearthnous.model.helper

import com.coooldoggy.noearthnous.model.data.UserSaveRequestDto
import retrofit2.Response

interface UserApiHelper {
    suspend fun saveUser(userData: UserSaveRequestDto): Response<Int>
}