package com.example.parkeador.features.crearhuella

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.bluetooth.BluetoothAdapter

class RadioDataManager(private val context: Context) {

    private val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    private val wifiScanReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Handle Wi-Fi scan results
        }
    }

    @SuppressLint("MissingPermission")
    fun start() {
        context.registerReceiver(wifiScanReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        wifiManager.startScan()

        bluetoothAdapter?.startDiscovery()
    }

    @SuppressLint("MissingPermission")
    fun stop() {
        context.unregisterReceiver(wifiScanReceiver)
        bluetoothAdapter?.cancelDiscovery()
    }
}