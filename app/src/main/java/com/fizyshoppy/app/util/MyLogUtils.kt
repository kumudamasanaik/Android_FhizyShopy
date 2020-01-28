package com.fizyshoppy.app.util

import android.util.Log
import com.fizyshoppy.app.BuildConfig

class MyLogUtils {
    companion object {
        var isDebug = BuildConfig.DEBUG

        fun d(tag: String, msg: String) {
            if (isDebug) {
                if (Validation.isValidString(tag) && Validation.isValidString(msg))
                    Log.d(tag, msg)
            }
        }

        fun e(tag: String, msg: String) {
            if (isDebug) {
                if (Validation.isValidString(tag) && Validation.isValidString(msg))
                    Log.e(tag, msg)
            }
        }

        fun w(tag: String, msg: String, tr: Throwable) {
            if (isDebug) {
                if (Validation.isValidString(tag) && Validation.isValidString(msg))
                    Log.w(tag, msg, tr)
            }
        }

        fun i(tag: String, msg: String) {
            if (isDebug) {
                if (Validation.isValidString(tag) && Validation.isValidString(msg))
                    Log.i(tag, msg)
            }
        }
    }
}