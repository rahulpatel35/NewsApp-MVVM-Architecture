package com.rahulpatel.newsapp.ui.topheadline

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahulpatel.newsapp.NewsApplication
import com.rahulpatel.newsapp.data.model.topheadlines.Article
import com.rahulpatel.newsapp.databinding.ActivityTopHeadlineBinding
import com.rahulpatel.newsapp.di.component.DaggerActivityComponent
import com.rahulpatel.newsapp.di.module.ActivityModule
import com.rahulpatel.newsapp.ui.base.UiState
import kotlinx.coroutines.launch
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
        binding = ActivityTopHeadlineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = topHeadlineAdapter
        }

        binding.includeLayout.tryAgainBtn.setOnClickListener {
            topHeadlineViewModel.startFetchingArticle()
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                topHeadlineViewModel.topHeadLineUiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            renderList(it.data)
                            binding.recyclerView.visibility = View.VISIBLE
                            binding.includeLayout.errorLayout.visibility = View.GONE
                        }

                        UiState.Loading -> {
                            binding.apply {
                                progressBar.visibility = View.VISIBLE
                                binding.recyclerView.visibility = View.GONE
                                binding.includeLayout.errorLayout.visibility = View.GONE
                            }
                        }

                        is UiState.Error -> {
                            binding.apply {
                                progressBar.visibility = View.GONE
                                binding.recyclerView.visibility = View.GONE
                                binding.includeLayout.errorLayout.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }

    private fun renderList(data: List<Article>) {
        topHeadlineAdapter.addArticle(data)
        topHeadlineAdapter.notifyDataSetChanged()
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