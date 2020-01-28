package com.fizyshoppy.app.screen.register

import android.util.Patterns
import com.fizyshoppy.AppController
import com.fizyshoppy.app.R
import com.fizyshoppy.app.api.ApiRequestTypes
import com.fizyshoppy.app.api.ApiResponsePresenter
import com.fizyshoppy.app.api.IResponseInterface
import com.fizyshoppy.app.model.CustomerRes
import com.fizyshoppy.app.model.payloadmodel.RegisterInput
import retrofit2.Call
import retrofit2.Response

class RegisterPresenter(view: RegisterContract.View?) : RegisterContract.Presenter, IResponseInterface {
    private var iResponseInterface: ApiResponsePresenter
    var view: RegisterContract.View?

    init {
        this.view = view
        this.iResponseInterface = ApiResponsePresenter(this)
    }

    override fun validate(input: RegisterInput): Boolean {
        if (input.name.isNullOrBlank()) {
            view?.showInValidFiledMsg(view?.getContext()?.getString(R.string.err_please_enter_valid_name)!!)
            return false
        }
        if (input.email.isNullOrBlank() || !(Patterns.EMAIL_ADDRESS.matcher(input.email).matches())) {
            view?.showInValidFiledMsg(view?.getContext()?.getString(R.string.err_please_enter_valid_email)!!)
            return false
        }
        if (input.mobile.isNullOrBlank() || !(input.mobile.length == 10)) {
            view?.showInValidFiledMsg(view?.getContext()?.getString(R.string.err_please_enter_valid_mobile)!!)
            return false
        }
        if (input.password.isNullOrBlank()) {
            view?.showInValidFiledMsg(view?.getContext()?.getString(R.string.err_please_enter_valid_password)!!)
            return false
        }
        if (input.confPassword.isNullOrBlank()) {
            view?.showInValidFiledMsg(view?.getContext()?.getString(R.string.err_please_enter_valid_conf_pass)!!)
            return false
        }

        if (!input.confPassword?.contentEquals(input.password)!!) {
            view?.showInValidFiledMsg(view?.getContext()?.getString(R.string.err_please_enter_mismatch_conf_pass)!!)
            return false
        }
        input.confPassword = null
        return true
    }

    override fun callRegister(input: RegisterInput) {
        iResponseInterface.callApi(AppController.service.doRegister(input), ApiRequestTypes.REGISTER)
    }

    override fun <T> onResponseSuccess(call: Call<T>, response: Response<T>, reqType: String) {
        view?.hideLoader()
        if (response.isSuccessful) {
            when (reqType) {
                ApiRequestTypes.REGISTER -> {
                    view?.setRegsiterRes(response.body() as CustomerRes)
                }
            }
        }
    }

    override fun <T> onResponseFailure(call: Call<T>, responseError: Throwable, reqType: String) {
        view?.hideLoader()
    }
}