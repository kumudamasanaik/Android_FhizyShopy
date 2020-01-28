package com.fizyshoppy.app.screen.cart

import com.fizyshoppy.app.BasePresenter
import com.fizyshoppy.app.BaseView
import com.fizyshoppy.app.model.CommonRes

interface CartActivityContract {

    interface View : BaseView {
        fun navigateDetailScreen()
        fun callFetchCartdApi()
        fun setFetchCartApiRes(commonRes: CommonRes)
        fun modifyCartApi()
        fun setModifyCartApiResp(commonRes: CommonRes)
        fun callModifyWishListApi()
        fun setModifyWishListApiResp(commonRes: CommonRes)
        fun removeFromCart()
        fun setRemoveFromCart(commonRes: CommonRes)

    }

    interface Presenter : BasePresenter<View> {
        fun callFetchCartdApi()
        fun callModifyCart(ModifyParmeter :String)
        fun callModifyWishList(typeOpretion:String,productId:String)
        fun callRemoveFromCart(ModifyParmeter :String)
    }

}