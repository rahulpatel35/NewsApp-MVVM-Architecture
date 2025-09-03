package com.rahulpatel.newsapp.ui.topheadlines

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rahulpatel.newsapp.databinding.ActivityTopHeadlineBinding
import javax.inject.Inject

class TopHeadlineActivity : AppCompatActivity() {


    @Inject
    lateinit var topHeadlineViewModel: TopHeadlineViewModel

    @Inject
    lateinit var topHeadlineAdapter: TopHeadlineAdapter

    private lateinit var binding: ActivityTopHeadlineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)

    }

    private fun injectDependencies() {

    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, TopHeadlineActivity::class.java)
        }
    }

}