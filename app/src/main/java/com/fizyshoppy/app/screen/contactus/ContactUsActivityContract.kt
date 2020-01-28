package com.fizyshoppy.app.screen.contactus

import com.fizyshoppy.app.BasePresenter
import com.fizyshoppy.app.BaseView
import com.fizyshoppy.app.model.ContactRes

interface ContactUsActivityContract {

    interface View : BaseView {
        fun callContactUsScreenApi()
        fun setContactUsApiRes(response: ContactRes)

    }

    interface Presenter : BasePresenter<View> {
        fun callContactUsAPi(string: String)
    }

}