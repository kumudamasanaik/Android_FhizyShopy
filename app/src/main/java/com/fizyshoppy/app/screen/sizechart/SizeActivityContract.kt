package com.fizyshoppy.app.screen.sizechart

import com.fizyshoppy.app.BasePresenter
import com.fizyshoppy.app.BaseView
import com.fizyshoppy.app.model.CustomerRes

interface SizeActivityContract {

    interface View : BaseView {
        fun callSizeListApi()
        fun setSizeListApiResp(res: CustomerRes)
    }

    interface Presenter : BasePresenter<View> {
        fun callSizeChartApi()
    }

}