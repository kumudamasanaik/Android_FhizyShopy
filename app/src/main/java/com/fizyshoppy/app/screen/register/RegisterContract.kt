package com.fizyshoppy.app.screen.register

import android.content.Context
import com.fizyshoppy.app.BasePresenter
import com.fizyshoppy.app.BaseView
import com.fizyshoppy.app.model.CustomerRes
import com.fizyshoppy.app.model.payloadmodel.RegisterInput

interface RegisterContract {

    interface View : BaseView {
        fun doRegister()
        fun setRegsiterRes(res: CustomerRes)
        fun getContext(): Context
        fun showInValidFiledMsg(msg: String)
    }

    interface Presenter : BasePresenter<View> {
        fun validate(input: RegisterInput): Boolean
        fun callRegister(input: RegisterInput)
    }

}