package com.example.iciban.data


sealed class ResultState<out T> {
    data class Succes<T>(val data:T): ResultState<T>()
    data class Error(val error: Throwable) : ResultState<Nothing>()
    data object Loading : ResultState<Nothing>()
}