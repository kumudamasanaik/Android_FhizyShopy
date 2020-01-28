package com.fizyshoppy.app.screen.otp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.telephony.SmsMessage
import android.text.SpannableStringBuilder
import android.view.KeyEvent
import android.widget.TextView
import com.fizyshoppy.app.util.hideProgressLoader
import com.fizyshoppy.app.util.showProgressLoader
import com.fizyshoppy.app.R
import com.fizyshoppy.app.R.attr.otp
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.model.CustomerRes
import com.fizyshoppy.app.model.ResendOtpRes
import com.fizyshoppy.app.screen.home.HomeActivity
import com.fizyshoppy.app.util.CommonUtil
import com.fizyshoppy.app.util.CommonUtil.Companion.getAuthorizationKey
import com.fizyshoppy.app.util.MyLogUtils
import com.fizyshoppy.app.util.RealTimePermission
import com.fizyshoppy.app.util.Validation
import kotlinx.android.synthetic.main.activity_otp_verification.*
import showSnackBar


class OtpVerificationActivity : AppCompatActivity(), OtpVerificationContract.View, TextView.OnEditorActionListener {

    lateinit var presenter: OtpVerificationPresenter

    lateinit var resendOtpRes: ResendOtpRes

    private lateinit var mContext: Context
    private var otpReceiver: OtpReceiver? = null
    private var intentFilter: IntentFilter? = null

    private val TAG = "OtpActivity"

    lateinit var mobile: String
    lateinit var source: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)
        mContext = this
        otpReceiver = OtpReceiver()
        intentFilter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        init()
    }

    override fun init() {
        if (!RealTimePermission.checkPermissionSMS(this)) {
            RealTimePermission.requestSMSPermission(this, RealTimePermission.SMS_PERMISSION_REQUEST_CODE)
        }

//        RealTimePermission.requestSMSPermission(this, RealTimePermission.SMS_PERMISSION_REQUEST_CODE)
        presenter = OtpVerificationPresenter(this)
        btn_verify_otp.setOnClickListener { verifyOtp() }

        tv_resend_otp.setOnClickListener { resendOtp() }
    }

    private fun resendOtp() {


        if (Validation.isValidString(CommonUtil.getAuthorizationKey())) {
            showLoader()
            presenter.resendOtp(CommonUtil.getAuthorizationKey(), true)
        }
//        showLoader()
//        presenter.resendOtp(CommonUtil.getAuthorizationKey(), true)
    }

    override fun onResume() {
        super.onResume()
        if (RealTimePermission.checkPermissionSMS(mContext))
            this.registerReceiver(otpReceiver, intentFilter)

    }

    override fun onDestroy() {
        super.onDestroy()

        if (otpReceiver != null)
            unregisterReceiver(otpReceiver)
    }


    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        return false
    }

    override fun verifyOtp() {
        if (!et_otp_entry.text.toString().isNullOrBlank()) {
            showLoader()
            presenter.verifyOrt(header = getAuthorizationKey(), otp = et_otp_entry.text.toString())
        } else
            showMsg(getString(R.string.err_please_enter_otp))
    }

    override fun setOtpRes(res: CustomerRes) {
        if (Validation.isValidStatus(res)) {
            showMsg(res.message)

            if (Validation.isValidObject(res.result.get(0)))
                CommonUtil.setCustomerData(res.result.get(0))
            CommonUtil.setIsLoggedIn(true)
            setNavigation()
        } else if (res.message!!.contentEquals(Constants.SUCCESS))
            showMsg(res.message)
        else
            showMsg(null)
    }

    private fun setNavigation() {
        var intent = Intent(this, HomeActivity::class.java)
        intent.apply {
            putExtra(Constants.SOURCE, Constants.OTP_ACTIVITY)
        }
        startActivity(intent)
    }

    override fun showResendOtpRes(res: ResendOtpRes) {
        if (Validation.isValidStatus(res)) {
            resendOtpRes = res

            startActivity(Intent(this@OtpVerificationActivity, HomeActivity::class.java))

        } else
            showMsg(res.message ?: getString(R.string.error_something_wrong))
    }


    override fun showMsg(msg: String?) {
        layout_otp.showSnackBar(msg ?: getString(R.string.err_some_went_wrong))
    }

    override fun showLoader() {
        showProgressLoader()
    }

    override fun hideLoader() {
        hideProgressLoader()
    }

    override fun showViewState(state: Int) {

    }

    /** OTP */
    internal inner class OtpReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {

            MyLogUtils.i("Otpreceiver", "onReceive called")
            if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
                val bundle = intent.extras
                try {
                    if (bundle != null) {
                        val pdusObj = bundle.get("pdus") as Array<Any>
                        for (i in pdusObj.indices) {
                            val currentMessage = SmsMessage.createFromPdu(pdusObj[i] as ByteArray)
                            val phoneNumber = currentMessage.displayOriginatingAddress
                            var otpbody = currentMessage.displayMessageBody//.split(":")[1];
                            otpbody = otpbody.substring(otpbody.length - 4, otpbody.length)

//                            if (otpbody.contains("Fizyshoppy"))
                            et_otp_entry.text = SpannableStringBuilder(otpbody)
                            verifyOtp()
                            MyLogUtils.i("SmsReceiver", intent.getStringExtra(Constants.OTP))
                            MyLogUtils.i("SmsReceiver", "senderNum: " + phoneNumber + "; message: " + otp + " ; Full Message " + currentMessage.displayMessageBody)
                        }
                    }
                } catch (e: Exception) {
                    MyLogUtils.e("SmsReceiver", "Exception smsReceiver$e")
                }
            }
        }
    }
}
