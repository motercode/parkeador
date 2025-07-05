package com.example.parkeador.data.datasource

import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import com.example.parkeador.domain.model.BluetoothData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class BluetoothScanner(private val context: Context) {

    private val bluetoothManager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
    private val _bluetoothDataFlow = MutableStateFlow<List<BluetoothData>>(emptyList())
    val bluetoothDataFlow: Flow<List<BluetoothData>> = _bluetoothDataFlow

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE, BluetoothDevice::class.java)
                    } else {
                        @Suppress("DEPRECATION")
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    }
                    val rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE)
                    device?.let {
                        val bluetoothData = BluetoothData(
                            timestamp = System.currentTimeMillis(),
                            macAddress = it.address,
                            rssi = rssi.toInt()
                        )
                        _bluetoothDataFlow.value = _bluetoothDataFlow.value + bluetoothData
                    }
                }
            }
        }
    }

    fun startScanning() {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(receiver, filter)
        // TODO: Handle the case where the permission is not granted
        if (context.checkSelfPermission(android.Manifest.permission.BLUETOOTH_SCAN) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            bluetoothAdapter?.startDiscovery()
        }
    }

    fun stopScanning() {
        context.unregisterReceiver(receiver)
        bluetoothAdapter?.cancelDiscovery()
    }
}
