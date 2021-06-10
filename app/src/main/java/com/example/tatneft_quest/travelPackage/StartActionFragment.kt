package com.example.tatneft_quest.travelPackage

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.*
import com.example.tatneft_quest.*
import com.example.tatneft_quest.R
import com.example.tatneft_quest.Variables.Companion.LATITUDE
import com.example.tatneft_quest.Variables.Companion.LONGITUDE
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER
import com.example.tatneft_quest.Variables.Companion.fragmentList
import com.example.tatneft_quest.Variables.Companion.pointsSheet
import com.example.tatneft_quest.databinding.FragmentStartActionBinding
import com.example.tatneft_quest.fragments.BaseFragment
import com.example.tatneft_quest.models.ClusterMarkerPoints
import com.example.tatneft_quest.models.ClusterMarkerUser
import com.example.tatneft_quest.services.LocationService
import com.example.tatneft_quest.utils.MyClusterManagerRendererPoints
import com.example.tatneft_quest.utils.MyClusterManagerRendererUser
import com.example.tatneft_quest.utils.ViewWeightAnimationWrapper
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.clustering.ClusterManager
import com.google.zxing.integration.android.IntentIntegrator
import com.mikepenz.iconics.Iconics.applicationContext
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


@Suppress("DEPRECATION")
@SuppressLint("MissingPermission")
class StartActionFragment : BaseFragment(), OnMapReadyCallback, View.OnClickListener {
    //  Widgets
    private lateinit var headerRelative: RelativeLayout
    private lateinit var mapRelative: RelativeLayout
    private lateinit var footerRelative: RelativeLayout
    private lateinit var btnMapFullScreen: ImageView
    private lateinit var btnMoveCamera: ImageView
    private lateinit var btnInPlace: Button
    private lateinit var btnSeeingMap: Button
    private lateinit var btnScan: Button
    private lateinit var pointPosition: TextView
    private lateinit var nameLocation: TextView

    private var alertDialog: AlertDialog? = null
    private var map: GoogleMap? = null
    private var lastKnownLocation: Location? = null
    private val mHandler: Handler = Handler()
    private var mRunnable: Runnable? = null

    private var mClusterManagerUser: ClusterManager<ClusterMarkerUser>? = null
    private var mClusterManagerRendererUser: MyClusterManagerRendererUser? = null
    private val mClusterMarkersUser: ArrayList<ClusterMarkerUser> = ArrayList()

    private var clusterManagerPoints: ClusterManager<ClusterMarkerPoints>? = null
    private var clusterManagerRendererPoints: MyClusterManagerRendererPoints? = null

    private var mLocationPermissionGranted = false
    private val defaultLocation = LatLng(54.901388, 52.297118)
    private var mMapLayoutState = 0

    private lateinit var sharedPreferencesUser: SharedPreferences
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var binding: FragmentStartActionBinding

    companion object {
        private const val TAG = "check"
        private const val DEFAULT_ZOOM = 15f
        private const val LOCATION_UPDATE_INTERVAL = 3000
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9002
        private const val START_GPS = 9003
        private const val MAP_LAYOUT_STATE_CONTRACTED = 0
        private const val MAP_LAYOUT_STATE_EXPANDED = 1
    }

    //  Интерфейс карты
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        if (checkSelfPermission(requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        getDeviceLocation()
        addMarkerPoint()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentStartActionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun initMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    private fun init() {
        sharedPreferencesUser = requireActivity().getSharedPreferences(SAVE_DATA_USER, MODE_PRIVATE)
        headerRelative = binding.headerRelative
        mapRelative = binding.mapRelative
        footerRelative = binding.footerRelativeButton!!
        btnMapFullScreen = binding.btnMapFullScreen
        btnMoveCamera = binding.btnMoveCamera!!
        btnInPlace = binding.inPlace
        btnSeeingMap = binding.seeingMap
        btnScan = binding.btnScan
        pointPosition = binding.pointPosition
        nameLocation = binding.nameLocation

        btnMapFullScreen.setOnClickListener(this)
        btnMoveCamera.setOnClickListener(this)
        btnInPlace.setOnClickListener {
            btnInPlace.visibility = View.GONE
            btnScan.visibility = View.VISIBLE
        }
        btnScan.setOnClickListener {
//            IntentIntegrator.forSupportFragment(this)
//                .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
//                .setPrompt("Отсканируйте QR-код")
//                .setCameraId(0)
//                .setBeepEnabled(false)
//                .setOrientationLocked(false)
//                .setBarcodeImageEnabled(true)
//                .initiateScan()
            mFragmentHandler?.replace(LocationHistoryFragment(), true)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun visibility() {
        btnMapFullScreen.visibility = View.VISIBLE
        btnMoveCamera.visibility = View.VISIBLE
        btnInPlace.visibility = View.VISIBLE
        btnSeeingMap.visibility = View.VISIBLE

        pointsSheet.forEach { el ->
            if (el.getActive()) {
                pointPosition.text = "Точка №${el.getId()}"
                nameLocation.text = el.title
            }
        }
    }

    private fun moveCamera(latitude: Double, longitude: Double) {
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude),
            DEFAULT_ZOOM))
    }

    private fun animateCamera(latitude: Double, longitude: Double) {
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude),
            DEFAULT_ZOOM))
    }

    //  Первая локация пользователя
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
                            addMarkerUser(lastKnownLocation!!.latitude,
                                lastKnownLocation!!.longitude)
                            moveCamera(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
                        } else {
                            getDeviceLocation()
                        }
                    } else {
                        Log.e(TAG, "getDeviceLocation: ${task.exception}")
                        moveCamera(defaultLocation.latitude, defaultLocation.longitude)
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "getDeviceLocation: ${e.message}")
        }
    }

    //  Запуск потока для обновления местоположения маркера
    private fun startUserLocationsRunnable() {
        Log.d(TAG, "startUserLocationsRunnable")
        mHandler.postDelayed(Runnable {
            retrieveUserLocations()
            mHandler.postDelayed(mRunnable!!, LOCATION_UPDATE_INTERVAL.toLong())
        }.also { mRunnable = it }, LOCATION_UPDATE_INTERVAL.toLong())
    }

    //  Остановка потока обновления местоположения маркера
    private fun stopLocationUpdates() {
        mHandler.removeCallbacks(mRunnable!!)
        Log.d(TAG, "stopLocationUpdates")
    }

    //  Обновление местоположения маркера
    private fun retrieveUserLocations() {
        for (clusterMarker in mClusterMarkersUser) {
            for (i in mClusterMarkersUser.indices) {
                mClusterMarkersUser[i].position = LatLng(LATITUDE, LONGITUDE)
                mClusterManagerRendererUser!!.setUpdateMarker(mClusterMarkersUser[i])
            }
        }
    }

    //  Маркет пользователя
    private fun addMarkerUser(latitude: Double, longitude: Double) {
        if (map != null) {
            if (mClusterManagerUser == null) {
                mClusterManagerUser =
                    ClusterManager<ClusterMarkerUser>(requireActivity().applicationContext, map)
            }
            if (mClusterManagerRendererUser == null) {
                mClusterManagerRendererUser =
                    MyClusterManagerRendererUser(requireActivity(), map, mClusterManagerUser)
                mClusterManagerUser!!.renderer = mClusterManagerRendererUser
            }
            try {
                val avatar =
                    sharedPreferencesUser.getString(Variables.SAVE_DATA_USER_AVATAR, "").toString()
                val byteArray = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Base64.getDecoder().decode(avatar)
                } else {
                    android.util.Base64.decode(avatar, android.util.Base64.DEFAULT)
                }
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

                val newClusterMarker = ClusterMarkerUser(LatLng(latitude, longitude),
                    "Ваше местоположение", bitmap)
                mClusterManagerUser!!.addItem(newClusterMarker)
                mClusterMarkersUser.add(newClusterMarker)
            } catch (e: NullPointerException) {
                Log.e(TAG, "addMapMarkersUser: NullPointerException: ${e.message}")
            }
        }
        mClusterManagerUser?.cluster()
    }

    private fun addMarkerPoint() {
        if (map != null) {
            if (clusterManagerPoints == null) {
                clusterManagerPoints =
                    ClusterManager<ClusterMarkerPoints>(requireActivity().applicationContext, map)
            }
            if (clusterManagerRendererPoints == null) {
                clusterManagerRendererPoints =
                    MyClusterManagerRendererPoints(requireActivity(), map, clusterManagerPoints)
                clusterManagerPoints!!.renderer = clusterManagerRendererPoints
            }
            try {
                pointsSheet.forEach { el ->
                    if (el.getActive()) {
                        clusterManagerPoints!!.addItem(el)
                    }
                }
            } catch (e: java.lang.NullPointerException) {
                Log.e(TAG, "addMapMarkersPoints: NullPointerException: ${e.message}")
            }
        }
        clusterManagerPoints?.cluster()
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
            visibility()
            startLocationService()
            startUserLocationsRunnable()
        }
    }

    override fun onStop() {
        super.onStop()
        alertDialog?.dismiss()
        if (mLocationPermissionGranted) {
            stopLocationUpdates()
        }
    }

    //  Запуск сервиса
    private fun startLocationService() {
        if (!isLocationServiceRunning()) {
            val intent = Intent(context, LocationService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(requireContext(), intent)
            } else {
                activity?.startService(intent)
            }
        }
    }

    // Проверка запущен ли сервис
    private fun isLocationServiceRunning(): Boolean {
        val manager = activity?.getSystemService(ACTIVITY_SERVICE) as ActivityManager?
        for (service in manager!!.getRunningServices(Int.MAX_VALUE)) {
            if ("com.example.tatneft_quest.services.LocationService" == service.service.className) {
                Log.d(TAG, "isLocationServiceRunning: location service is already running.")
                return true
            }
        }
        Log.d(TAG, "isLocationServiceRunning: location service is not running.")
        return false
    }

    //  Проверка перед запуском карты
    private fun checkMapServices(): Boolean {
        if (isServicesOK()) {
            if (isMapsEnabled()) {
                return true
            }
        }
        return false
    }

    //  Проверка google services
    private fun isServicesOK(): Boolean {
        val available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
        when {
            available == ConnectionResult.SUCCESS -> {
                return true
            }
            GoogleApiAvailability.getInstance().isUserResolvableError(available) -> {
                buildAlertMessageUpdateGooglePlayServices()
            }
            else -> {
                Toast.makeText(requireActivity().applicationContext,
                    "Вы не можете делать запросы на карту",
                    Toast.LENGTH_SHORT).show()
                fragmentList.removeAt(fragmentList.size - 1)
                super.requireActivity().onBackPressed()
            }
        }
        return false
    }

    //  Поверка вкл ли gps
    private fun isMapsEnabled(): Boolean {
        val manager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        if (!manager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()
            return false
        }
        return true
    }

    //  Модальное окно GooglePlayServices
    private fun buildAlertMessageUpdateGooglePlayServices() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Требуется обновление Google Play сервисов")
            .setCancelable(false)
            .setPositiveButton("Обновить") { _, _ ->
                try {
                    startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.google.android.gms&hl=ru&gl=RU")))
                } catch (e: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.gms&hl=ru&gl=RU")))
                }
            }
            .setNegativeButton("Отказаться") { _, _ ->
                alertDialog?.dismiss()
                fragmentList.removeAt(fragmentList.size - 1)
                super.requireActivity().onBackPressed()
            }
        alertDialog = builder.create()
        alertDialog!!.show()
    }

    //  Модальное окно Gps
    private fun buildAlertMessageNoGps() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Это приложение требует GPS. Вы хотите его включить?")
            .setCancelable(false)
            .setPositiveButton("Да") { _, _ ->
                startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), START_GPS)
            }
            .setNegativeButton("Нет") { _, _ ->
                fragmentList.removeAt(fragmentList.size - 1)
                super.requireActivity().onBackPressed()
            }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    //  Проверка если ли доступ к локации пользователя
    private fun getLocationPermission() {
        if (checkSelfPermission(requireActivity(),
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

    //  Прием ответа пользователя на доступ к локации
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
                    fragmentList.removeAt(fragmentList.size - 1)
                    super.requireActivity().onBackPressed()
                }
            }
        }
    }

    //  Проверка QR-code
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Snackbar.make(requireView(), "Нужно отсканировать QR-код", Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                val json = JSONObject(result.contents)
                val namePoint = json.get("name").toString()
                pointsSheet.forEach { el ->
                    if (el.getActive()) {
                        if (el.title == namePoint) {
                            mFragmentHandler?.replace(LocationHistoryFragment(), true)
                        } else {
                            Snackbar.make(requireView(),
                                "QR-код не соответсвует данной локации",
                                Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
            Snackbar.make(requireView(), "Что то не так", Snackbar.LENGTH_SHORT).show()
        }
    }

    //  Полный экран карты
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

    //  Обычный экран
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

    //  Клики на иконки карты
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
            R.id.btnMoveCamera -> {
                animateCamera(LATITUDE, LONGITUDE)
            }
        }
    }
}