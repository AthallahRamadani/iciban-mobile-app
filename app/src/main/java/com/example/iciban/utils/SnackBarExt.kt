package com.example.iciban.utils

import android.view.View
import com.example.iciban.data.datasource.api.response.ErrorResponse
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import retrofit2.HttpException

fun View.showSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun Throwable.getErrorMessage(): String {
    var message = this.message
    if (this is HttpException) {
        val errorResponse =
            Gson().fromJson(
                this.response()?.errorBody()?.string(),
                ErrorResponse::class.java
            ) ?: ErrorResponse()
        errorResponse.message?.let { message = it }
    }
    return message.toString()
}