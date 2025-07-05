package com.example.parkeador.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.parkeador.presentation.viewmodel.HuellaCaptureViewModel

@Composable
fun HuellaCaptureScreen(viewModel: HuellaCaptureViewModel = viewModel()) {
    val isCapturing = viewModel.isCapturing.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isCapturing.value) {
            Button(onClick = { viewModel.stopCapture() }) {
                Text("Fijar Punto Clave")
            }
        } else {
            Button(onClick = { viewModel.startCapture() }) {
                Text("Iniciar Captura")
            }
        }
    }
}
