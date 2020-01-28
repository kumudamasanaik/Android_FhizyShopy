package com.fizyshoppy.app.screen.login

import com.fizyshoppy.app.BasePresenter
import com.fizyshoppy.app.BaseView
import com.fizyshoppy.app.model.CustomerRes

interface LoginContract {

    interface View : BaseView {
        fun doLogin()
        fun startSignUpScreen()
        fun startOtpScreen()
        fun invalidEmailPhone()
        fun invalidPass()
        fun showForgotpassRes(res: CustomerRes)
        fun showLoginRes(res: CustomerRes)
    }

    interface Presenter : BasePresenter<View> {
        fun validateLogin(name: String, pass: String): Boolean
        fun doLogin(name: String, pass: String)
        fun forgotPasswrd(name: String, isEmailRNumber: Boolean)
    }
}