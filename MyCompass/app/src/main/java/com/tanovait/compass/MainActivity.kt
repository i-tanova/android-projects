package com.tanovait.compass

import android.hardware.Sensor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

import android.hardware.SensorManager
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView


class MainActivity : AppCompatActivity(), SensorEventListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        compassImage = findViewById(R.id.compassImage)
        text = findViewById(R.id.poleText)
    }

    private var sensorManager: SensorManager? = null
    private val accelerometerReading = FloatArray(3)
    private val magnetometerReading = FloatArray(3)

    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)
    private var compassImage: ImageView? = null
    private var text: TextView? = null

    override fun onResume() {
        super.onResume()
        sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager?.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_UI
            )
        }
        sensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.also { magneticField ->
            sensorManager?.registerListener(
                this,
                magneticField,
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_UI
            )
        }


    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerReading, 0, accelerometerReading.size)
        } else if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading, 0, magnetometerReading.size)
        }

        updateOrientationAngles()

        val rotation = -orientationAngles[0] * 180 / 3.14159
        compassImage?.setRotation((rotation).toFloat());
        Log.d("tag", " orientation: ${rotation}")
        //Log.d("tag", " azimuth: ${orientationAngles[0]}")

        val azimuth = (Math.toDegrees(orientationAngles[0].toDouble()) + 360) % 360
        Log.d("tag", " azimuth: ${azimuth}")
        Log.d("tag", " text: ${getAzimuthText(azimuth.toInt())}")
        text?.text = getAzimuthText(azimuth.toInt())

    }

    private fun getAzimuthText(azimuth: Int): CharSequence? {
        return when (azimuth) {
            in 0..30 -> "N"
            in 30..60 -> "NE"
            in 60..90 -> "E"
            in 90..120 -> "SE"
            in 120..150 -> "SE"
            in 150..180 -> "S"
            in 180..210 -> "S"
            in 210..240 -> "SW"
            in 240..270 -> "W"
            in 270..300 -> "W"
            in 300..330 -> "NW"
            in 330..360 -> "N"
            else -> "?"
        }
    }


    // Compute the three orientation angles based on the most recent readings from
    // the device's accelerometer and magnetometer.
    fun updateOrientationAngles() {
        // Update rotation matrix, which is needed to update orientation angles.
        SensorManager.getRotationMatrix(
            rotationMatrix,
            null,
            accelerometerReading,
            magnetometerReading
        )

        // "rotationMatrix" now has up-to-date information.

        SensorManager.getOrientation(rotationMatrix, orientationAngles)

        // "orientationAngles" now has up-to-date information.
    }
}