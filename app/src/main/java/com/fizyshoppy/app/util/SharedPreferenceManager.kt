package com.fizyshoppy.app.util

import android.content.Context
import android.content.SharedPreferences
import com.fizyshoppy.AppController

class SharedPreferenceManager {

    enum class VALUE_TYPE {
        BOOLEAN, INTEGER, STRING, FLOAT, LONG
    }

    companion object {
        val PREFERENCE_NAME = "app_pref"
        val IS_LOGED_IN = "IS_LOGED_IN"
        val SCREEN_WIDTH = "SCREEN_WIDTH"
        val SCREEN_HEIGHT = "SCREEN_HEIGHT"
        val USER_DATA = "USER_DATA"
        val USER_ID = "USER_ID"
        val PHONE = "PHONE"
        val TOKEN = "TOKEN"
        val BOARD_ID = "BOARD_ID"
        val CLASS_ID = "CLASS_ID"
        val CLASS_NAME = "CLASS_NAME"
        val BOARD_NAME = "BOARD_NAME"
        val NAME = "NAME"
        val SUBSCRIPTION_ID = "SUBSCRIPTION_ID"
        val SUBSCRIPTION_NAME = "SUBSCRIPTION_NAME"
        val SUBSCRIPTION_VALIDITY = "SUBSCRIPTION_VALIDITY"
        val SUBSCRIPTION_PRICE = "SUBSCRIPTION_PRICE"
        val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
        val MAIN_CAT_LIST_DATA = "MAIN_CAT_LIST_DATA"


        fun clearPreferences() {
            getPrefs().edit().clear().apply()
        }

        private fun getPrefs(): SharedPreferences {
            return AppController.context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        }



        fun setPrefVal(key: String, value: Any, vType: VALUE_TYPE) {
            when (vType) {
                VALUE_TYPE.BOOLEAN -> getPrefs().edit().putBoolean(key, value as Boolean).apply()
                VALUE_TYPE.INTEGER -> getPrefs().edit().putInt(key, value as Int).apply()
                VALUE_TYPE.STRING -> getPrefs().edit().putString(key, value as String).apply()
                VALUE_TYPE.FLOAT -> getPrefs().edit().putFloat(key, value as Float).apply()
                VALUE_TYPE.LONG -> getPrefs().edit().putLong(key, value as Long).apply()
                else -> {
                }
            }
        }

        fun getPrefVal(key: String, defValue: Any, vType: VALUE_TYPE): Any? {
            val `object`: Any?
            when (vType) {
                VALUE_TYPE.BOOLEAN -> `object` = getPrefs().getBoolean(key, defValue as Boolean)
                VALUE_TYPE.INTEGER -> `object` = getPrefs().getInt(key, defValue as Int)
                VALUE_TYPE.STRING -> `object` = getPrefs().getString(key, defValue as String)
                VALUE_TYPE.FLOAT -> `object` = getPrefs().getFloat(key, defValue as Float)
                VALUE_TYPE.LONG -> `object` = getPrefs().getLong(key, defValue as Long)
            }
            return `object`
        }

        fun isLogIn(): Boolean {
            return getPrefVal(IS_LOGED_IN, false, VALUE_TYPE.BOOLEAN) as Boolean
        }
    }

}