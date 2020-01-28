package com.fizyshoppy.app.screen.productdetail

import com.fizyshoppy.app.BasePresenter
import com.fizyshoppy.app.BaseView
import com.fizyshoppy.app.model.ProductDetailRes

interface ProductDetailActivityContract {

    interface View : BaseView {
        fun callProductDetailScreenApi()

        fun setProductDetailRes(responce: ProductDetailRes)

    }

    interface Presenter : BasePresenter<View> {
        fun callProductDetailApi(parent: String)
        // TODO ("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}