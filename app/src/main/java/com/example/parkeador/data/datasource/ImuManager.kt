package com.example.parkeador.data.datasource

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.parkeador.domain.model.ImuData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ImuManager(private val context: Context) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val gyroscope: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    private val _imuDataFlow = MutableStateFlow<ImuData?>(null)
    val imuDataFlow: Flow<ImuData?> = _imuDataFlow

    fun start() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            when (it.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> {
                    _imuDataFlow.value = ImuData(
                        timestamp = System.currentTimeMillis(),
                        accelerometer = it.values.clone(),
                        gyroscope = floatArrayOf(0f, 0f, 0f) // Placeholder
                    )
                }
                Sensor.TYPE_GYROSCOPE -> {
                    _imuDataFlow.value = _imuDataFlow.value?.copy(
                        gyroscope = it.values.clone()
                    )
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for this use case
    }
}
