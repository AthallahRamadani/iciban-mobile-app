package com.example.iciban.fragment.prelogin.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iciban.data.ResultState
import com.example.iciban.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _loginState = MutableStateFlow<ResultState<Boolean>?>(null)
    val loginState: StateFlow<ResultState<Boolean>?> = _loginState
    
    fun makeLogin(username : String, password :String ){
        viewModelScope.launch { 
            userRepository.login(username,password).collect{
                resultState -> _loginState.value = resultState
            }
        }
    }
}