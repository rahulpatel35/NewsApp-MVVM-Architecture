package com.rahulpatel.newsapp.data.repository

import com.rahulpatel.newsapp.data.model.Language
import com.rahulpatel.newsapp.di.ActivityScope
import com.rahulpatel.newsapp.utils.AppConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@ActivityScope
class LanguageListRepository @Inject constructor() {

    fun getLanguage(): Flow<List<Language>> {
        return flow { emit(AppConstant.LANGUAGES) }
    }
}