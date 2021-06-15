package com.example.tatneft_quest.travelPackage

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tatneft_quest.R
import com.example.tatneft_quest.Variables
import com.example.tatneft_quest.Variables.Companion.pointsSheet
import com.example.tatneft_quest.databinding.FragmentMapBinding
import com.example.tatneft_quest.models.ClusterMarkerPoints
import com.example.tatneft_quest.utils.MyClusterManagerRendererPoints
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager

class MapFragment : Fragment(), OnMapReadyCallback, View.OnClickListener {
    private var map: GoogleMap? = null
    private var alertDialog: AlertDialog? = null

    private var clusterManagerPoints: ClusterManager<ClusterMarkerPoints>? = null
    private var clusterManagerRendererPoints: MyClusterManagerRendererPoints? = null
    private lateinit var currentPoint: ClusterMarkerPoints

    private var numberClick = 1
    private var serviceOk = false

    private lateinit var navPanel: RelativeLayout
    private lateinit var arrowLeft: ImageButton
    private lateinit var arrowRight: ImageButton
    private lateinit var numberPointsText: TextView
    private lateinit var namePointsText: TextView
    private lateinit var binding: FragmentMapBinding

    companion object {
        private const val TAG = "check"
        private const val DEFAULT_ZOOM = 15f
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMapBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Квест"
        init()
    }

    private fun init() {
        navPanel = binding.navPanel
        arrowLeft = binding.arrowLeft
        arrowRight = binding.arrowRight
        numberPointsText = binding.numberPoints
        namePointsText = binding.namePoints

        arrowLeft.setOnClickListener(this)
        arrowRight.setOnClickListener(this)
    }

    private fun visibility() {
        navPanel.visibility = View.VISIBLE
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        firstPointMove()
        addMarkerPoints()
    }

    private fun initMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    @SuppressLint("SetTextI18n")
    private fun firstPointMove() {
        currentPoint = pointsSheet[numberClick - 1]
        moveCamera(currentPoint.position.latitude, currentPoint.position.longitude)
        numberPointsText.text = "${currentPoint.getId()} / ${pointsSheet.size}"
        namePointsText.text = currentPoint.title
    }

    //  Добавление маркеров
    private fun addMarkerPoints() {
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
                pointsSheet.forEach {
                    clusterManagerPoints!!.addItem(it)
                }
            } catch (e: java.lang.NullPointerException) {
                Log.e(TAG, "addMapMarkersPoints: NullPointerException: ${e.message}")
            }
        }
        clusterManagerPoints?.cluster()
    }

    //  Переключения локаций
    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.arrowLeft -> {
                if (numberClick == 1) {
                    numberClick = pointsSheet.size
                } else {
                    numberClick--
                }
                currentPoint = pointsSheet[numberClick - 1]
                animateCamera(currentPoint.position.latitude, currentPoint.position.longitude)
                numberPointsText.text = "${currentPoint.getId()} / ${pointsSheet.size}"
                namePointsText.text = currentPoint.title
            }
            R.id.arrowRight -> {
                if (numberClick == pointsSheet.size) {
                    numberClick = 1
                } else {
                    numberClick++
                }
                currentPoint = pointsSheet[numberClick - 1]
                animateCamera(currentPoint.position.latitude, currentPoint.position.longitude)
                numberPointsText.text = "${currentPoint.getId()} / ${pointsSheet.size}"
                namePointsText.text = currentPoint.title
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (checkMapServices()) {
            initMap()
        }
    }

    override fun onResume() {
        super.onResume()
        if (serviceOk) {
            visibility()
        }
    }

    override fun onStop() {
        super.onStop()
        alertDialog?.dismiss()
    }

    //  Проверка перед запуском карты
    private fun checkMapServices(): Boolean {
        if (isServicesOK()) {
            serviceOk = true
            return true
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
                Variables.fragmentList.removeAt(Variables.fragmentList.size - 1)
                super.requireActivity().onBackPressed()
            }
        }
        return false
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
                Variables.fragmentList.removeAt(Variables.fragmentList.size - 1)
                super.requireActivity().onBackPressed()
            }
        alertDialog = builder.create()
        alertDialog!!.show()
    }

    private fun moveCamera(latitude: Double, longitude: Double) {
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude),
            DEFAULT_ZOOM))
    }

    private fun animateCamera(latitude: Double, longitude: Double) {
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude),
            DEFAULT_ZOOM))
    }

}