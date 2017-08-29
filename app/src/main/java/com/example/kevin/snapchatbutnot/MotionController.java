package com.example.kevin.snapchatbutnot;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Kevin on 6/24/2017.
 */

public class MotionController implements SensorEventListener {

    DrawActivity drawActivity;
    SensorManager sensorManager;
    Sensor accSensor;
    float[] accData;

    public MotionController(DrawActivity drawActivity) {
        this.drawActivity = drawActivity;
        sensorManager = (SensorManager) drawActivity.getSystemService(Context.SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accData = new float[3];
    }

    public void register() {
        if (accSensor != null) {
            sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void unregister() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.equals(accSensor)) {
            final float alpha = 0.8f;

            float[] gravity = new float[3];
            // Isolate the force of gravity with the low-pass filter.
            gravity[0] = alpha * gravity[0] + (1 - alpha) * sensorEvent.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * sensorEvent.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * sensorEvent.values[2];

            // Remove the gravity contribution with the high-pass filter.
            accData[0] = sensorEvent.values[0] - gravity[0];
            accData[1] = sensorEvent.values[1] - gravity[1];
            accData[2] = sensorEvent.values[2] - gravity[2];

            if (Math.abs(accData[0]) > 17) {
                drawActivity.clearIcons();
            }
            if (Math.abs(accData[1]) > 19) {
                drawActivity.undoIcon();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
