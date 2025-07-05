package com.example.parkeador.features.crearhuella

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.SurfaceView
import com.google.ar.core.ArCoreApk
import com.google.ar.core.Session
import com.google.ar.core.exceptions.CameraNotAvailableException

class ArCoreManager(private val context: Context) {

    private var session: Session? = null
    private var surfaceView: SurfaceView? = null

    fun create() {
        if (session == null) {
            try {
                session = Session(context)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun resume() {
        try {
            session?.resume()
        } catch (e: CameraNotAvailableException) {
            e.printStackTrace()
            session = null
            return
        }
    }

    fun pause() {
        session?.pause()
    }

    fun destroy() {
        session?.close()
        session = null
    }

    fun setSurfaceView(surfaceView: SurfaceView) {
        this.surfaceView = surfaceView
        session?.setCameraTextureName(0)
    }

    fun checkArCoreAvailability(activity: Activity): Boolean {
        val availability = ArCoreApk.getInstance().checkAvailability(activity)
        if (availability.isTransient) {
            // Re-query at 5Hz while compatibility is checked in the background.
            Handler(Looper.getMainLooper()).postDelayed({
                checkArCoreAvailability(activity)
            }, 200)
        }
        return availability.isSupported
    }

    fun requestArCoreInstallation(activity: Activity) {
        ArCoreApk.getInstance().requestInstall(activity, true)
    }
}