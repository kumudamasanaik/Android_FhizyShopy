package com.fizyshoppy.app.screen.otp

import com.fizyshoppy.app.BasePresenter
import com.fizyshoppy.app.BaseView
import com.fizyshoppy.app.model.CustomerRes
import com.fizyshoppy.app.model.ResendOtpRes

interface OtpVerificationContract {

    interface View : BaseView {
        fun verifyOtp()
        fun setOtpRes(res:CustomerRes)
        fun showResendOtpRes(res: ResendOtpRes)
    }

    interface Presenter : BasePresenter<View> {
        fun verifyOrt(otp: String,header:String)
        fun resendOtp(name: String, isEmailRNumber: Boolean)
    }
}