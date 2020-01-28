package com.fizyshoppy.app.screen.purchasegiftcard

import com.fizyshoppy.app.BasePresenter
import com.fizyshoppy.app.BaseView
import com.fizyshoppy.app.model.PurchaseGiftCardRes

interface PurchaseGiftCardActivityContract {

    interface View : BaseView {
        fun callPurchaseGiftCardScreenApi()
        fun setPurchaseGiftCardRes(responce: PurchaseGiftCardRes)

    }

    interface Presenter : BasePresenter<View> {
        fun callPurchaseGiftCardApi(parent: String)
    }
}