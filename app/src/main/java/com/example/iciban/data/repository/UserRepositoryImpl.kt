package com.example.iciban.data.repository

import com.example.iciban.data.datasource.api.service.ApiService
import com.example.iciban.data.datasource.preference.UserDataStore

class UserRepositoryImpl (
    private val apiService: ApiService,
    private val userDataStore: UserDataStore,
    private val appPreferences: UserDataStore
)
{

}