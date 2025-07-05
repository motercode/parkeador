package com.example.parkeador.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkeador.data.datasource.ArCoreManager
import com.example.parkeador.data.datasource.BluetoothScanner
import com.example.parkeador.data.datasource.ImuManager
import com.example.parkeador.data.datasource.WifiScanner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipOutputStream

class HuellaCaptureViewModel(application: Application) : AndroidViewModel(application) {

    private val arCoreManager = ArCoreManager(application)
    private val wifiScanner = WifiScanner(application)
    private val bluetoothScanner = BluetoothScanner(application)
    private val imuManager = ImuManager(application)

    private val _isCapturing = MutableStateFlow(false)
    val isCapturing: StateFlow<Boolean> = _isCapturing

    private val poseData = mutableListOf<String>()
    private val wifiData = mutableListOf<String>()
    private val bluetoothData = mutableListOf<String>()
    private val imuData = mutableListOf<String>()

    fun startCapture() {
        _isCapturing.value = true
        arCoreManager.start()
        wifiScanner.startScanning()
        bluetoothScanner.startScanning()
        imuManager.start()

        arCoreManager.getPoseFlow()
            .onEach { poseData.add(it) }
            .launchIn(viewModelScope)

        wifiScanner.wifiDataFlow
            .onEach { wifiData.add(it.toString()) }
            .launchIn(viewModelScope)

        bluetoothScanner.bluetoothDataFlow
            .onEach { bluetoothData.add(it.toString()) }
            .launchIn(viewModelScope)

        imuManager.imuDataFlow
            .onEach { imuData.add(it.toString()) }
            .launchIn(viewModelScope)
    }

    fun stopCapture() {
        _isCapturing.value = false
        arCoreManager.stop()
        wifiScanner.stopScanning()
        bluetoothScanner.stopScanning()
        imuManager.stop()
        zipData()
    }

    private fun zipData() {
        val zipFile = File(getApplication<Application>().cacheDir, "huella_data.zip")
        ZipOutputStream(FileOutputStream(zipFile)).use { zipOutputStream ->
            zipOutputStream.putNextEntry(java.util.zip.ZipEntry("pose.txt"))
            zipOutputStream.write(poseData.joinToString("\n").toByteArray())
            zipOutputStream.closeEntry()

            zipOutputStream.putNextEntry(java.util.zip.ZipEntry("wifi.txt"))
            zipOutputStream.write(wifiData.joinToString("\n").toByteArray())
            zipOutputStream.closeEntry()

            zipOutputStream.putNextEntry(java.util.zip.ZipEntry("bluetooth.txt"))
            zipOutputStream.write(bluetoothData.joinToString("\n").toByteArray())
            zipOutputStream.closeEntry()

            zipOutputStream.putNextEntry(java.util.zip.ZipEntry("imu.txt"))
            zipOutputStream.write(imuData.joinToString("\n").toByteArray())
            zipOutputStream.closeEntry()
        }
    }
}