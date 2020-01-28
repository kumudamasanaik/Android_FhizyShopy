package com.fizyshoppy.app.screen.wishlist

import com.fizyshoppy.app.BasePresenter
import com.fizyshoppy.app.BaseView

interface WishListActivityContract {

    interface View : BaseView {
        fun callGetWishListScreenApi()
//        fun setMyAccountRes(responce: MyAccountRes)

    }

    interface Presenter : BasePresenter<View> {
//        fun callMyAccountApi(parent: String)
    }
}