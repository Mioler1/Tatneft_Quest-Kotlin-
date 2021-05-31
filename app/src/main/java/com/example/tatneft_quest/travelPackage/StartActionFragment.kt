package com.example.tatneft_quest.travelPackage

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.*
import com.example.tatneft_quest.fragments.BaseFragment
import com.example.tatneft_quest.models.ClusterMarker
import com.example.tatneft_quest.R
import com.example.tatneft_quest.services.LocationService
import com.example.tatneft_quest.utils.MyClusterManagerRenderer
import com.example.tatneft_quest.utils.ViewWeightAnimationWrapper
import com.example.tatneft_quest.Variables.Companion.LATITUDE
import com.example.tatneft_quest.Variables.Companion.LONGTITUDE
import com.example.tatneft_quest.databinding.FragmentStartActionBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.mikepenz.iconics.Iconics.applicationContext

@Suppress("DEPRECATION")
@SuppressLint("MissingPermission")
class StartActionFragment : BaseFragment(), OnMapReadyCallback, View.OnClickListener {
    //  Widgets
    private var map: GoogleMap? = null
    private lateinit var headerRelative: RelativeLayout
    private lateinit var mapRelative: RelativeLayout
    private lateinit var footerRelative: RelativeLayout
    private lateinit var btnMapFullScreen: ImageView

    private var lastKnownLocation: Location? = null
    private var locationManager: LocationManager? = null
    private var cameraPosition: CameraPosition? = null
    private val mHandler: Handler = Handler()
    private var mRunnable: Runnable? = null

    private var mClusterManager: ClusterManager<ClusterMarker>? = null
    private var mClusterManagerRenderer: MyClusterManagerRenderer? = null
    private val mClusterMarkers: ArrayList<ClusterMarker> = ArrayList()

    private var mLocationPermissionGranted = false
    private val defaultLocation = LatLng(54.901388, 52.297118)
    private var mMapLayoutState = 0

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var binding: FragmentStartActionBinding

    companion object {
        private const val TAG = "Map"
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
        private const val DEFAULT_ZOOM = 15
        private const val LOCATION_UPDATE_INTERVAL = 3000
        private const val ERROR_DIALOG_REQUEST = 9001
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9002
        private const val START_GPS = 9003

        private const val MAP_LAYOUT_STATE_CONTRACTED = 0
        private const val MAP_LAYOUT_STATE_EXPANDED = 1
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        if (checkSelfPermission(activity!!,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        getDeviceLocation()

        map?.isMyLocationEnabled = true
        map?.uiSettings?.isMyLocationButtonEnabled = true
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        map?.let { map ->
//            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
//            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
//        }
//        super.onSaveInstanceState(outState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
//        if (savedInstanceState != null) {
//            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
//            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
//        }
        binding = FragmentStartActionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?

        headerRelative = binding.headerRelative
        mapRelative = binding.mapRelative
        footerRelative = binding.footerRelative
        btnMapFullScreen = binding.btnMapFullScreen.also {
            it.setOnClickListener(this)
        }
        binding.inPlace.setOnClickListener {
            binding.inPlace.visibility = View.GONE
            binding.btnScan.visibility = View.VISIBLE
        }
        binding.btnScan.setOnClickListener {
            mFragmentHandler?.replace(LocationHistoryFragment(), true)
        }
    }

    private fun initMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    private fun getDeviceLocation() {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(applicationContext)
        try {
            if (mLocationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            addMarker(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
                            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(
                                lastKnownLocation!!.latitude,
                                lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
                        }
                    } else {
                        Log.e(TAG, "getDeviceLocation: ${task.exception}")
                        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation,
                            DEFAULT_ZOOM.toFloat()))
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "getDeviceLocation: ${e.message}")
        }
    }

    private fun startUserLocationsRunnable() {
        Log.d(TAG, "startUserLocationsRunnable")
        mHandler.postDelayed(Runnable {
            retrieveUserLocations()
            mHandler.postDelayed(mRunnable!!, LOCATION_UPDATE_INTERVAL.toLong())
        }.also { mRunnable = it }, LOCATION_UPDATE_INTERVAL.toLong())
    }

    private fun stopLocationUpdates() {
        mHandler.removeCallbacks(mRunnable!!)
        Log.d(TAG, "stopLocationUpdates")
    }

    private fun retrieveUserLocations() {
        for (clusterMarker in mClusterMarkers) {
            for (i in mClusterMarkers.indices) {
                mClusterMarkers[i].position = LatLng(LATITUDE, LONGTITUDE)
                mClusterManagerRenderer!!.setUpdateMarker(mClusterMarkers[i])
                Log.d(TAG, "retrieveUserLocations: $LATITUDE + $LONGTITUDE")
            }
        }
    }

    private fun addMarker(latitude: Double, longitude: Double) {
        if (map != null) {
            if (mClusterManager == null) {
                mClusterManager = ClusterManager<ClusterMarker>(activity!!.applicationContext, map)
            }
            if (mClusterManagerRenderer == null) {
                mClusterManagerRenderer = MyClusterManagerRenderer(activity!!, map, mClusterManager)
                mClusterManager!!.renderer = mClusterManagerRenderer
            }
            try {
                val newClusterMarker = ClusterMarker(LatLng(latitude, longitude), R.drawable.icon5)
                mClusterManager!!.addItem(newClusterMarker)
                mClusterMarkers.add(newClusterMarker)
            } catch (e: NullPointerException) {
                Log.e(TAG, "addMapMarkers: NullPointerException: ${e.message}")
            }
        }
        mClusterManager?.cluster()
    }

    override fun onStart() {
        super.onStart()
        if (checkMapServices()) {
            if (!mLocationPermissionGranted) {
                getLocationPermission()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (mLocationPermissionGranted) {
            startLocationService()
            startUserLocationsRunnable()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mLocationPermissionGranted) {
            stopLocationUpdates()
        }
    }

    private fun startLocationService() {
        if (!isLocationServiceRunning()) {
            val intent = Intent(context, LocationService::class.java)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                startForegroundService(context!!, intent)
            } else {
                activity?.startService(intent)
            }
        }
    }

    private fun isLocationServiceRunning(): Boolean {
        val manager = activity?.getSystemService(ACTIVITY_SERVICE) as ActivityManager?
        for (service in manager!!.getRunningServices(Int.MAX_VALUE)) {
            if ("com.example.tatneft_quest.Services.LocationService" == service.service.className) {
                Log.d(TAG, "isLocationServiceRunning: location service is already running.")
                return true
            }
        }
        Log.d(TAG, "isLocationServiceRunning: location service is not running.")
        return false
    }

    private fun checkMapServices(): Boolean {
        if (isServicesOK()) {
            if (isMapsEnabled()) {
                return true
            }
        }
        return false
    }

    private fun isServicesOK(): Boolean {
        val available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
        when {
            available == ConnectionResult.SUCCESS -> {
                return true
            }
            GoogleApiAvailability.getInstance().isUserResolvableError(available) -> {
                GoogleApiAvailability.getInstance()
                    .getErrorDialog(activity, available, ERROR_DIALOG_REQUEST).show()
            }
            else -> {
                Toast.makeText(applicationContext,
                    "Вы не можете делать запросы на карту",
                    Toast.LENGTH_SHORT).show()
                activity?.finish()
            }
        }
        return false
    }

    private fun isMapsEnabled(): Boolean {
        val manager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        if (!manager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()
            return false
        }
        return true
    }

    private fun buildAlertMessageNoGps() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context!!)
        builder.setMessage("Это приложение требует GPS. Вы хотите его включить?")
            .setCancelable(false)
            .setPositiveButton("Да") { _, _ ->
                startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), START_GPS)
            }
            .setNegativeButton("Нет") { _, _ ->
                activity?.finish()
            }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    private fun getLocationPermission() {
        if (checkSelfPermission(activity!!,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationPermissionGranted = true
            initMap()
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray,
    ) {
        mLocationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    mFragmentHandler?.replace(StartActionFragment(), false)
                } else {
                    activity?.finish()
                }
            }
        }
    }

    private fun expandMapAnimation() {
        val mapAnimationWrapper = ViewWeightAnimationWrapper(mapRelative)
        val mapAnimation = ObjectAnimator.ofFloat(mapAnimationWrapper,
            "weight", 65f, 100f)
        mapAnimation.duration = 0
        val recyclerHeaderAnimationWrapper = ViewWeightAnimationWrapper(headerRelative)
        val recyclerHeaderAnimation = ObjectAnimator.ofFloat(recyclerHeaderAnimationWrapper,
            "weight", 15f, 0f)
        recyclerHeaderAnimation.duration = 0
        val recyclerFooterAnimationWrapper = ViewWeightAnimationWrapper(footerRelative)
        val recyclerFooterAnimation = ObjectAnimator.ofFloat(recyclerFooterAnimationWrapper,
            "weight", 20f, 0f)
        recyclerFooterAnimation.duration = 0
        recyclerHeaderAnimation.start()
        recyclerFooterAnimation.start()
        mapAnimation.start()
    }

    private fun contractMapAnimation() {
        val mapAnimationWrapper = ViewWeightAnimationWrapper(mapRelative)
        val mapAnimation = ObjectAnimator.ofFloat(mapAnimationWrapper,
            "weight", 100f, 65f)
        mapAnimation.duration = 0
        val recyclerHeaderAnimationWrapper = ViewWeightAnimationWrapper(headerRelative)
        val recyclerHeaderAnimation = ObjectAnimator.ofFloat(recyclerHeaderAnimationWrapper,
            "weight", 0f, 15f)
        recyclerHeaderAnimation.duration = 0
        val recyclerFooterAnimationWrapper = ViewWeightAnimationWrapper(footerRelative)
        val recyclerFooterAnimation = ObjectAnimator.ofFloat(recyclerFooterAnimationWrapper,
            "weight", 0f, 20f)
        recyclerFooterAnimation.duration = 0
        recyclerHeaderAnimation.start()
        recyclerFooterAnimation.start()
        mapAnimation.start()
    }

    @Override
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnMapFullScreen -> {
                if (mMapLayoutState == MAP_LAYOUT_STATE_CONTRACTED) {
                    mMapLayoutState = MAP_LAYOUT_STATE_EXPANDED
                    expandMapAnimation()
                    btnMapFullScreen.setImageResource(R.drawable.ic_fullscreen_exit)
                } else if (mMapLayoutState == MAP_LAYOUT_STATE_EXPANDED) {
                    mMapLayoutState = MAP_LAYOUT_STATE_CONTRACTED
                    contractMapAnimation()
                    btnMapFullScreen.setImageResource(R.drawable.ic_fullscreen)
                }
            }
        }
    }
}