package com.example.parkeador.features.crearhuella

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CrearHuellaViewModel(application: Application) : AndroidViewModel(application) {

    private val sensorDataManager = SensorDataManager(application)
    private val radioDataManager = RadioDataManager(application)

    private val _isCapturing = MutableStateFlow(false)
    val isCapturing: StateFlow<Boolean> = _isCapturing

    fun onStartCaptureClick() {
        _isCapturing.value = true
        sensorDataManager.start()
        radioDataManager.start()
    }

    fun onStopCaptureClick() {
        _isCapturing.value = false
        sensorDataManager.stop()
        radioDataManager.stop()
    }
}