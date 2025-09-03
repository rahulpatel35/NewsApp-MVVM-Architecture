package com.rahulpatel.newsapp.ui.topheadline

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rahulpatel.newsapp.NewsApplication
import com.rahulpatel.newsapp.databinding.ActivityTopHeadlineBinding
import com.rahulpatel.newsapp.di.component.DaggerActivityComponent
import com.rahulpatel.newsapp.di.module.ActivityModule
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
        setContentView(binding.root)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {

    }

    private fun setupObserver() {

    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, TopHeadlineActivity::class.java)
        }
    }

}