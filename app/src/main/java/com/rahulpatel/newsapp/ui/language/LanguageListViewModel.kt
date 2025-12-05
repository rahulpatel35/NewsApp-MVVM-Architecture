package com.rahulpatel.newsapp.ui.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulpatel.newsapp.data.model.Language
import com.rahulpatel.newsapp.data.repository.LanguageListRepository
import com.rahulpatel.newsapp.ui.base.UiState
import com.rahulpatel.newsapp.utils.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class LanguageListViewModel @Inject constructor(
    private val languageListRepository: LanguageListRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _languageUiState = MutableStateFlow<UiState<List<Language>>>(UiState.Loading)
    val languageUiState: StateFlow<UiState<List<Language>>> = _languageUiState

    init {
        fetchLanguages()
    }

    fun fetchLanguages() {

        viewModelScope.launch(dispatcherProvider.main) {
            languageListRepository.getLanguage()
                .flowOn(dispatcherProvider.default)
                .catch { e ->
                    _languageUiState.value = UiState.Error(e.toString())
                }.collect {
                    _languageUiState.value = UiState
                        .Success(it)
                }
        }
    }
}