package com.rahulpatel.newsapp.utils

//defines a Kotlin object — which is basically a singleton.
// AppConstant is a singleton object for storing constants that are reused across your app,
// avoiding hardcoding strings/numbers in multiple places.

object AppConstant {
    const val API_KEY = "8094a578ebb841239f30392d9e0fb066"
    const val COUNTRY = "us"
    const val BASE_URL = "https://newsapi.org/v2/"

    const val DATABASE_NAME = "news-database"

    const val INITIAL_PAGE = 1
    const val PAGE_SIZE = 20
    const val DEBOUNCE_TIMEOUT = 300L
    const val MIN_SEARCH_CHAR = 3

    const val NEWS_BY_SOURCES = "sources"
    const val NEWS_BY_COUNTRY = "country"
    const val NEWS_BY_LANGUAGE = "language"

}