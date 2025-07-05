package com.example.parkeador.features.crearhuella

import android.view.SurfaceView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CrearHuellaScreen(
    isCapturing: Boolean,
    arCoreManager: ArCoreManager,
    onStartCaptureClick: () -> Unit,
    onStopCaptureClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isCapturing) {
            AndroidView(
                factory = { context ->
                    SurfaceView(context).apply {
                        arCoreManager.setSurfaceView(this)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
            Button(
                onClick = onStopCaptureClick,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text(text = "Stop Capture")
            }
        } else {
            Button(onClick = onStartCaptureClick) {
                Text(text = "Start Capture")
            }
        }
    }
}