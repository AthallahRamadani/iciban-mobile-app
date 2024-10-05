package com.example.iciban.fragment.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iciban.data.ResultState
import com.example.iciban.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _registerState = MutableStateFlow<ResultState<Boolean>?>(null)
    val registerState: StateFlow<ResultState<Boolean>?> = _registerState

    fun makeRegister(username: String, email: String, password: String) {
        viewModelScope.launch {
            userRepository.register(username, email, password).collect { resultState ->
                _registerState.value = resultState
            }
        }
    }
}