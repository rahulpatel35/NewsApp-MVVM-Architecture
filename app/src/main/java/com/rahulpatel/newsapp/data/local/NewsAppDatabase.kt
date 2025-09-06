package com.rahulpatel.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rahulpatel.newsapp.data.local.dao.SourceDao
import com.rahulpatel.newsapp.data.local.dao.TopHeadlinesDao
import com.rahulpatel.newsapp.data.local.entity.Article
import com.rahulpatel.newsapp.data.local.entity.NewsSources


@Database(entities = [Article::class, NewsSources::class], version = 1, exportSchema = false)
abstract class NewsAppDatabase : RoomDatabase() {
    abstract fun topHeadlinesDao(): TopHeadlinesDao
    abstract fun newsSourceDao(): SourceDao
}