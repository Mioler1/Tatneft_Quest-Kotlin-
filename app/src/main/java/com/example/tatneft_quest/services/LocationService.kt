package com.example.tatneft_quest.services

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.tatneft_quest.fragments.MyApplication
import com.example.tatneft_quest.MainActivity
import com.example.tatneft_quest.R
import com.example.tatneft_quest.Variables.Companion.LATITUDE
import com.example.tatneft_quest.Variables.Companion.LONGITUDE
import com.google.android.gms.location.*

@Suppress("DEPRECATION")
class LocationService : Service() {
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var notificationManager: NotificationManager? = null
    private var notification: Notification? = null
    private var mLocationCallback: LocationCallback? = null
    private lateinit var mLocationRequestHighAccuracy: LocationRequest

    companion object {
        private const val UPDATE_INTERVAL: Long = 4000
        private const val FASTEST_INTERVAL: Long = 2000
        private const val ACTION_STOP_SERVICE = "stop"
        private const val ACTION_START_ACTIVITY = "start"
        private const val TAG = "Map"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        sendNotification()
        checkLocation()
        requestLocationUpdates()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        if (ACTION_STOP_SERVICE == intent?.action) {
            if (!(applicationContext as MyApplication).isAppForeground()) {
                removeLocationUpdates()
                stopSelf()
            }
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    private fun sendNotification() {
        Log.d(TAG, "sendNotification")

        val stopIntent = Intent(this, LocationService::class.java)
        stopIntent.action = ACTION_STOP_SERVICE
        val pendingStopSelf =
            PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val startIntent = Intent(this, MainActivity::class.java)
        startIntent.action = ACTION_START_ACTIVITY
        startIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingStart = PendingIntent.getActivity(this, 0, startIntent, startIntent.flags)

        if (Build.VERSION.SDK_INT >= 26) {
            val channelId = "my_channel_01"
            val channel = NotificationChannel(channelId,
                "My Channel",
                NotificationManager.IMPORTANCE_DEFAULT)

            notificationManager?.createNotificationChannel(channel)
            notification = Notification.Builder(this, channelId)
                .setAutoCancel(false)
                .setTicker("this is ticker text")
                .setContentTitle("Tatneft Quest")
                .setContentText("Считываем вашу локацию")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingStart)
                .setOngoing(false)
                .addAction(R.drawable.ic_logout, "Закрыть", pendingStopSelf)
                .build()
        } else {
            notification = Notification.Builder(this)
                .setAutoCancel(false)
                .setTicker("this is ticker text")
                .setContentTitle("Tatneft Quest")
                .setContentText("Считываем вашу локацию")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingStart)
                .setOngoing(false)
                .addAction(R.drawable.ic_logout, "Закрыть", pendingStopSelf)
                .build()
        }

        startForeground(1, notification)
    }

    private fun checkLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "getLocation: stopping the location service.")
            stopSelf()
            return
        }

        mLocationRequestHighAccuracy = LocationRequest()
        mLocationRequestHighAccuracy.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequestHighAccuracy.interval = UPDATE_INTERVAL
        mLocationRequestHighAccuracy.fastestInterval = FASTEST_INTERVAL

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location: Location? = locationResult.lastLocation
                if (location != null) {
                    LATITUDE = location.latitude
                    LONGITUDE = location.longitude
                    Log.d(TAG, "$LATITUDE + $LONGITUDE")
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {
        mFusedLocationClient?.requestLocationUpdates(mLocationRequestHighAccuracy,
            mLocationCallback,
            Looper.myLooper())
    }

    private fun removeLocationUpdates() {
        mFusedLocationClient?.removeLocationUpdates(mLocationCallback)
    }
}