package com.example.parkeador.domain.model

data class ImuData(
    val timestamp: Long,
    val accelerometer: FloatArray,
    val gyroscope: FloatArray
)

data class WifiData(
    val timestamp: Long,
    val bssid: String,
    val rssi: Int
)

data class BluetoothData(
    val timestamp: Long,
    val macAddress: String,
    val rssi: Int
)
