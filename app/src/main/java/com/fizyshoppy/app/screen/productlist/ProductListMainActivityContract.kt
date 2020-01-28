package com.fizyshoppy.app.screen.productlist

import com.fizyshoppy.app.BasePresenter
import com.fizyshoppy.app.BaseView
import com.fizyshoppy.app.model.ProductMainListRes

interface ProductListMainActivityContract {
    interface View : BaseView {
        fun navigateFilterScreen()
        fun setProductMainListRes(responce: ProductMainListRes)

    }

    interface Presenter : BasePresenter<View> {
        fun callProductMainListApi(parent: String)
    }
}