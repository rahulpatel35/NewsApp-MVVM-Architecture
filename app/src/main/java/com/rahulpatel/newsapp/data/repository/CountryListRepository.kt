package com.rahulpatel.newsapp.data.repository

import com.rahulpatel.newsapp.data.model.Country
import com.rahulpatel.newsapp.di.ActivityScope
import com.rahulpatel.newsapp.utils.AppConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityScope
class CountryListRepository @Inject constructor() {

    fun getCountry(): Flow<List<Country>> {
        return flow { emit(AppConstant.COUNTRIES) }
    }
}