package com.example.lvl1final.data.network

import kotlinx.coroutines.flow.StateFlow

interface NetworkConnectionManager {

    val isNetworkConnectedFlow: StateFlow<Boolean>

    val isNetworkConnected: Boolean

    fun startListenNetworkState()

    fun stopListenNetworkState()
}