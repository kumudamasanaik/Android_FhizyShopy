package com.fizyshoppy.app.screen.wishlist

import com.fizyshoppy.app.api.ApiResponsePresenter
import com.fizyshoppy.app.api.IResponseInterface
import retrofit2.Call
import retrofit2.Response

class WishListActivityPresenter(view: WishListActivityContract.View) : WishListActivityContract.Presenter, IResponseInterface {

    private var iResponseInterface: ApiResponsePresenter = ApiResponsePresenter(this)
    private var view: WishListActivityContract.View? = view

    override fun <T> onResponseSuccess(call: Call<T>, response: Response<T>, reqType: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> onResponseFailure(call: Call<T>, responseError: Throwable, reqType: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}