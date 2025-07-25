package com.example.parkeador

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.parkeador.presentation.ui.HuellaCaptureScreen
import com.example.parkeador.presentation.viewmodel.HuellaCaptureViewModel

class MainActivity : ComponentActivity() {

    private val huellaCaptureViewModel: HuellaCaptureViewModel by viewModels()
    private lateinit var arCoreManager: ArCoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arCoreManager = ArCoreManager(this)
        arCoreManager.create()

        setContent {
            ParkeadorTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.MAIN_SCREEN) {
                    composable(Routes.MAIN_SCREEN) {
                        MainScreen(
                            onCrearHuellaClick = { navController.navigate(Routes.CREAR_HUELLA_SCREEN) },
                            onBuscarHuellaClick = { navController.navigate(Routes.BUSCAR_HUELLA_SCREEN) }
                        )
                    }
                    composable(Routes.CREAR_HUELLA_SCREEN) {
                        HuellaCaptureScreen(viewModel = huellaCaptureViewModel)
                    }
                    composable(Routes.BUSCAR_HUELLA_SCREEN) {
                        BuscarHuellaScreen(
                            onStartSearchClick = { /* TODO */ }
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (arCoreManager.checkArCoreAvailability(this)) {
            arCoreManager.resume()
        } else {
            arCoreManager.requestArCoreInstallation(this)
        }
    }

    override fun onPause() {
        super.onPause()
        arCoreManager.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        arCoreManager.destroy()
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
        )
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        @SuppressLint("InlinedApi")
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH_SCAN
            ).toTypedArray()
    }
}
