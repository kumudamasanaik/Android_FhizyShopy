package com.fizyshoppy.app.screen.myaccount

import com.fizyshoppy.AppController
import com.fizyshoppy.app.api.ApiRequestParam
import com.fizyshoppy.app.api.ApiRequestTypes
import com.fizyshoppy.app.api.ApiResponsePresenter
import com.fizyshoppy.app.api.IResponseInterface
import com.fizyshoppy.app.model.MyAccountRes
import retrofit2.Call
import retrofit2.Response

class MyAccountActivityPresenter(view: MyAccountActivityContract.View) : MyAccountActivityContract.Presenter, IResponseInterface {

    private var iResponseInterface: ApiResponsePresenter = ApiResponsePresenter(this)
    private var view: MyAccountActivityContract.View? = view

    override fun callMyAccountApi(parent: String) {
        iResponseInterface.callApi(AppController.service.getMyAccountList(ApiRequestParam.myAccountRequest(parent)), ApiRequestTypes.GET_MY_ACCOUNT_DATA)

    }

    override fun <T> onResponseSuccess(call: Call<T>, response: Response<T>, reqType: String) {

        view?.hideLoader()
        if (response.isSuccessful) {
            when (reqType) {
                ApiRequestTypes.GET_MY_ACCOUNT_DATA ->
                    view?.setMyAccountRes(response.body() as MyAccountRes)

            }
        }
    }

    override fun <T> onResponseFailure(call: Call<T>, responseError: Throwable, reqType: String) {

        view?.hideLoader()
        view?.showMsg(responseError.message)

    }

}