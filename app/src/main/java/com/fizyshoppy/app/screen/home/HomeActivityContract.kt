package com.fizyshoppy.app.screen.home

import android.content.Context
import com.fizyshoppy.app.BasePresenter
import com.fizyshoppy.app.BaseView
import com.fizyshoppy.app.model.Home

/**
 * Created by FuGenX-14 on 07-08-2018.
 */
interface HomeActivityContract {
    interface View : BaseView {
        fun callHomeScreenApi()
        fun setDashboardApiRes(responce: Home)

    }
    interface Presenter : BasePresenter<View> {
        fun callHomeAPi(mContext:Context)
    }
}
