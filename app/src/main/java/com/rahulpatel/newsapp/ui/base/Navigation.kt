package com.rahulpatel.newsapp.ui.base

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rahulpatel.newsapp.ui.home.HomeScreenRoute
import com.rahulpatel.newsapp.ui.topheadline.TopHeadLineRoute
import androidx.core.net.toUri
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.rahulpatel.newsapp.ui.news.NewsListRoute
import com.rahulpatel.newsapp.ui.offline.OfflineTopHeadlineRoute
import com.rahulpatel.newsapp.ui.pagination.PaginationTopHeadlineRoute
import com.rahulpatel.newsapp.ui.sources.NewsSourcesRoute
import com.rahulpatel.newsapp.utils.AppConstant

sealed class Route(val name: String) {
    object HomeScreen : Route("homescreen")
    object TopHeadline : Route("topheadline")
    object PaginationTopHeadline : Route("paginationtopheadline")
    object OfflineTopHeadline : Route("offlinetopheadline")

    object NewsSources : Route("newssources")
    object LanguageList : Route("languagelist")
    object CountryList : Route("countrylist")
    object Search : Route("search")

    object NewsList :
        Route(name = "newslist?sourceId={sourceId}&countryId={countryId}&languageId={languageId}") {
        fun passData(
            sourceId: String = "",
            countryId: String = "",
            languageId: String = ""
        ): String {
            return "newslist?sourceId=$sourceId&countryId=$countryId&languageId=$languageId"
        }
    }

}

@Composable
fun NewsNavHost() {

    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Route.HomeScreen.name
    ) {
        composable(route = Route.HomeScreen.name) {
            HomeScreenRoute(navController)
        }

        composable(route = Route.TopHeadline.name) {
            TopHeadLineRoute(onNewsClick = {
                openCustomChromeTab(context, it)
            })
        }

        composable(route = Route.OfflineTopHeadline.name) {
            OfflineTopHeadlineRoute(onNewsClick = {
                openCustomChromeTab(context, it)
            })
        }

        composable(route = Route.PaginationTopHeadline.name) {
            PaginationTopHeadlineRoute(onNewsClick = {
                openCustomChromeTab(context, it)
            })
        }

        composable(route = Route.NewsSources.name) {
            NewsSourcesRoute(onNewsClick = {
                navController.navigate(route = Route.NewsList.passData(sourceId = it))
            })
        }

        composable(
            route = Route.NewsList.name,
            arguments = listOf(
                navArgument(AppConstant.SOURCE_ID) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(AppConstant.COUNTRY_ID) {
                    type = NavType.StringType
                    defaultValue = ""

                },
                navArgument(AppConstant.LANGUAGE_ID) {
                    type = NavType.StringType
                    defaultValue = ""

                }
            )
        ) { it ->
            val sourceId = it.arguments?.getString(AppConstant.SOURCE_ID).toString()
            val countryId = it.arguments?.getString(AppConstant.COUNTRY_ID).toString()
            val languageId = it.arguments?.getString(AppConstant.LANGUAGE_ID).toString()

            NewsListRoute(onNewsClick = {
                openCustomChromeTab(context, it)
            }, sourceId = sourceId, countryId = countryId, languageId = languageId)
        }
    }
}

fun openCustomChromeTab(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, url.toUri())
}