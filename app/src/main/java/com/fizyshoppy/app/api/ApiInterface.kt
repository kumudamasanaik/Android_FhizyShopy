package com.fizyshoppy.app.api

import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.model.*
import com.fizyshoppy.app.model.payloadmodel.RegisterInput
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {

    @POST(ApiConstants.LOGIN)
    fun doLogin(@Body json: JsonObject): Call<CustomerRes>

    @POST(ApiConstants.REGISTER)
    fun doRegister(@Body registerInput: RegisterInput): Call<CustomerRes>

    @POST(ApiConstants.OTP)
    fun verifyOtp(@Header(Constants.AUTHORIZATION) header: String, @Body json: JsonObject): Call<CustomerRes>

    @POST(ApiConstants.FORGOT_PASSWORD_NEW)
    fun forgotPasss(@Body jsonObject: JsonObject): Call<CustomerRes>

    @GET(ApiConstants.HOME)
    fun getHomeScreenData(): Call<Home>

    @POST(ApiConstants.FETCH_CART)
    fun fetchCartResp(@Body jsonObject: JsonObject): Call<CommonRes>

    @POST(ApiConstants.MODIFY_CART_URL)
    fun modifyCart(@Body modifyParameter: String): Call<CommonRes>

    @POST(ApiConstants.REMOVE_PRODUCT_FROM_CART_URL)
    fun removeFromCart(@Body modifyParameter: String): Call<CommonRes>

    @POST(ApiConstants.MODIFY_WISH_LIST)
    fun getModifyWishList(@Body jsonObject: JsonObject): Call<CommonRes>

    @POST(ApiConstants.PRODUCT_LIST)
    fun getProductList(@Body jsonObject: JsonObject): Call<ProductResponse>

    @POST(ApiConstants.SIZE_CHART)
    fun getSizeChartList(@Body jsonObject: JsonObject): Call<CommonRes>


    @POST(ApiConstants.RESEND_OTP)
    fun resendOtp(@Body jsonObject: JsonObject): Call<ResendOtpRes>

    @GET(ApiConstants.MAIN_CATEGORY)
    fun getMainCategory(): Call<MainCategoryResp>

    @POST(ApiConstants.SUB_CATEGORY)
    fun getSubCategory(@Body jsonObject: JsonObject): Call<SubCategoryResp>

    /** about us, contact us and privacy policy api interface*/
    @POST(ApiConstants.ABOUT_US)
    fun getAboutUsScreen(@Body jsonObject: JsonObject): Call<ContactRes>

    @POST(ApiConstants.ALL_CATEGORY)
    fun getAllCategoryApi(@Body jsonObject: JsonObject): Call<AllCategoryResp>

    @POST(ApiConstants.SUB_CATEGORY)
    fun getProductMainList(@Body jsonObject: JsonObject): Call<ProductMainListRes>

    @POST(ApiConstants.NOTIFICATION)
    fun getNotificationList(@Body jsonObject: JsonObject): Call<NotificationRes> //  TODO(notification api)

    @POST(ApiConstants.MY_ACCOUNT)
    fun getMyAccountList(@Body jsonObject: JsonObject): Call<MyAccountRes>

    @POST(ApiConstants.PURCHASE_GIFT_CARD)
    fun getPurchaseGiftCardList(@Body jsonObject: JsonObject): Call<PurchaseGiftCardRes> //  TODO(purchase gift card)

    @POST(ApiConstants.PRODUCT_DETAIL)
    fun getProductDetail(@Body jsonObject: JsonObject): Call<ProductResponse> //  TODO(product detail)

    @POST(ApiConstants.WISH_LIST)
    fun getWishList() //  TODO (wish list)

    @POST(ApiConstants.WISH_LIST)
    fun getAddDeleteWishList() //  TODO (wish list)

}