package com.fizyshoppy.app.screen.otp

import com.fizyshoppy.AppController
import com.fizyshoppy.app.api.*
import com.fizyshoppy.app.model.CustomerRes
import com.fizyshoppy.app.model.ResendOtpRes
import retrofit2.Call
import retrofit2.Response

class OtpVerificationPresenter(view: OtpVerificationContract.View) : OtpVerificationContract.Presenter, IResponseInterface {
    var view: OtpVerificationContract.View? = null
    var iRequestInterface: IRequestInterface

    init {
        this.view = view
        this.iRequestInterface = ApiResponsePresenter(this)
    }

    override fun verifyOrt(otp: String, header: String) {
        iRequestInterface.callApi(AppController.service.verifyOtp(header, ApiRequestParam.verifyOtp(otp)), ApiRequestTypes.OTP)
    }

    override fun resendOtp(name: String, isEmailRNumber: Boolean) {
        iRequestInterface.callApi(AppController.service.resendOtp(ApiRequestParam.resendOtp(name, isEmailRNumber)), ApiRequestTypes.RESEND_OTP)
    }

    override fun <T> onResponseSuccess(call: Call<T>, response: Response<T>, reqType: String) {
        view?.hideLoader()
        if (response.isSuccessful) {
            when (reqType) {
                ApiRequestTypes.OTP -> {
                    view?.setOtpRes(response.body() as CustomerRes)
                }

                ApiRequestTypes.RESEND_OTP ->
                    view?.showResendOtpRes(response.body() as ResendOtpRes)
            }
        }
    }

    override fun <T> onResponseFailure(call: Call<T>, responseError: Throwable, reqType: String) {
        view?.hideLoader()
    }
}