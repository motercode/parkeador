package com.example.parkeador.data.datasource

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import com.example.parkeador.domain.model.WifiData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class WifiScanner(private val context: Context) {

    private val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    private val _wifiDataFlow = MutableStateFlow<List<WifiData>>(emptyList())
    val wifiDataFlow: Flow<List<WifiData>> = _wifiDataFlow

    private val wifiScanReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
            if (success) {
                scanSuccess()
            }
        }
    }

    fun startScanning() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        context.registerReceiver(wifiScanReceiver, intentFilter)
        wifiManager.startScan()
    }

    fun stopScanning() {
        context.unregisterReceiver(wifiScanReceiver)
    }

    private fun scanSuccess() {
        val results = wifiManager.scanResults
        val wifiDataList = results.map {
            WifiData(
                timestamp = System.currentTimeMillis(),
                bssid = it.BSSID,
                rssi = it.level
            )
        }
        _wifiDataFlow.value = wifiDataList
    }
}
