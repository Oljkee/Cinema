package com.example.lvl1final.data

import kotlinx.coroutines.flow.StateFlow

interface NetworkConnectionManager {

    val isNetworkConnectedFlow: StateFlow<Boolean>

    val isNetworkConnected: Boolean

    fun startListenNetworkState()

    fun stopListenNetworkState()
}