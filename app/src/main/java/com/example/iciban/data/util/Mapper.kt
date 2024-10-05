package com.example.iciban.data.util

import com.example.iciban.data.datasource.api.response.RegisterDataResponse
import com.example.iciban.data.model.User

fun RegisterDataResponse.toUser(): User =
    User(username = userName, accessToken = accessToken, refreshToken = refreshToken)