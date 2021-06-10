package com.example.tatneft_quest

import androidx.fragment.app.Fragment
import com.example.tatneft_quest.models.ClusterMarkerPoints
import kotlin.properties.Delegates

open class Variables {
    companion object{
        //  Variables
        const val TAG = "check"
        val fragmentList: ArrayList<Fragment> = ArrayList()
        val menuList: ArrayList<Fragment> = ArrayList()
        var pointsSheet: ArrayList<ClusterMarkerPoints> = ArrayList()
        var LATITUDE by Delegates.notNull<Double>()
        var LONGITUDE by Delegates.notNull<Double>()
        var TIME by Delegates.notNull<Int>()

        //  Name settings
        const val SAVE_DATA_USER: String = "saveDataUser"
        const val SAVE_DATA_PROGRAM = "saveDataProgram"
        const val SAVE_DATA_POINTS = "saveDataPoints"

        //  Name variables SAVE_DATA_USER
        const val SAVE_DATA_USER_EMAIL = "email"
        const val SAVE_DATA_USER_PASSWORD = "password"
        const val SAVE_DATA_USER_SURNAME = "surname"
        const val SAVE_DATA_USER_NAME = "name"
        const val SAVE_DATA_USER_PATRONYMIC = "patronymic"
        const val SAVE_DATA_USER_BIRTHDAY = "birthday"
        const val SAVE_DATA_USER_GENDER = "gender"
        const val SAVE_DATA_USER_CITY = "city"
        const val SAVE_DATA_USER_NUMBER = "number"
        const val SAVE_DATA_USER_LOGIN = "login"
        const val SAVE_DATA_USER_AVATAR = "avatar"
        const val SAVE_DATA_USER_TOKEN = "token"
        const val SAVE_DATA_USER_SCORE = "score"

        //  Name variables SAVE_DATA_PROGRAM
        const val INTRO_OPEN = "introOpen"

        //  Name variables SAVE_DATA_POINTS
        const val LIST_DATA_POINTS = "listDataPoints"
        const val ACTIVE_QUEST = "activeQuest"
        const val TIME_QUEST = "timeQuest"
    }
}