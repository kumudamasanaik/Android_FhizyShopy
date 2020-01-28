package com.fizyshoppy.app.screen.location

import android.app.IntentService
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.ResultReceiver
import android.text.TextUtils
import android.util.Log
import com.fizyshoppy.app.R
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.util.MyLogUtils
import com.fizyshoppy.app.util.Validation
import java.io.IOException
import java.util.*


/**
 * Created by FuGenX-14 on 31-07-2018.
 */
class FetchAddressIntentService : IntentService("FetchAddressIntentService") {
    @Volatile
    var shouldContinue = true
    private val TAG = "FetchAddressIntentService"
    /**
     * The receiver where results are forwarded from this service.
     */
    private var mReceiver: ResultReceiver? = null


    override fun onHandleIntent(intent: Intent?) {
        var errorMessage = ""
        mReceiver = intent!!.getParcelableExtra(Constants.RECEIVER)
        // Check if receiver was properly registered.
        if (mReceiver == null) {
            MyLogUtils.d(TAG, "No receiver received. There is nowhere to send the results.")
            return
        }
        // Get the location passed to this service through an extra.
        val location = intent.getParcelableExtra<Location>(Constants.LOCATION_DATA_EXTRA)

        // Make sure that the location data was really sent over through an extra. If it wasn't,
        // send an error error message and return.
        if (location == null) {
            errorMessage = getString(R.string.no_location_data_provided)
            Log.wtf(TAG, errorMessage)
            deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage, null)
            return
        }

        // Errors could still arise from using the Geocoder (for example, if there is no
        // connectivity, or if the Geocoder is given illegal location data). Or, the Geocoder may
        // simply not have an address for a location. In all these cases, we communicate with the
        // receiver using a resultCode indicating failure. If an address is found, we use a
        // resultCode indicating success.

        // The Geocoder used in this sample. The Geocoder's responses are localized for the given
        // Locale, which represents a specific geographical or linguistic region. Locales are used
        // to alter the presentation of information such as numbers or dates to suit the conventions
        // in the region they describe.
        val geocoder = Geocoder(this, Locale.getDefault())

        // Address found using the Geocoder.
        var addresses: List<Address>? = null

        try {
            // Using getFromLocation() returns an array of Addresses for the area immediately
            // surrounding the given latitude and longitude. The results are a best guess and are
            // not guaranteed to be accurate.
            addresses = geocoder.getFromLocation(
                    location!!.getLatitude(),
                    location!!.getLongitude(),
                    // In this sample, we get just a single address.
                    1)
        } catch (ioException: IOException) {
            // Catch network or other I/O problems.
            errorMessage = getString(R.string.service_not_available)
            MyLogUtils.e(TAG, errorMessage)
        } catch (illegalArgumentException: IllegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = getString(R.string.invalid_lat_long_used)
            MyLogUtils.e(TAG, errorMessage + ". " +
                    "Latitude = " + location!!.getLatitude() +
                    ", Longitude = " + location!!.getLongitude())
        }


        // Handle case where no address was found.
        if (addresses == null || addresses.size == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = getString(R.string.no_address_found)
                MyLogUtils.e(TAG, errorMessage)
            }
            deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage, null)
        } else {
            val address = addresses[0]
            val addressFragments = ArrayList<String>()

            // Fetch the address lines using {@code getAddressLine},
            // join them, and send them to the thread. The {@link android.location.address}
            // class provides other options for fetching address details that you may prefer
            // to use. Here are some examples:
            // getLocality() ("Mountain View", for example)
            // getAdminArea() ("CA", for example)
            // getPostalCode() ("94043", for example)
            // getCountryCode() ("US", for example)
            // getCountryName() ("United States", for example)
            for (i in 0..address.maxAddressLineIndex) {
                addressFragments.add(address.getAddressLine(i))
            }
            MyLogUtils.i(TAG, getString(R.string.address_found))
            deliverResultToReceiver(Constants.SUCCESS_RESULT,
                    TextUtils.join(System.getProperty("line.separator"), addressFragments), address)
        }
    }

    /**
     * Sends a resultCode and message to the receiver.
     */
    private fun deliverResultToReceiver(resultCode: Int, message: String, address: Address?) {
        if (Validation.isValidObject(mReceiver)) {
            val bundle = Bundle()
            bundle.putString(Constants.RESULT_DATA_KEY, message)
            bundle.putParcelable(Constants.RESULT_CURRENT_LOC, address)
            mReceiver!!.send(resultCode, bundle)
        }
    }

}