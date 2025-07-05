package com.example.parkeador

import android.app.Activity
import android.content.Context
import com.google.ar.core.ArCoreApk
import com.google.ar.core.Session
import com.google.ar.core.exceptions.CameraNotAvailableException

class ArCoreManager(private val context: Context) {

    private var session: Session? = null

    fun create() {
        // Create a new ARCore session.
        session = Session(context)
    }

    fun resume() {
        // Resume the ARCore session.
        try {
            session?.resume()
        } catch (e: CameraNotAvailableException) {
            // Handle the exception.
        }
    }

    fun pause() {
        // Pause the ARCore session.
        session?.pause()
    }

    fun destroy() {
        // Destroy the ARCore session.
        session?.close()
    }

    fun checkArCoreAvailability(activity: Activity): Boolean {
        val availability = ArCoreApk.getInstance().checkAvailability(activity)
        if (availability.isTransient) {
            // Re-query in a few seconds.
        }
        return availability.isSupported
    }

    fun requestArCoreInstallation(activity: Activity) {
        ArCoreApk.getInstance().requestInstall(activity, true)
    }
}
