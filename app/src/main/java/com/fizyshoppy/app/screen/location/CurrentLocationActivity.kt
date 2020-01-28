package com.fizyshoppy.app.screen.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.support.v7.app.AppCompatActivity
import android.widget.ProgressBar
import android.widget.Toast
import com.fizyshoppy.app.R
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.model.AddresData
import com.fizyshoppy.app.screen.home.HomeActivity
import com.fizyshoppy.app.util.CommonUtil
import com.fizyshoppy.app.util.MyLogUtils
import com.fizyshoppy.app.util.RealTimePermission
import com.fizyshoppy.app.util.Validation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_current_location.*
import showSnackBar

class CurrentLocationActivity : AppCompatActivity() {
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var mLastLocation: Location? = null

    private var mAddressRequested: Boolean = false
    private var mAddressOutput: String? = null
    lateinit var userCurAddress: Address

    private var mResultReceiver: AddressResultReceiver? = null
    internal var context: Context? = null
    private var source: String? = ""
    private val TAG: String = "CurrentLocationActivity"
    private val ADDRESS_REQUESTED_KEY = "address-request-pending"
    private val LOCATION_ADDRESS_KEY = "location-address"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_location)
        context = this
        if (intent != null) {
            source = intent.extras!!.getString(Constants.SOURCE)
        }
        mResultReceiver = AddressResultReceiver(Handler())
        // Set defaults, then update using values stored in the Bundle.
        mAddressRequested = false
        mAddressOutput = ""
        updateValuesFromBundle(savedInstanceState)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        tv_set_loc_manually.setOnClickListener {
            performSetManualLoc()
        }

        layout_use_my_loc.setOnClickListener {
            fetchAddressButtonHandler()
        }


    }


    override fun onStart() {
        super.onStart()
        if (!RealTimePermission.checkPermissionLocation(context)) {
            RealTimePermission.requestLocationPermission(context, RealTimePermission.LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            getAddress()
        }
    }


    @SuppressLint("MissingPermission")
    private fun getAddress() {

        mFusedLocationClient.lastLocation.addOnSuccessListener(this, OnSuccessListener { location ->
            if (location == null) {
                MyLogUtils.e(TAG, "onSuccess:null")
                return@OnSuccessListener
            } else
                mLastLocation = location
            // Determine whether a Geocoder is available.
            if (!Geocoder.isPresent()) {
                view_content.showSnackBar(getString(R.string.no_geocoder_available))
                return@OnSuccessListener
            }

            // If the user pressed the fetch address button before we had the location,
            // this will be set to true indicating that we should kick off the intent
            // service after fetching the location.
            if (mAddressRequested) {
                startIntentService()
            }
        })
                .addOnFailureListener(this) { e -> MyLogUtils.w(TAG, "getLastLocation:onFailure", e) }


    }

     fun fetchAddressButtonHandler() {
        if (!RealTimePermission.checkPermissionLocation(context)) {
            RealTimePermission.requestLocationPermission(context, RealTimePermission.LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            if (mLastLocation != null) {
                startIntentService()
                return
            } else {
                getAddress()
            }
            // If we have not yet retrieved the user location, we process the user's request by setting
            // mAddressRequested to true. As far as the user is concerned, pressing the Fetch Address button
            // immediately kicks off the process of getting the address.
            mAddressRequested = true
        }
    }


    private fun performSetManualLoc() {
        Toast.makeText(this, "need to implement", Toast.LENGTH_LONG).show()
        /*val intent = Intent(this, AddAddressActivity::class.java)
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_CURRENT_LOC)
        startActivity(intent)
        finish()*/
    }


    private fun startIntentService() {
        // Create an intent for passing to the intent service responsible for fetching the address.
        val intent = Intent(this, FetchAddressIntentService::class.java)

        // Pass the userprofile receiver as an extra to the service.
        intent.putExtra(Constants.RECEIVER, mResultReceiver)
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation)

        progress_bar.visibility = ProgressBar.VISIBLE
        startService(intent)
    }


    private fun updateValuesFromBundle(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            // Check savedInstanceState to see if the address was previously requested.
            if (savedInstanceState.keySet().contains(ADDRESS_REQUESTED_KEY)) {
                mAddressRequested = savedInstanceState.getBoolean(ADDRESS_REQUESTED_KEY)
            }
            // Check savedInstanceState to see if the location address string was previously found
            // and stored in the Bundle. If it was found, display the address string in the UI.
            if (savedInstanceState.keySet().contains(LOCATION_ADDRESS_KEY)) {
                mAddressOutput = savedInstanceState.getString(LOCATION_ADDRESS_KEY)
                //displayAddressOutput();
            }
        }

    }


    private inner class AddressResultReceiver internal constructor(handler: Handler) : ResultReceiver(handler) {

        /**
         * Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
            progress_bar.visibility = ProgressBar.GONE
            // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY)
            userCurAddress = resultData.getParcelable(Constants.RESULT_CURRENT_LOC)
            if (Validation.isValidObject(userCurAddress))
                saveLocationResultAddress()
            displayAddressOutput()

            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                //showToast(getString(R.string.address_found));
            }

            // Reset. Enable the Fetch Address button and stop showing the progress bar.
            mAddressRequested = false
        }

        private fun displayAddressOutput() {
            view_content.showSnackBar(mAddressOutput!!)
        }

        private fun saveLocationResultAddress() {
            val mAdress = AddresData()
            mAdress.address1 = userCurAddress.getAddressLine(0)
            mAdress.city = userCurAddress.locality
            mAdress.state = userCurAddress.adminArea
            mAdress.country = userCurAddress.countryName
            mAdress.pincode = userCurAddress.postalCode

            CommonUtil.saveAddress(Gson().toJson(mAdress))
            navigateToHome()

        }

        private fun navigateToHome() {
            val intent = Intent(context, HomeActivity::class.java)
            intent.putExtra(Constants.SOURCE, Constants.SOURCE_CURRENT_LOC)
            startActivity(intent)
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            RealTimePermission.LOCATION_PERMISSION_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getAddress()
            }
        }
    }
}




