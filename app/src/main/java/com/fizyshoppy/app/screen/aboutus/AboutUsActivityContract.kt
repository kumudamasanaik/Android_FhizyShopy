package com.fizyshoppy.app.screen.aboutus

import com.fizyshoppy.app.BasePresenter
import com.fizyshoppy.app.BaseView
import com.fizyshoppy.app.model.ContactRes

interface AboutUsActivityContract {
    interface View : BaseView {
        fun callAboutUsScreenApi()
        fun setAboutUsApiRes(response: ContactRes)

    }

    interface Presenter : BasePresenter<View> {
        fun callAboutUsAPi(string: String)
    }
}