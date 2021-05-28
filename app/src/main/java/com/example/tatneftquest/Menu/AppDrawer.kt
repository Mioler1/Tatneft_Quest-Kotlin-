package com.example.tatneftquest.Menu

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.tatneftquest.R
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem

class AppDrawer(private val activity: AppCompatActivity, private val toolbar: Toolbar) {
    private lateinit var mDrawer: Drawer
    private lateinit var mHeader: AccountHeader
    private var pos: Int = 2

    fun drawerMenuFunc() {
        activity.setSupportActionBar(toolbar)
        mHeader = AccountHeaderBuilder()
            .withActivity(activity)
            .withHeaderBackground(R.drawable.background_header_menu)
            .addProfiles(
                ProfileDrawerItem().withName("Sergey Galdin")
                    .withEmail("veretennik-v@mail.ru")
                    .withIcon(R.drawable.ic_launcher_foreground)
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
                            2 -> addFragment(TravelFragment())
                        }
                    }
                    return false
                }
            }).build()
    }

    private fun addFragment(fragment: Fragment) {
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment).commit()
    }
}