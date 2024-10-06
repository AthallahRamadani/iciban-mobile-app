package com.example.iciban.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iciban.data.repository.AppRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeViewModel(
    private val appRepository: AppRepository
    ) : ViewModel() {
    fun prefGetUsername(): String = runBlocking {
        appRepository.getUsername().first()
    }

    fun makeLogout() {
        viewModelScope.launch {
            appRepository.logout()
        }
    }
}