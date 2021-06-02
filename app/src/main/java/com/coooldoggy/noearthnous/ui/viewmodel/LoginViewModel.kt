package com.coooldoggy.noearthnous.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coooldoggy.noearthnous.model.data.UserSaveRequestDto
import com.coooldoggy.noearthnous.model.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository ): ViewModel() {
    private val TAG = LoginViewModel::class.java.simpleName

    fun sendUserToServer(user: UserSaveRequestDto){
        viewModelScope.launch {
            kotlin.runCatching {
                val result = userRepository.saveUser(user)
                Log.d(TAG, "sendUserToServer $result")
            }.onFailure {
                Log.e(TAG, it.stackTraceToString())
            }
        }
    }
}