package com.muratcangzm.dummysahibinden.common.listener

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.muratcangzm.dummysahibinden.utils.NetworkConnection

class NetworkListener(private val onNetworkChange: (Boolean) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        context?.let {

            val isConnected: Boolean = NetworkConnection.isNetworkAvailable(context)

            onNetworkChange(isConnected)
        }
    }
}
