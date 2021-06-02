package com.coooldoggy.noearthnous.model.service

import com.coooldoggy.noearthnous.SUB_URL_USER_INSERT
import com.coooldoggy.noearthnous.model.data.UserSaveRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {
    @POST(SUB_URL_USER_INSERT)
    suspend fun saveUser(@Body userData: UserSaveRequestDto): Response<Int>
}