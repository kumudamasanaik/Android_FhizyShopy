package com.fizyshoppy.app.api

import android.content.Context
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.constants.Constants.Companion.ANDROID
import com.fizyshoppy.app.constants.Constants.Companion.DEVICE_TYPE
import com.fizyshoppy.app.util.CommonUtil
import com.fizyshoppy.app.util.MyLogUtils
import com.google.gson.JsonArray
import com.google.gson.JsonObject

object ApiRequestParam {
    private lateinit var respParamObj: JsonObject
    private lateinit var respParamArrey: JsonArray
    const val TAG: String = "ApiRequestParam"

    private fun addCommonParameter(respParamObj: JsonObject) {
        respParamObj.addProperty(Constants.PINCODE, CommonUtil.getPincode()) /*TO DO PIN CODE IS HARD CODED */
    }

    private fun addSession(jsonObject: JsonObject) {
        jsonObject.addProperty(Constants.SESSION, CommonUtil.getSession())
    }

    private fun addCustomerID(jsonObject: JsonObject) {
        if (CommonUtil.isUserLogin() && CommonUtil.getCustomerId() != null) {
            jsonObject.addProperty(Constants._ID, CommonUtil.getCustomerId())
        }
    }

    fun login(name: String, pas: String): JsonObject {
        respParamObj = JsonObject()
        respParamObj.apply {
            addProperty(Constants.name, name)
            addProperty(Constants.pass, pas)
        }
        return respParamObj
    }

    fun verifyOtp(otp: String): JsonObject {
        respParamObj = JsonObject()
        respParamObj.apply {
            addProperty(Constants.OTP, otp)
        }
        return respParamObj
    }

    fun forgotPass(emailOrMoble: String, isemail: Boolean): JsonObject {
        try {
            respParamObj = JsonObject()
            respParamObj.addProperty(if (isemail) Constants.EMAIL else Constants.MOBILE, emailOrMoble)
        } catch (exp: Exception) {
            MyLogUtils.e(TAG, exp.message!!)
        }
        return respParamObj
    }

    fun resendOtp(emailOrMoble: String, isemail: Boolean): JsonObject {
        try {
            respParamObj = JsonObject()
            respParamObj.addProperty(if (isemail) Constants.EMAIL else Constants.MOBILE, emailOrMoble)
        } catch (exp: Exception) {
            MyLogUtils.e(TAG, exp.message!!)
        }
        return respParamObj
    }

    /*Home*/
    fun getHomeDataPairs(context: Context): JsonObject {
        try {
            respParamObj = JsonObject()
            respParamObj.addProperty(DEVICE_TYPE, ANDROID)
            addCustomerID(respParamObj)
            addSession(respParamObj)
            addCommonParameter(respParamObj)
        } catch (exp: Exception) {
            MyLogUtils.e(TAG, exp.message!!)
        }
        return respParamObj
    }

    /*Fecth cart input */
    fun fetchCart(): JsonObject {
        try {
            respParamObj = JsonObject()
            addCustomerID(respParamObj)
            respParamObj.addProperty(Constants.OP, Constants.CART_GET)
            addSession(respParamObj)
            addCommonParameter(respParamObj)

        } catch (exp: Exception) {
            MyLogUtils.e(TAG, exp.message!!)
        }
        return respParamObj
    }

    /*MODIFY WISH LIST*/
    fun modifyWishList(type: String, productId: String): JsonObject {
        try {
            respParamObj = JsonObject()
            respParamObj.addProperty(Constants.ID_PRODUCT, productId)
            respParamObj.addProperty(Constants.OP, type)
            addSession(respParamObj)
            addCustomerID(respParamObj)
        } catch (exp: Exception) {
            MyLogUtils.e(TAG, exp.message!!)
        }
        return respParamObj
    }


    /*PRODUCT LIST */
    fun getProductListPairs(productId: String): JsonObject {
        try {
            respParamObj = JsonObject()
            respParamObj.addProperty(Constants.ID_PRODUCT, productId)
        } catch (exp: Exception) {
            MyLogUtils.e(TAG, exp.message!!)
        }
        return respParamObj
    }

    /*SIZE CHART*/

    fun getSizeChartPairs(): JsonObject {
        try {
            respParamObj = JsonObject()
            addSession(respParamObj)
            addCustomerID(respParamObj)
        } catch (exp: Exception) {
            MyLogUtils.e(TAG, exp.message!!)
        }
        return respParamObj

    }

    /* SUB CATEGORY API CALL*/
    fun subCategoryRequest(id: String): JsonObject {
        try {
            respParamObj = JsonObject()
            respParamObj.addProperty(Constants.PARENT, id)
        } catch (exp: Exception) {
            MyLogUtils.e(TAG, exp.message!!)
        }
        return respParamObj
    }


    /* SUB CATEGORY API CALL*/
    fun getChildCategoryData(id: String): JsonObject {
        try {
            respParamObj = JsonObject()
            respParamObj.addProperty(Constants.CATEGORY_ID, id)
        } catch (exp: Exception) {
            MyLogUtils.e(TAG, exp.message!!)
        }
        return respParamObj
    }

    /** about us, contact us and privacy policy api request parameter*/

    fun aboutUsRequest(requestType: String): JsonObject {
        try {
            respParamObj = JsonObject()
            respParamObj.addProperty(Constants.TYPE, requestType)
        } catch (exp: Exception) {
            MyLogUtils.e(TAG, exp.message!!)
        }
        return respParamObj
    }

    fun notificationRequest(requestType: String): JsonObject {
        try {
            respParamObj = JsonObject()
            respParamObj.addProperty(Constants.NOTIFICATION, requestType)
        } catch (exp: Exception) {
            MyLogUtils.e(TAG, exp.message!!)
        }
        return respParamObj
    }   //  TODO(notification api request parameter)

    fun myAccountRequest(requestType: String): JsonObject {
        try {
            respParamObj = JsonObject()
            respParamObj.addProperty(Constants.MY_ACCOUNT, requestType)
        } catch (exp: Exception) {
            MyLogUtils.e(TAG, exp.message!!)
        }
        return respParamObj
    }

    fun purchaseGiftCardRequest(requestType: String): JsonObject {
        try {
            respParamObj = JsonObject()
            respParamObj.addProperty(Constants.PURCHASE_GIFT_CARD, requestType)
        } catch (exp: Exception) {
            MyLogUtils.e(TAG, exp.message!!)
        }
        return respParamObj
    }   //  TODO(purchasegiftcard api request parameter

    fun productDetailRequest(requestType: String): JsonObject {
        try {
            respParamObj = JsonObject()
            respParamObj.addProperty(Constants.PURCHASE_GIFT_CARD, requestType)
        } catch (exp: Exception) {
            MyLogUtils.e(TAG, exp.message!!)
        }
        return respParamObj
//        TODO (product detail request parameter)
    }

    fun addWishListRequest(requestType: String): JsonObject {

        try {

            respParamObj = JsonObject()
            respParamObj.addProperty(Constants.WISH_LIST, requestType)

        } catch (exp: Exception) {
            MyLogUtils.e(TAG, exp.message!!)
        }

        return respParamObj
        //        TODO (add to wish list)
    }

    fun deleteFromWishList(requestType: String): JsonObject {
        return respParamObj
        //        TODO (delete from wish list)
    }

    fun getWishList(requestType: String): JsonObject {
        return respParamObj
        //        TODO (get wish list)
    }

}