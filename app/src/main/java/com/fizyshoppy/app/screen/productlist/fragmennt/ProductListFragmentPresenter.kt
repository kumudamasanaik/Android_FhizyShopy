package com.fizyshoppy.app.screen.productlist.fragmennt

import com.fizyshoppy.AppController
import com.fizyshoppy.app.api.ApiRequestParam
import com.fizyshoppy.app.api.ApiRequestTypes
import com.fizyshoppy.app.api.ApiResponsePresenter
import com.fizyshoppy.app.api.IResponseInterface
import com.fizyshoppy.app.customview.MultiStateView
import com.fizyshoppy.app.model.CommonRes
import retrofit2.Call
import retrofit2.Response

class ProductListFragmentPresenter(view: ProductListFragmentContract.View) : ProductListFragmentContract.Presenter, IResponseInterface {

    private var iResponseInterface: ApiResponsePresenter = ApiResponsePresenter(this)
    private var view: ProductListFragmentContract.View? = view

    override fun callProductListApi(catId: String) {
        iResponseInterface.callApi(AppController.service.getProductList(ApiRequestParam.getProductListPairs(catId)), ApiRequestTypes.PRODUCT_LIST)

    }

    override fun callModifyCart(ModifyParmeter: String) {
        iResponseInterface.callApi(AppController.service.modifyCart(ModifyParmeter), ApiRequestTypes.MODIFY_CART)

    }

    override fun callModifyWishList(typeOpretion: String, productId: String) {
        iResponseInterface.callApi(AppController.service.getModifyWishList(ApiRequestParam.modifyWishList(typeOpretion, productId)), ApiRequestTypes.MODIFY_WISH_LIST)

    }

    override fun <T> onResponseSuccess(call: Call<T>, response: Response<T>, reqType: String) {
        view?.hideLoader()
        if (response.isSuccessful) {
            when (reqType) {
                ApiRequestTypes.PRODUCT_LIST -> {
                    view?.setProductListApiRes(response.body() as CommonRes)
                }

                ApiRequestTypes.MODIFY_WISH_LIST -> {
                    view?.setModifyWishListApiResp(response.body() as CommonRes)
                }

                ApiRequestTypes.MODIFY_CART -> {
                    view?.setModifyCartApiResp(response.body() as CommonRes)
                }
            }
        } else view?.showViewState(MultiStateView.VIEW_STATE_ERROR)
    }

    override fun <T> onResponseFailure(call: Call<T>, responseError: Throwable, reqType: String) {
        view?.hideLoader()
        when (reqType) {
            ApiRequestTypes.PRODUCT_LIST -> {
                view?.showViewState(MultiStateView.VIEW_STATE_ERROR)
            }

            ApiRequestTypes.MODIFY_WISH_LIST -> {
                view?.showMsg(responseError.message)
            }

            ApiRequestTypes.MODIFY_CART -> {
                view?.showMsg(responseError.message)
            }


        }
    }
}