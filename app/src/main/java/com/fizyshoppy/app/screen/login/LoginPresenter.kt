package com.fizyshoppy.app.screen.login

import com.fizyshoppy.AppController
import com.fizyshoppy.app.api.ApiRequestParam
import com.fizyshoppy.app.api.ApiRequestTypes
import com.fizyshoppy.app.api.ApiResponsePresenter
import com.fizyshoppy.app.api.IResponseInterface
import com.fizyshoppy.app.model.CustomerRes
import retrofit2.Call
import retrofit2.Response


class LoginPresenter(view: LoginContract.View) : LoginContract.Presenter, IResponseInterface {


    private var iResponseInterface: ApiResponsePresenter
    var view: LoginContract.View?

    init {
        this.view = view
        this.iResponseInterface = ApiResponsePresenter(this)
    }

    override fun validateLogin(name: String, pass: String): Boolean {
        if (!name.isNotBlank()) {
            view?.invalidEmailPhone()
            return false
        }

        if (!pass.isNotBlank()) {
            view?.invalidPass()
            return false
        }

        return true
    }

    override fun doLogin(name: String, pass: String) {
        iResponseInterface.callApi(AppController.service.doLogin(ApiRequestParam.login(name, pass)), ApiRequestTypes.lOGIN)
    }

    override fun forgotPasswrd(name: String, isEmailRNumber: Boolean) {
        iResponseInterface.callApi(AppController.service.forgotPasss(ApiRequestParam.forgotPass(name, isEmailRNumber)), ApiRequestTypes.FORGOT_PASSWORD)
    }

    override fun <T> onResponseSuccess(call: Call<T>, response: Response<T>, reqType: String) {
        view?.hideLoader()
        if (response.isSuccessful) {
            when (reqType) {
                ApiRequestTypes.lOGIN ->
                    view?.showLoginRes(response.body() as CustomerRes)

                ApiRequestTypes.FORGOT_PASSWORD ->
                    view?.showForgotpassRes(response.body() as CustomerRes)
            }
        }

    }

    override fun <T> onResponseFailure(call: Call<T>, responseError: Throwable, reqType: String) {
        view?.hideLoader()
        view?.showMsg(responseError.message)
    }
}