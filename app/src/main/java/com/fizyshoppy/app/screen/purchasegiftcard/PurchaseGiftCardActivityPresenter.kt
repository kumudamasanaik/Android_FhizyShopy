package com.fizyshoppy.app.screen.purchasegiftcard

import com.fizyshoppy.AppController
import com.fizyshoppy.app.api.ApiRequestParam
import com.fizyshoppy.app.api.ApiRequestTypes
import com.fizyshoppy.app.api.ApiResponsePresenter
import com.fizyshoppy.app.api.IResponseInterface
import com.fizyshoppy.app.model.PurchaseGiftCardRes
import retrofit2.Call
import retrofit2.Response

class PurchaseGiftCardActivityPresenter(view: PurchaseGiftCardActivityContract.View) : PurchaseGiftCardActivityContract.Presenter, IResponseInterface {

    private var iResponseInterface: ApiResponsePresenter = ApiResponsePresenter(this)
    private var view: PurchaseGiftCardActivityContract.View? = view

    override fun callPurchaseGiftCardApi(parent: String) {
        iResponseInterface.callApi(AppController.service.getPurchaseGiftCardList(ApiRequestParam.purchaseGiftCardRequest(parent)), ApiRequestTypes.PURCHASE_GIFT_CARD_DATA)
    }

    override fun <T> onResponseSuccess(call: Call<T>, response: Response<T>, reqType: String) {
        view?.hideLoader()
        if (response.isSuccessful) {
            when (reqType) {
                ApiRequestTypes.PURCHASE_GIFT_CARD_DATA ->
                    view?.setPurchaseGiftCardRes(response.body() as PurchaseGiftCardRes)

            }
        }
    }

    override fun <T> onResponseFailure(call: Call<T>, responseError: Throwable, reqType: String) {
        view?.hideLoader()
        view?.showMsg(responseError.message)
    }
}