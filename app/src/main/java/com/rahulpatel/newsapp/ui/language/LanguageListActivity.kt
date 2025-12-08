package com.rahulpatel.newsapp.ui.language

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahulpatel.newsapp.data.model.Language
import com.rahulpatel.newsapp.databinding.ActivityLanguageListBinding
import com.rahulpatel.newsapp.ui.BaseActivity
import com.rahulpatel.newsapp.ui.base.UiState
import com.rahulpatel.newsapp.ui.news.NewsListActivity
import com.rahulpatel.newsapp.utils.AppConstant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LanguageListActivity : BaseActivity() {

    //@Inject
    lateinit var languageListViewModel: LanguageListViewModel

    @Inject
    lateinit var languageListAdapter: LanguageListAdapter


    private lateinit var binding: ActivityLanguageListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        //injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageListBinding.inflate(layoutInflater)
        setupViewModel()
        setContentView(binding.root)
        setUpUi()
        setUpObserver()
    }

    private fun setupViewModel() {
        languageListViewModel = ViewModelProvider(this)[LanguageListViewModel::class.java]
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                languageListViewModel.languageUiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            renderList(it.data)
                            binding.recyclerView.visibility = View.VISIBLE
                            binding.includeLayout.errorLayout.visibility = View.GONE
                        }

                        is UiState.Loading -> {
                            binding.apply {
                                progressBar.visibility = View.VISIBLE
                                recyclerView.visibility = View.GONE
                                binding.includeLayout.errorLayout.visibility = View.GONE
                            }
                        }

                        is UiState.Error -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.includeLayout.errorLayout.visibility = View.VISIBLE
                            Toast.makeText(this@LanguageListActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun setUpUi() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = languageListAdapter
        }

        binding.includeLayout.tryAgainBtn.setOnClickListener {
            languageListViewModel.fetchLanguages()
        }

        languageListAdapter.itemClickLister = { _, languageList ->
            val language = languageList as Language

            languageListAdapter.selectedLanguageState =
                languageListAdapter.selectedLanguageState.copy(
                    selectedLanguage = if (languageListAdapter.selectedLanguageState.selectedLanguage.contains(
                            language
                        )
                    ) {
                        languageListAdapter.selectedLanguageState.selectedLanguage - language
                    } else {
                        if (languageListAdapter.selectedLanguageState.selectedLanguage.size < 2) {
                            languageListAdapter.selectedLanguageState.selectedLanguage + language
                        } else {
                            languageListAdapter.selectedLanguageState.selectedLanguage
                        }
                    }
                )

            languageListAdapter.notifyDataSetChanged()

            val listLanguage: List<Language> =
                languageListAdapter.selectedLanguageState.selectedLanguage
            if (listLanguage.size == 2) {
                val languageString = listLanguage.joinToString(",") { it.id.toString() }
                onLanguageClick(languageString)

            }

        }
    }

    private fun onLanguageClick(languageString: String) {
        startActivity(
            NewsListActivity.getStartIntent(
                context = this@LanguageListActivity,
                langList = languageString,
                newsType = AppConstant.NEWS_BY_LANGUAGE
            )
        )
    }

    private fun renderList(languageList: List<Language>) {
        languageListAdapter.addLanguage(languageList)
        languageListAdapter.notifyDataSetChanged()
    }

   /* private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }*/

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LanguageListActivity::class.java)
        }
    }


}