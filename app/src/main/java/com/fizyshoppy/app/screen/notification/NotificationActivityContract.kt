package com.fizyshoppy.app.screen.notification

import com.fizyshoppy.app.BasePresenter
import com.fizyshoppy.app.BaseView
import com.fizyshoppy.app.model.NotificationRes

interface NotificationActivityContract {

    interface View : BaseView {
        fun callNotificationScreenApi()
        fun setNotificationRes(responce: NotificationRes)

    }

    interface Presenter : BasePresenter<View> {
        fun callNotificationApi(parent: String)
    }
}