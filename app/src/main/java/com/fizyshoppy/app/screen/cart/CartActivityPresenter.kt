package com.fizyshoppy.app.screen.cart

import com.fizyshoppy.AppController
import com.fizyshoppy.app.api.ApiRequestParam
import com.fizyshoppy.app.api.ApiRequestTypes
import com.fizyshoppy.app.api.ApiResponsePresenter
import com.fizyshoppy.app.api.IResponseInterface
import com.fizyshoppy.app.customview.MultiStateView
import com.fizyshoppy.app.model.CommonRes
import retrofit2.Call
import retrofit2.Response

class CartActivityPresenter(view: CartActivityContract.View) : CartActivityContract.Presenter, IResponseInterface {

    private var iResponseInterface: ApiResponsePresenter = ApiResponsePresenter(this)
    private var view: CartActivityContract.View? = view

    override fun callFetchCartdApi() {
        iResponseInterface.callApi(AppController.service.fetchCartResp(ApiRequestParam.fetchCart()), ApiRequestTypes.FETCH_CART)
    }

    override fun callModifyCart(ModifyParmeter: String) {
        iResponseInterface.callApi(AppController.service.modifyCart(ModifyParmeter), ApiRequestTypes.MODIFY_CART)

    }

    override fun callModifyWishList(typeOpretion: String, productId: String) {
        iResponseInterface.callApi(AppController.service.getModifyWishList(ApiRequestParam.modifyWishList(typeOpretion, productId)), ApiRequestTypes.MODIFY_WISH_LIST)

    }

    override fun callRemoveFromCart(ModifyParmeter: String) {
        iResponseInterface.callApi(AppController.service.removeFromCart(ModifyParmeter), ApiRequestTypes.REMOVE_FROM_CART)
    }

    override fun <T> onResponseSuccess(call: Call<T>, response: Response<T>, reqType: String) {
        view?.hideLoader()
        if (response.isSuccessful) {
            when (reqType) {
                ApiRequestTypes.FETCH_CART -> {
                    view?.setFetchCartApiRes(response.body() as CommonRes)
                }

                ApiRequestTypes.MODIFY_WISH_LIST -> {
                    view?.setModifyWishListApiResp(response.body() as CommonRes)
                }

                ApiRequestTypes.MODIFY_CART -> {
                    view?.setModifyCartApiResp(response.body() as CommonRes)
                }

                ApiRequestTypes.REMOVE_FROM_CART -> {
                    view?.setRemoveFromCart(response.body() as CommonRes)
                }

            }
        } else view?.showViewState(MultiStateView.VIEW_STATE_ERROR)

    }

    override fun <T> onResponseFailure(call: Call<T>, responseError: Throwable, reqType: String) {
        view?.hideLoader()
        when (reqType) {
            ApiRequestTypes.FETCH_CART -> {
                view?.showViewState(MultiStateView.VIEW_STATE_ERROR)
            }

            ApiRequestTypes.MODIFY_WISH_LIST -> {
                view?.showMsg(responseError.message)
            }

            ApiRequestTypes.MODIFY_CART -> {
                view?.showMsg(responseError.message)
            }

            ApiRequestTypes.REMOVE_FROM_CART -> {
                view?.showMsg(responseError.message)
            }

        }
    }
    // view?.showMsg(responseError.message)
}

