package com.rahulpatel.newsapp.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkHelperImpl(private val context: Context) : NetworkHelper {

    override fun isNetworkConnected(): Boolean {
        val connMgr = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeInfo: NetworkCapabilities? = null
        activeInfo = connMgr.getNetworkCapabilities(connMgr.activeNetwork)
        if (activeInfo != null) {
            if (activeInfo.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (activeInfo.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (activeInfo.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
        return false
    }

    /*override fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork?.isConnected ?: false
    }*/
}