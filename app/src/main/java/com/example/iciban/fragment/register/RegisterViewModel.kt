package com.example.iciban.fragment.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iciban.data.ResultState
import com.example.iciban.data.repository.AppRepository
import com.example.iciban.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterViewModel(
    private val userRepository: UserRepository,
    private val appRepository: AppRepository
) : ViewModel() {
    private val _registerState = MutableStateFlow<ResultState<Boolean>?>(null)
    val registerState: StateFlow<ResultState<Boolean>?> = _registerState

    fun makeRegister(username: String, email: String, password: String) {
        viewModelScope.launch {
            userRepository.register(username, email, password).collect { resultState ->
                _registerState.value = resultState
            }
        }
    }

    fun prefSetUsername(value: String) {
        runBlocking {
            appRepository.setUsername(value)
        }
    }
}