package com.fizyshoppy.app.screen.home

import android.content.Context
import com.fizyshoppy.AppController
import com.fizyshoppy.app.api.ApiRequestTypes
import com.fizyshoppy.app.api.ApiResponsePresenter
import com.fizyshoppy.app.api.IResponseInterface
import com.fizyshoppy.app.customview.MultiStateView
import com.fizyshoppy.app.model.Home
import retrofit2.Call
import retrofit2.Response

/**
 * Created by FuGenX-14 on 07-08-2018.
 */
class HomeActivityPresenter(view: HomeActivityContract.View) : HomeActivityContract.Presenter, IResponseInterface {
    private var view: HomeActivityContract.View? = view
    private var iResponseInterface: ApiResponsePresenter
    init {
        this.view = view
        this.iResponseInterface = ApiResponsePresenter(this)
    }

    override fun callHomeAPi(mContext: Context) {
        //ApiRequestParam.getHomeDataPairs(mContext)
        iResponseInterface.callApi(AppController.service.getHomeScreenData(), ApiRequestTypes.DASHBOARD)

    }

    override fun <T> onResponseSuccess(call: Call<T>, response: Response<T>, reqType: String) {
        view?.hideLoader()
        if (response.isSuccessful) {
            when (reqType) {
                ApiRequestTypes.DASHBOARD ->
                    view?.setDashboardApiRes(response.body() as Home)

            }
        } else view?.showViewState(MultiStateView.VIEW_STATE_ERROR)

    }

    override fun <T> onResponseFailure(call: Call<T>, responseError: Throwable, reqType: String) {
        view?.hideLoader()
        // view?.showMsg(responseError.message)
        view?.showViewState(MultiStateView.VIEW_STATE_ERROR)
    }


}