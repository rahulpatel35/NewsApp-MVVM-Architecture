package com.rahulpatel.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rahulpatel.newsapp.databinding.ActivityMainBinding
import com.rahulpatel.newsapp.ui.country.CountryListActivity
import com.rahulpatel.newsapp.ui.offline.OfflineTopHeadlineActivity
import com.rahulpatel.newsapp.ui.pagination.PaginationTopHeadlineActivity
import com.rahulpatel.newsapp.ui.sources.NewsSourcesActivity
import com.rahulpatel.newsapp.ui.topheadline.TopHeadlineActivity

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        applyEdgeToEdge(binding.root)
        /*enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        setContentView(binding.root)
    }

    fun startTopHeadlinesActivity(view: View) {
        startActivity(Intent(TopHeadlineActivity.getStartIntent(this@MainActivity)))
    }

    fun startOfflineTopHeadlinesActivity(view: View) {
        startActivity(Intent(OfflineTopHeadlineActivity.getStartIntent(this@MainActivity)))
    }

    fun startTopHeadlinesPaginationActivity(view: View) {
        startActivity(Intent(PaginationTopHeadlineActivity.getStartIntent(this@MainActivity)))
    }

    fun startNewsSourcesActivity(view: View) {
        startActivity(Intent(NewsSourcesActivity.getStartIntent(this@MainActivity)))
    }

    fun startCountryListActivity(view: View) {
        startActivity(Intent(CountryListActivity.getStartIntent(this@MainActivity)))
    }
}