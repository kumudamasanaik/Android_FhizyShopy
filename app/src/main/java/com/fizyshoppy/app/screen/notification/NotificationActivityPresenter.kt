package com.fizyshoppy.app.screen.notification

import com.fizyshoppy.AppController
import com.fizyshoppy.app.api.ApiRequestParam
import com.fizyshoppy.app.api.ApiRequestTypes
import com.fizyshoppy.app.api.ApiResponsePresenter
import com.fizyshoppy.app.api.IResponseInterface
import com.fizyshoppy.app.model.NotificationRes
import retrofit2.Call
import retrofit2.Response

class NotificationActivityPresenter(view: NotificationActivityContract.View) : NotificationActivityContract.Presenter, IResponseInterface {

    private var iResponseInterface: ApiResponsePresenter = ApiResponsePresenter(this)
    private var view: NotificationActivityContract.View? = view

    override fun callNotificationApi(parent: String) {
        iResponseInterface.callApi(AppController.service.getNotificationList(ApiRequestParam.notificationRequest(parent)), ApiRequestTypes.NOTIFICATION_DATA)
    }

    override fun <T> onResponseSuccess(call: Call<T>, response: Response<T>, reqType: String) {
        view?.hideLoader()
        if (response.isSuccessful) {
            when (reqType) {
                ApiRequestTypes.NOTIFICATION_DATA ->
                    view?.setNotificationRes(response.body() as NotificationRes)

            }
        }
    }

    override fun <T> onResponseFailure(call: Call<T>, responseError: Throwable, reqType: String) {
        view?.hideLoader()
        view?.showMsg(responseError.message)
    }
}