package com.example.brainvira_task.Util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {

    private val TAG = NetworkUtils::class.java.simpleName

    @JvmStatic
    fun isNetworkAvailable(activity: Activity?): Boolean {
        if (activity != null) {
            val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnected
        }
        return false
    }

}