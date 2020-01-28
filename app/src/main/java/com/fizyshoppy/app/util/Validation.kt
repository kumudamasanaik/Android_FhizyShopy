package com.fizyshoppy.app.util

import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.model.CommonRes

class Validation {
    companion object {
        fun isValidStatus(res: CommonRes?): Boolean {
            if (res != null && isValidString(res.status) && res.status!!.contentEquals(Constants.SUCCESS)) {
                if (isValidString(res.key)) {
                    CommonUtil.setAuthorizationKey(res.key)
                }
                if (isValidString(res.session)) {
                    CommonUtil.setSession(res.key)
                }

                return true;
            } else return false;

        }

        fun isValidObject(`object`: Any?): Boolean {
            return `object` != null
        }

        fun isValidString(string: String?): Boolean {
            return string != null && string.trim().isNotEmpty()
        }

        fun isValidList(list: List<*>?): Boolean {
            return list != null && list.isNotEmpty()
        }

        fun validateValue(value: String?): Boolean {
            return value != null && value.isNotEmpty()
        }

        fun isValidOtp(string: String?): Boolean {
            return string != null && string.trim().length == 4
        }

        fun isValidArrayList(list: Array<*>?): Boolean {
            return list != null && list.isNotEmpty()
        }

        fun isValidDigit(integer: Double): Boolean {
            return if (integer > 0) true else false
        }


    }
}