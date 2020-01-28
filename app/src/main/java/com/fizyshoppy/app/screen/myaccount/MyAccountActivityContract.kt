package com.fizyshoppy.app.screen.myaccount

import com.fizyshoppy.app.BasePresenter
import com.fizyshoppy.app.BaseView
import com.fizyshoppy.app.model.MyAccountRes

interface MyAccountActivityContract {

    interface View : BaseView {
        fun callMyAccountScreenApi()
        fun setMyAccountRes(responce: MyAccountRes)

    }

    interface Presenter : BasePresenter<View> {
        fun callMyAccountApi(parent: String)
    }
}