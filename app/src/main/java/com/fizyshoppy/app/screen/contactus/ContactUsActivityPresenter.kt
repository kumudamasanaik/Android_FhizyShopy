package com.fizyshoppy.app.screen.contactus

import com.fizyshoppy.AppController
import com.fizyshoppy.app.api.ApiRequestParam
import com.fizyshoppy.app.api.ApiRequestTypes
import com.fizyshoppy.app.api.ApiResponsePresenter
import com.fizyshoppy.app.api.IResponseInterface
import com.fizyshoppy.app.customview.MultiStateView
import com.fizyshoppy.app.model.ContactRes
import retrofit2.Call
import retrofit2.Response

class ContactUsActivityPresenter(view: ContactUsActivityContract.View) : ContactUsActivityContract.Presenter, IResponseInterface {

    private var view: ContactUsActivityContract.View? = view
    private var iResponseInterface: ApiResponsePresenter

    init {
        this.view = view
        this.iResponseInterface = ApiResponsePresenter(this)
    }

    override fun <T> onResponseSuccess(call: Call<T>, response: Response<T>, reqType: String) {
        view?.hideLoader()
        if (response.isSuccessful) {
            when (reqType) {
                ApiRequestTypes.CONTACT_US ->
                    view?.setContactUsApiRes(response.body() as ContactRes)

            }
        } else view?.showViewState(MultiStateView.VIEW_STATE_ERROR)
    }

    override fun <T> onResponseFailure(call: Call<T>, responseError: Throwable, reqType: String) {
        view?.hideLoader()
        view?.showViewState(MultiStateView.VIEW_STATE_ERROR)
    }

    override fun callContactUsAPi(string: String) {
        iResponseInterface.callApi(AppController.service.getAboutUsScreen(ApiRequestParam.aboutUsRequest(string)), ApiRequestTypes.CONTACT_US)
    }
}