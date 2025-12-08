package com.rahulpatel.newsapp.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.rahulpatel.newsapp.ui.base.NewsNavHost
import com.rahulpatel.newsapp.ui.theme.NewsAppTheme
import com.rahulpatel.newsapp.ui.theme.gray40
import com.rahulpatel.newsapp.utils.AppConstant

class MainActivity : BaseActivity() {
    //private lateinit var binding: ActivityMainBinding

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = Color.White
                            ), title = {
                                Text(text = AppConstant.APP_NAME)
                            })
                    }) { padding ->
                    Column(
                        modifier = Modifier
                            .padding(padding)
                            .background(gray40),
                    ) {
                        NewsNavHost()
                    }
                }
            }
        }
        /*binding = ActivityMainBinding.inflate(layoutInflater)
        applyEdgeToEdge(binding.root)
        setContentView(binding.root)*/
    }

}

/*fun startTopHeadlinesActivity(view: View) {
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

fun startLanguageListActivity(view: View) {
    startActivity(Intent(LanguageListActivity.getStartIntent(this@MainActivity)))
}

fun startSearchActivity(view: View) {
    startActivity(Intent(SearchActivity.getStartIntent(this@MainActivity)))
}*/
