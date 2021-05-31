package com.example.tatneftquest.TravelPackage

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tatneftquest.Fragments.BaseFragment
import com.example.tatneftquest.LocationHistoryFragment
import com.example.tatneftquest.R
import com.example.tatneftquest.databinding.FragmentStartActionBinding
import org.json.JSONException
import org.json.JSONObject

class StartActionFragment : BaseFragment() {

    private lateinit var binding: FragmentStartActionBinding
//    private var qrScanIntegrator: IntentIntegrator? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartActionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inPlace.setOnClickListener {
            binding.inPlace.visibility = View.GONE
            binding.btnScan.visibility = View.VISIBLE

        }

        binding.btnScan.setOnClickListener {
//            performAction()
            mFragmentHandler?.replace(LocationHistoryFragment())
        }

//        qrScanIntegrator = IntentIntegrator.forFragment(this)
//        qrScanIntegrator?.setOrientationLocked(false)
//        qrScanIntegrator?.setPrompt(getString(R.string.scan_a_code))
//        qrScanIntegrator?.setCameraId(0)  // Use a specific camera of the device
//        qrScanIntegrator?.setBeepEnabled(false)
//        qrScanIntegrator?.setBarcodeImageEnabled(true)

    }

    private fun performAction() {
//        qrScanIntegrator?.initiateScan()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
//        if (result != null) {
//            if (result.contents == null) {
//                Toast.makeText(activity, R.string.result_not_found, Toast.LENGTH_LONG).show()
//            } else {
//                try {
//                    val obj = JSONObject(result.contents)
//                    binding.nameLocation.text = obj.getString("name")
//                    binding.descriptionLocation.text = obj.getString("site_name")
//
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                    Toast.makeText(activity, result.contents, Toast.LENGTH_LONG).show()
//                }
//
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data)
//        }
//    }
}