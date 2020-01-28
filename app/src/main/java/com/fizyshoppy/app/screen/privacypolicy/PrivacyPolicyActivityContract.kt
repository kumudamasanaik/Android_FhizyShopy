package com.fizyshoppy.app.screen.privacypolicy

import com.fizyshoppy.app.BasePresenter
import com.fizyshoppy.app.BaseView
import com.fizyshoppy.app.model.ContactRes

interface PrivacyPolicyActivityContract {

    interface View : BaseView {
        fun callPrivacyPolicyScreenApi()
        fun setPrivacyPolicyApiRes(response: ContactRes)

    }

    interface Presenter : BasePresenter<View> {
        fun callPrivacyPolicyAPi(string: String)
    }
}