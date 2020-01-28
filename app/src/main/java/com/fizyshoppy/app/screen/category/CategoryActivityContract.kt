package com.fizyshoppy.app.screen.category

import com.fizyshoppy.app.BasePresenter
import com.fizyshoppy.app.BaseView
import com.fizyshoppy.app.model.AllCategoryResp
import com.fizyshoppy.app.model.SubCategoryResp

/**
 * Created by FuGenX-14 on 07-08-2018.
 */
interface CategoryActivityContract {

    interface View : BaseView {

        fun callSubCategoryApi()
        fun setCategoryApiRes(subCategoryResp: SubCategoryResp)
        fun callALLCategoryApi()
        fun setAllCategoryApiResp(allCategoryResp: AllCategoryResp)

    }

    interface Presenter : BasePresenter<View> {

        fun callSubCategoryApi(parent: String)
        fun callAllCategoryApi(parent: String)
    }
}