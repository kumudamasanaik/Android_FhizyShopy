package com.fizyshoppy.app.util

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.provider.ContactsContract.ProviderStatus.STATUS
import android.support.v4.app.ActivityCompat.finishAffinity
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.fizyshoppy.AppController
import com.fizyshoppy.app.R
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.constants.Constants.Companion.AUTHORIZATION
import com.fizyshoppy.app.constants.Constants.Companion.CUSTOMER_ID
import com.fizyshoppy.app.constants.Constants.Companion.CUSTOMER_TYPE
import com.fizyshoppy.app.constants.Constants.Companion.EMAIL
import com.fizyshoppy.app.constants.Constants.Companion.EMAIL_VERIFIED
import com.fizyshoppy.app.constants.Constants.Companion.FACEBOOK_ID
import com.fizyshoppy.app.constants.Constants.Companion.GENDER
import com.fizyshoppy.app.constants.Constants.Companion.GOOGLE_ID
import com.fizyshoppy.app.constants.Constants.Companion.IS_LOGGED_IN
import com.fizyshoppy.app.constants.Constants.Companion.MOBILE
import com.fizyshoppy.app.constants.Constants.Companion.NAME
import com.fizyshoppy.app.constants.Constants.Companion.PIC
import com.fizyshoppy.app.constants.Constants.Companion.PINCODE
import com.fizyshoppy.app.constants.Constants.Companion.REFERRAL_CODE
import com.fizyshoppy.app.constants.Constants.Companion.REFERRAL_USED
import com.fizyshoppy.app.constants.Constants.Companion.REFERRED_BY
import com.fizyshoppy.app.constants.Constants.Companion.SESSION
import com.fizyshoppy.app.constants.Constants.Companion.SESSION_TIME
import com.fizyshoppy.app.model.AddresData
import com.fizyshoppy.app.model.Category
import com.fizyshoppy.app.model.CustomerData
import com.fizyshoppy.app.screen.login.LoginActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.custome_toast.view.*
import java.text.SimpleDateFormat
import java.util.*


class CommonUtil {
    companion object {
        private var myProgressDialog: ProgressDialog? = null
        fun showLoading(mContext: Context, cancelable: Boolean) {
            try {
                hideLoading()
                myProgressDialog = ProgressDialog(mContext, R.style.AppTheme_Loading_Dialog)
                myProgressDialog!!.setMessage(mContext.getString(R.string.msg_please_wait))

                myProgressDialog!!.setCancelable(cancelable)
                myProgressDialog!!.setOnCancelListener { dialog ->
                    dialog.dismiss()
                }
                myProgressDialog!!.show()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }

        fun hideLoading() {
            if (myProgressDialog != null && myProgressDialog!!.isShowing)
                myProgressDialog!!.dismiss()
            myProgressDialog = null
        }


        fun showToastMsg(mContext: Context?, msg: String?, length: Int) {
            if (mContext != null) {
                val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val layout = inflater.inflate(R.layout.custome_toast, null)

                if (msg != null) {
                    layout.tv_custom_toast.text = msg
                } else layout.tv_custom_toast.text = mContext.getString(R.string.error_something_wrong)

                val toast = Toast(mContext)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.duration = length
                toast.view = layout
                toast.show()
            }
        }

        private fun generateSession(): String {

            try {
                val chars = "abcdefghijklmnopqrstuvwxyz".toCharArray()
                val sb = StringBuilder()
                val random = Random()
                for (i in 0..15) {
                    val c = chars[random.nextInt(chars.size)]
                    sb.append(c)
                }
                val randomString = sb.toString() + "_" + SimpleDateFormat("ddMMyyyyhhmmssSSS").format(java.util.Date())
                setSession(randomString)
                return randomString
            } catch (ex: Exception) {
                ex.printStackTrace()
                return ""
            }

        }

        fun getSession(): String {
            val session = SharedPreferenceManager.getPrefVal(SESSION, "", SharedPreferenceManager.VALUE_TYPE.STRING) as String

            if (!Validation.isValidString(session)) {
                MyLogUtils.i("SESSION : ", "session is null ")
                return generateSession()
            }

            return session
        }


        fun setAuthorizationKey(authorizationKey: String?) {
            MyLogUtils.i("AuthorizationKey", "AuthorizationKey $authorizationKey")
            SharedPreferenceManager.setPrefVal(AUTHORIZATION, authorizationKey!!, SharedPreferenceManager.VALUE_TYPE.STRING)
        }

        fun getAuthorizationKey(): String {
            return SharedPreferenceManager.getPrefVal(AUTHORIZATION, "", SharedPreferenceManager.VALUE_TYPE.STRING) as String
        }

        fun setSession(session: String?) {
            MyLogUtils.i("SESSION", "SetSession $session")
            SharedPreferenceManager.setPrefVal(SESSION, session!!, SharedPreferenceManager.VALUE_TYPE.STRING)
            SharedPreferenceManager.setPrefVal(SESSION_TIME, System.currentTimeMillis(), SharedPreferenceManager.VALUE_TYPE.LONG)
        }


        fun setIsLoggedIn(isLoggedIn: Boolean) {
            SharedPreferenceManager.setPrefVal(IS_LOGGED_IN, isLoggedIn, SharedPreferenceManager.VALUE_TYPE.BOOLEAN)
        }

        fun isUserLogin(): Boolean {
            return SharedPreferenceManager.getPrefVal(IS_LOGGED_IN, false, SharedPreferenceManager.VALUE_TYPE.BOOLEAN) as Boolean
        }

        fun setCustomerData(customerData: CustomerData) {
            try {
                if (Validation.validateValue(customerData._id)) {
                    SharedPreferenceManager.setPrefVal(CUSTOMER_ID, customerData._id, SharedPreferenceManager.VALUE_TYPE.STRING)
                    SharedPreferenceManager.setPrefVal(NAME, customerData.name, SharedPreferenceManager.VALUE_TYPE.STRING)
                    SharedPreferenceManager.setPrefVal(EMAIL, customerData.email, SharedPreferenceManager.VALUE_TYPE.STRING)
                    SharedPreferenceManager.setPrefVal(MOBILE, customerData.mobile, SharedPreferenceManager.VALUE_TYPE.STRING)
                    SharedPreferenceManager.setPrefVal(FACEBOOK_ID, customerData.facebook_id, SharedPreferenceManager.VALUE_TYPE.STRING)
                    SharedPreferenceManager.setPrefVal(GOOGLE_ID, customerData.google_id, SharedPreferenceManager.VALUE_TYPE.STRING)
                    SharedPreferenceManager.setPrefVal(CUSTOMER_TYPE, customerData.customer_type, SharedPreferenceManager.VALUE_TYPE.STRING)
                    SharedPreferenceManager.setPrefVal(GENDER, customerData.gender, SharedPreferenceManager.VALUE_TYPE.STRING)
                    SharedPreferenceManager.setPrefVal(PIC, customerData.pic, SharedPreferenceManager.VALUE_TYPE.STRING)
                    SharedPreferenceManager.setPrefVal(STATUS, customerData.status, SharedPreferenceManager.VALUE_TYPE.STRING)
                    SharedPreferenceManager.setPrefVal(EMAIL_VERIFIED, customerData.email_verified, SharedPreferenceManager.VALUE_TYPE.STRING)
                    SharedPreferenceManager.setPrefVal(REFERRAL_CODE, customerData.referral_code, SharedPreferenceManager.VALUE_TYPE.STRING)
                    SharedPreferenceManager.setPrefVal(REFERRED_BY, customerData.referred_by, SharedPreferenceManager.VALUE_TYPE.STRING)
                    SharedPreferenceManager.setPrefVal(REFERRAL_USED, customerData.referral_used, SharedPreferenceManager.VALUE_TYPE.STRING)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }

        fun getCustomerId(): String {
            return SharedPreferenceManager.getPrefVal(CUSTOMER_ID, "0", SharedPreferenceManager.VALUE_TYPE.STRING) as String

        }

        fun getCustomerName(): String {
            return SharedPreferenceManager.getPrefVal(NAME, "", SharedPreferenceManager.VALUE_TYPE.STRING) as String

        }


        fun setPincode(pincode: String) {
            MyLogUtils.i("PINCODE", "Set Pincode $pincode")
            SharedPreferenceManager.setPrefVal(PINCODE, pincode, SharedPreferenceManager.VALUE_TYPE.STRING)
        }

        fun getPincode(): String {
            return SharedPreferenceManager.getPrefVal(PINCODE, "", SharedPreferenceManager.VALUE_TYPE.STRING) as String

        }

        fun saveAddress(address: String) {
            MyLogUtils.i("Address", "Address $address")
            SharedPreferenceManager.setPrefVal(Constants.SAVE_ADDRES, address, SharedPreferenceManager.VALUE_TYPE.STRING)

        }

        fun getSavedAddress(): AddresData? {
            val data = SharedPreferenceManager.getPrefVal(Constants.SAVE_ADDRES, "", SharedPreferenceManager.VALUE_TYPE.STRING) as String
            if (Validation.isValidString(data))
                return Gson().fromJson(data, AddresData::class.java)
            else
                return null
        }

        fun startActivity(mContext: Context, activity: Class<*>, bundle: Bundle) {
            val move = Intent(mContext, activity)
            move.putExtras(bundle)
            mContext.startActivity(move)
        }


        fun checkLoginStatus(context: Context?, msg: String): Boolean {
            var context = context
            if (context == null)
                context = AppController.context
            if (isUserLogin())
                return true
            else {
                try {
                    showToastMsg(context, context.getString(R.string.user_not_login), 1)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return false
            }
        }


        fun getItemDecoration(context: Context, linearLayoutManager: LinearLayoutManager): RecyclerView.ItemDecoration {
            val dividerItemDecoration = DividerItemDecoration(context, linearLayoutManager.orientation)
            dividerItemDecoration.setDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.bg_rv_divider, null)!!)
            return dividerItemDecoration
        }


        fun getStrWithRsSym(context: Context, price: Double): String {
            return context.getString(R.string.Rs) + price.toString()
        }

        fun strikeThrText(textView: TextView?) {
            if (textView != null)
                textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }


        /** move clicked item to center on recyclerview*/
        fun moveRecyclerViewItemToCenter(context: Context, pos: Int, recyclerView: RecyclerView) {
            recyclerView.smoothScrollToPosition(pos)
            val linearLayoutManager = LinearLayoutManager(context)
            val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
            val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
            val centerPosition = (firstVisibleItemPosition + lastVisibleItemPosition) / 2
            if (pos > centerPosition) {
                recyclerView.smoothScrollToPosition(pos + 1)
            } else if (pos < centerPosition) {
                recyclerView.smoothScrollToPosition(pos - 1)
            }
        }

        fun saveMainCatList(categories: List<Category>) {
            val gson = Gson()
            val json = gson.toJson(categories).toString()
            SharedPreferenceManager.setPrefVal(SharedPreferenceManager.MAIN_CAT_LIST_DATA, json, SharedPreferenceManager.VALUE_TYPE.STRING)
        }

        fun getMainCatListData(): List<Category> {
            val gson = Gson()
            val json = SharedPreferenceManager.getPrefVal(SharedPreferenceManager.MAIN_CAT_LIST_DATA, "", SharedPreferenceManager.VALUE_TYPE.STRING) as String
            if (Validation.isValidString(json)) {
                val type = object : TypeToken<List<Category>>() {}.type
                return gson.fromJson<List<Category>>(json, type)
            }
            return emptyList()

        }

        fun showDialog(context: Context, msg: String = "Are you Sure?", type: String) {
            val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        when(type){
                            Constants.FOR_LOG_OUT ->{
                                SharedPreferenceManager.clearPreferences()
                                CommonUtil.startActivity(context!!, LoginActivity::class.java, bundle = Bundle())
                            }
                            Constants.FOR_EXIT ->{
                                System.exit(0)
                            }
                        }
                    }

                    DialogInterface.BUTTON_NEGATIVE -> {
                        dialog.dismiss()
                    }
                }
            }

            val builder = AlertDialog.Builder(context)
            builder.setMessage(msg).setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show()
        }
    }
}