package com.fizyshoppy.app.screen.productlist

import com.fizyshoppy.AppController
import com.fizyshoppy.app.api.ApiRequestParam
import com.fizyshoppy.app.api.ApiRequestTypes
import com.fizyshoppy.app.api.ApiResponsePresenter
import com.fizyshoppy.app.api.IResponseInterface
import com.fizyshoppy.app.model.ProductMainListRes
import retrofit2.Call
import retrofit2.Response

class ProductMainListActivityPresenter(view: ProductListMainActivityContract.View) : ProductListMainActivityContract.Presenter, IResponseInterface {

    private var iResponseInterface: ApiResponsePresenter = ApiResponsePresenter(this)
    private var view: ProductListMainActivityContract.View? = view

    override fun <T> onResponseSuccess(call: Call<T>, response: Response<T>, reqType: String) {
        view?.hideLoader()
        if (response.isSuccessful) {
            when (reqType) {
                ApiRequestTypes.PRODUCT_MAIN_LIST_DATA ->
                    view?.setProductMainListRes(response.body() as ProductMainListRes)

            }
        }
    }

    override fun <T> onResponseFailure(call: Call<T>, responseError: Throwable, reqType: String) {
        view?.hideLoader()
        view?.showMsg(responseError.message)
    }

    override fun callProductMainListApi(parent: String) {
        iResponseInterface.callApi(AppController.service.getProductMainList(ApiRequestParam.subCategoryRequest(parent)), ApiRequestTypes.PRODUCT_MAIN_LIST_DATA)
    }

}