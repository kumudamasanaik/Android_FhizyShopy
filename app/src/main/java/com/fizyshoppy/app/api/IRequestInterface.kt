package com.fizyshoppy.app.api

import retrofit2.Call

interface IRequestInterface {

    //void callApi(Call call, final String reqType);
    fun<T> callApi(call: Call<T>, reqType: String)
}

