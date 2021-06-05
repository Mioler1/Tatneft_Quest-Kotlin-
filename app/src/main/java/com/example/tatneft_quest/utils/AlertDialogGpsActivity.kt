package com.example.tatneft_quest.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.tatneft_quest.MainActivity
import com.example.tatneft_quest.Variables.Companion.TAG
import com.example.tatneft_quest.Variables.Companion.fragmentList
import com.example.tatneft_quest.Variables.Companion.menuList

class AlertDialogGpsActivity : AppCompatActivity() {
    private var alert: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Это приложение требует GPS. Вы хотите его включить?")
            .setCancelable(false)
            .setPositiveButton("Да") { _, _ ->
                startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0)
            }
            .setNegativeButton("Нет") { _, _ ->
                finish()
                fragmentList.clear()
                menuList.clear()
                startActivity(Intent(this, MainActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
            }
        alert = builder.create()
        alert!!.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            Log.d(TAG, "onActivityResult: $resultCode")
            finish()
        }
    }
}