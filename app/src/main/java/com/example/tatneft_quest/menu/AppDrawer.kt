package com.example.tatneft_quest.menu

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.tatneft_quest.R
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_AVATAR
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_EMAIL
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_NAME
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_PATRONYMIC
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_SURNAME
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_TOKEN
import com.example.tatneft_quest.Variables.Companion.fragmentList
import com.example.tatneft_quest.Variables.Companion.menuList
import com.example.tatneft_quest.firstActivity.AuthorizationActivity
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import java.util.*

class AppDrawer(private val activity: AppCompatActivity, private val toolbar: Toolbar) {
    private lateinit var mDrawer: Drawer
    private lateinit var mHeader: AccountHeader
    private lateinit var sharedPreferencesUser: SharedPreferences
    private lateinit var email: String
    private lateinit var surname: String
    private lateinit var name: String
    private lateinit var patranymic: String
    private lateinit var avatar: String
    private lateinit var bitmap: Bitmap
    private var pos: Int = 2

    private fun init() {
        sharedPreferencesUser = activity.getSharedPreferences(SAVE_DATA_USER, MODE_PRIVATE)
        email = sharedPreferencesUser.getString(SAVE_DATA_USER_EMAIL, "").toString()
        surname = sharedPreferencesUser.getString(SAVE_DATA_USER_SURNAME, "").toString()
        name = sharedPreferencesUser.getString(SAVE_DATA_USER_NAME, "").toString()
        patranymic = sharedPreferencesUser.getString(SAVE_DATA_USER_PATRONYMIC, "").toString()
        avatar = sharedPreferencesUser.getString(SAVE_DATA_USER_AVATAR, "").toString()
        val byteArray = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getDecoder().decode(avatar)
        } else {
            android.util.Base64.decode(avatar, android.util.Base64.DEFAULT)
        }
        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    fun drawerMenuFunc() {
        init()
        activity.setSupportActionBar(toolbar)
        mHeader = AccountHeaderBuilder()
            .withActivity(activity)
            .withHeaderBackground(R.drawable.background_header_menu)
            .addProfiles(
                ProfileDrawerItem().withName("$surname $name $patranymic")
                    .withEmail(email)
                    .withIcon(bitmap)
            ).build()
        mDrawer = DrawerBuilder()
            .withActivity(activity)
            .withToolbar(toolbar)
            .withActionBarDrawerToggle(true)
            .withSelectedItem(2)
            .withAccountHeader(mHeader)
            .withDelayOnDrawerClose(300)
            .addDrawerItems(
                PrimaryDrawerItem().withIdentifier(1)
                    .withIconTintingEnabled(true)
                    .withName("Профиль")
                    .withIcon(R.drawable.ic_person)
                    .withSelectable(true),
                PrimaryDrawerItem().withIdentifier(2)
                    .withIconTintingEnabled(true)
                    .withName("Путешествия")
                    .withIcon(R.drawable.ic_map)
                    .withSelectable(true),
                PrimaryDrawerItem().withIdentifier(3)
                    .withIconTintingEnabled(true)
                    .withName("Призы")
                    .withIcon(R.drawable.ic_celebration)
                    .withSelectable(true),
                PrimaryDrawerItem().withIdentifier(4)
                    .withIconTintingEnabled(true)
                    .withName("Вопросы")
                    .withIcon(R.drawable.ic_help)
                    .withSelectable(true),
                DividerDrawerItem(),
                PrimaryDrawerItem().withIdentifier(5)
                    .withIconTintingEnabled(true)
                    .withName("Помощь")
                    .withIcon(R.drawable.ic_support)
                    .withSelectable(true),
                PrimaryDrawerItem().withIdentifier(6)
                    .withIconTintingEnabled(true)
                    .withName("Настройки")
                    .withIcon(R.drawable.ic_settings)
                    .withSelectable(true),
                PrimaryDrawerItem().withIdentifier(7)
                    .withIconTintingEnabled(true)
                    .withName("Выход")
                    .withIcon(R.drawable.ic_logout)
                    .withSelectable(false)
            ).withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>,
                ): Boolean {
                    if (position != pos) {
                        pos = mDrawer.currentSelectedPosition
                        when (position) {
                            1 -> addFragment(ProfileFragment())
                            2 -> addFragment(fragment = if (fragmentList.isNotEmpty()) {
                                fragmentList[fragmentList.size - 1]
                            } else {
                                TravelFragment()
                            })
                            3 -> addFragment(PrizeFragment())
                            8 -> {
                                menuList.clear()
                                fragmentList.clear()
                                sharedPreferencesUser.edit()
                                    .putString(SAVE_DATA_USER_TOKEN, "false").apply()
                                activity.startActivity(Intent(activity.applicationContext,
                                    AuthorizationActivity::class.java))
                                activity.finish()

                            }
                            5 -> addFragment(HelpFragment())
                        }
                    }
                    return false
                }
            }).build()
    }

    fun setSelection() {
        mDrawer.setSelection(2)
    }

    private fun addFragment(fragment: Fragment) {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment).commit()
        if (menuList.isNotEmpty()) {
            menuList.clear()
        }
        menuList.add(fragment)
    }
}