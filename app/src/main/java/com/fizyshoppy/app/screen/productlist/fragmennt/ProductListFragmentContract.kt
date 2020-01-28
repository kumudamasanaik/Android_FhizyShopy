package com.fizyshoppy.app.screen.productlist.fragmennt

import com.fizyshoppy.app.BasePresenter
import com.fizyshoppy.app.BaseView
import com.fizyshoppy.app.model.CommonRes

interface ProductListFragmentContract {

    interface View : BaseView {
        fun callProductList()
        fun navigateDetailScreen()
        fun setProductListApiRes(resp: CommonRes)
        fun modifyCartApi()
        fun setModifyCartApiResp(commonRes: CommonRes)
        fun callModifyWishListApi()
        fun setModifyWishListApiResp(commonRes: CommonRes)

    }
    interface Presenter : BasePresenter<View> {
        fun callProductListApi(catId:String)
        fun callModifyCart(ModifyParmeter :String)
        fun callModifyWishList(typeOpretion:String,productId:String)
    }
}