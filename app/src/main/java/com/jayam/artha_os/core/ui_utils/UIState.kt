package com.jayam.artha_os.core.ui_utils

    sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T, val isRefreshing: Boolean = false) : UiState<T>
    data class Error<T>(val message: String, val cachedData: T? = null) : UiState<T>
}