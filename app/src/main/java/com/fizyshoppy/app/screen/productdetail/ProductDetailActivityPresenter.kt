package com.fizyshoppy.app.screen.productdetail

import com.fizyshoppy.AppController
import com.fizyshoppy.app.api.ApiRequestParam
import com.fizyshoppy.app.api.ApiRequestTypes
import com.fizyshoppy.app.api.ApiResponsePresenter
import com.fizyshoppy.app.api.IResponseInterface
import com.fizyshoppy.app.model.ProductDetailRes
import retrofit2.Call
import retrofit2.Response

class ProductDetailActivityPresenter(view: ProductDetailActivityContract.View) : ProductDetailActivityContract.Presenter, IResponseInterface {
    override fun callProductDetailApi(parent: String) {
        iResponseInterface.callApi(AppController.service.getProductDetail(ApiRequestParam.productDetailRequest(parent)), ApiRequestTypes.PRODUCT_DETAIL)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var iResponseInterface: ApiResponsePresenter = ApiResponsePresenter(this)
    private var view: ProductDetailActivityContract.View? = view

    override fun <T> onResponseSuccess(call: Call<T>, response: Response<T>, reqType: String) {

        view?.hideLoader()
        if (response.isSuccessful) {
            when (reqType) {
                ApiRequestTypes.PRODUCT_DETAIL ->
                    view?.setProductDetailRes(response.body() as ProductDetailRes)

            }
        }
    }

    override fun <T> onResponseFailure(call: Call<T>, responseError: Throwable, reqType: String) {

        view?.hideLoader()
        view?.showMsg(responseError.message)

    }

}