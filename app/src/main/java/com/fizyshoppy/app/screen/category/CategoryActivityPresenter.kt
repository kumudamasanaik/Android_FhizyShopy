package com.fizyshoppy.app.screen.category

import com.fizyshoppy.AppController
import com.fizyshoppy.app.api.ApiRequestParam
import com.fizyshoppy.app.api.ApiRequestTypes
import com.fizyshoppy.app.api.ApiResponsePresenter
import com.fizyshoppy.app.api.IResponseInterface
import com.fizyshoppy.app.model.AllCategoryResp
import com.fizyshoppy.app.model.SubCategoryResp
import retrofit2.Call
import retrofit2.Response

/**
 * Created by FuGenX-14 on 07-08-2018.
 */
class CategoryActivityPresenter(view: CategoryActivityContract.View) : CategoryActivityContract.Presenter, IResponseInterface {


    private var iResponseInterface: ApiResponsePresenter = ApiResponsePresenter(this)
    private var view: CategoryActivityContract.View? = view

    override fun <T> onResponseSuccess(call: Call<T>, response: Response<T>, reqType: String) {
        view?.hideLoader()
        if (response.isSuccessful) {
            when (reqType) {
                ApiRequestTypes.SUB_CAT_DATA ->
                    view?.setCategoryApiRes(response.body() as SubCategoryResp)

                ApiRequestTypes.ALL_CATEGORY_DATA ->
                    view?.setAllCategoryApiResp(response.body() as AllCategoryResp)

            }
        }
    }

    override fun <T> onResponseFailure(call: Call<T>, responseError: Throwable, reqType: String) {
        view?.hideLoader()
        view?.showMsg(responseError.message)
    }


    override fun callSubCategoryApi(parent: String) {
        iResponseInterface.callApi(AppController.service.getSubCategory(ApiRequestParam.subCategoryRequest(parent)), ApiRequestTypes.SUB_CAT_DATA)
    }

    override fun callAllCategoryApi(parent: String) {
        iResponseInterface.callApi(AppController.service.getAllCategoryApi(ApiRequestParam.getChildCategoryData(parent)), ApiRequestTypes.ALL_CATEGORY_DATA)
    }

}