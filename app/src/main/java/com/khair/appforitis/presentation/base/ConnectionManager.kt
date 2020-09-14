package com.khair.appforitis.presentation.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object ConnectionManager {

    fun hasConnection(context: Context): Boolean{
        val cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            val network = cm.activeNetwork
            val capabilities: NetworkCapabilities = cm.getNetworkCapabilities(network) ?: return false
            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        } else {
            val wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            val cellularInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            if(wifiInfo == null && cellularInfo == null){
                return false
            }
            return wifiInfo.isConnected || cellularInfo.isConnected
        }
        return false
    }
}