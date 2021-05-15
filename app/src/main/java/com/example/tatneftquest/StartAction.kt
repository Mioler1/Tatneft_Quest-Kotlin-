package com.example.tatneftquest

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.tatneftquest.Menu.AppDrawer
import com.example.tatneftquest.Menu.TravelFragment
import com.example.tatneftquest.databinding.ActivityMainBinding
import com.example.tatneftquest.databinding.ActivityStartActionBinding
import com.example.tatneftquest.databinding.FragmentStartGeneralBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class StartAction : AppCompatActivity() {

    lateinit var mBinding: ActivityStartActionBinding
    lateinit var mPlace: Button
    lateinit var mScan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityStartActionBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        init()

        mPlace.setOnClickListener {
            mScan.visibility = View.VISIBLE
            mPlace.visibility = View.GONE
        }

        mScan.setOnClickListener {
            val scanner =  IntentIntegrator(this)
            scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            scanner.setBeepEnabled(false)
            scanner.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if(result != null) {
                if(result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show();
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private fun init() {
        mPlace = mBinding.inPlace
        mScan = mBinding.Scanner
    }



}