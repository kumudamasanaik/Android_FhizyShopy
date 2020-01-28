package com.fizyshoppy.app.util


import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.fizyshoppy.app.R
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes

class RealTimePermission {
    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
        const val STORAGE_PERMISSION_REQUEST_CODE = 2
        const val SMS_PERMISSION_REQUEST_CODE = 3
        const val TAG: String = "RealTimePermission"

        fun checkPermissionSMS(context: Context): Boolean {
            val smsPer = arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS)
            for (item in smsPer) {
                if (ContextCompat.checkSelfPermission(context, item) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
            return true
        }

        fun requestSMSPermission(context: Context, PERMISSION_REQUEST_CODE: Int) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context as AppCompatActivity, Manifest.permission.READ_SMS)) {
                val myDialog = Dialog(context, R.style.CustomDialogThemeLightBg)
                myDialog.setCanceledOnTouchOutside(true)
                myDialog.setContentView(R.layout.access_permission_dialog)
                (myDialog.findViewById(R.id.dialog_title) as TextView).text = context.getString(R.string.read_sms)
                (myDialog.findViewById(R.id.dialog_text) as TextView).text = context.getString(R.string.access_sms_msg)
                (myDialog.findViewById(R.id.dialog_btn_yes) as Button).setOnClickListener(View.OnClickListener {
                    ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS), PERMISSION_REQUEST_CODE)
                    myDialog.dismiss()
                })

                (myDialog.findViewById(R.id.dialog_btn_no) as Button).setOnClickListener { myDialog.dismiss() }
                myDialog.show()
            } else {
                ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS), PERMISSION_REQUEST_CODE)
            }
        }

        fun checkPermissionLocation(context: Context?): Boolean {
            if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                return false
            return true
        }


        fun requestLocationPermission(context: Context?, PERMISSION_REQUEST_CODE: Int) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(context as AppCompatActivity, Manifest.permission.ACCESS_FINE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                val myDialog = Dialog(context, R.style.CustomDialogThemeLightBg)
                myDialog.setCanceledOnTouchOutside(true)
                myDialog.setContentView(R.layout.access_permission_dialog)
                (myDialog.findViewById(R.id.dialog_title) as TextView).text = "Access your Location"
                (myDialog.findViewById(R.id.dialog_text) as TextView).text = context.getString(R.string.access_location_msg)
                val btnYes = myDialog.findViewById(R.id.dialog_btn_yes) as Button
                val btnNo = myDialog.findViewById(R.id.dialog_btn_no) as Button

                btnYes.setOnClickListener {
                    ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
                    myDialog.dismiss()
                }

                btnNo.setOnClickListener { myDialog.dismiss() }
                myDialog.show()
            } else {
                ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
            }

        }

        fun displayLocationSettingsRequest(context: Context) {
            val googleApiClient = GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API).build() as GoogleApiClient
            googleApiClient.connect()

            val locationRequest = LocationRequest.create() as LocationRequest
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = 10000
            locationRequest.fastestInterval = (10000 / 2).toLong()

            val builder: LocationSettingsRequest.Builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            builder.setAlwaysShow(true)

            val result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())

            result.setResultCallback { result ->
                val status = result.status
                when (status.statusCode) {
                    LocationSettingsStatusCodes.SUCCESS -> MyLogUtils.i(TAG, "All location settings are satisfied.")
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        MyLogUtils.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ")
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(context as Activity, LOCATION_PERMISSION_REQUEST_CODE)
                        } catch (e: IntentSender.SendIntentException) {
                            MyLogUtils.i(TAG, "PendingIntent unable to execute request.")
                        }

                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> MyLogUtils.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.")
                }
            }
        }
    }

}