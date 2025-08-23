package com.rahulpatel.newsapp.ui.base

//UiState is a sealed interface that represents all possible UI states when fetching or displaying data.
//This UiState sealed interface is a state management pattern — it cleanly represents
//what the UI should display at any given moment:
//Loading → show progress bar
//Success(data) → show content
//Error(message) → show error screen
sealed interface UiState<out T> {

    data class Success<T>(val data: T) : UiState<T>

    data class Error(val message: String) : UiState<Nothing>

    object Loading : UiState<Nothing>

}