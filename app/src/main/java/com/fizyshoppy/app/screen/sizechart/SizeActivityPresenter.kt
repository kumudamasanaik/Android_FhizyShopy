package com.fizyshoppy.app.screen.sizechart

import com.fizyshoppy.AppController
import com.fizyshoppy.app.api.*
import com.fizyshoppy.app.model.CustomerRes
import retrofit2.Call
import retrofit2.Response

class SizeActivityPresenter(view: SizeActivityContract.View) : SizeActivityContract.Presenter, IResponseInterface {

    var view: SizeActivityContract.View? = null
    var iRequestInterface: IRequestInterface

    init {
        this.view = view
        this.iRequestInterface = ApiResponsePresenter(this)
    }


    override fun callSizeChartApi() {
        iRequestInterface.callApi(AppController.service.getSizeChartList(ApiRequestParam.getSizeChartPairs()), ApiRequestTypes.SIZE_CHART)
    }

    override fun <T> onResponseSuccess(call: Call<T>, response: Response<T>, reqType: String) {
        view?.hideLoader()
        if (response.isSuccessful) {
            when (reqType) {
                ApiRequestTypes.SIZE_CHART -> {
                    view?.setSizeListApiResp(response.body() as CustomerRes)
                }
            }
        }
    }

    override fun <T> onResponseFailure(call: Call<T>, responseError: Throwable, reqType: String) {
        view?.hideLoader()
    }
}