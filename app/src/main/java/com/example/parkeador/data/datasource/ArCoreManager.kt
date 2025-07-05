package com.example.parkeador.data.datasource

import android.content.Context
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.core.TrackingState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ArCoreManager(private val context: Context) {

    private var session: Session? = null

    fun start() {
        session = Session(context)
        val config = Config(session)
        session?.configure(config)
    }

    fun stop() {
        session?.close()
        session = null
    }

    fun getPoseFlow(): Flow<String> = flow {
        while (session != null) {
            session?.update()?.let { frame ->
                if (frame.camera.trackingState == TrackingState.TRACKING) {
                    val pose = frame.camera.pose
                    emit("Pose: ${pose.tx()}, ${pose.ty()}, ${pose.tz()}")
                }
            }
        }
    }
}
