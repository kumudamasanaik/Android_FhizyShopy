package com.fizyshoppy.app.api

class ApiConstants {
    companion object {
        const val BASE_URL: String = "http://54.201.67.32/fizyshoppy/api/"
        const val IMAGE_BASE_URL: String = "http://54.201.67.32/fizyshoppy/"
        const val LOGIN: String = "customer/login"
        const val REGISTER: String = "customer"
        const val OTP: String = "customer/otp"
        const val FORGOT_PASSWORD: String = "customer/forgot"
        const val HOME: String = "home" /*TO DO CHANGE API*/
        const val FETCH_CART: String = "cart" /*TO DO CHANGE API*/
        const val MODIFY_CART_URL: String = "cart"
        const val REMOVE_PRODUCT_FROM_CART_URL = "cart/remove"
        const val MODIFY_WISH_LIST = "wishlist"
        const val PRODUCT_LIST = "product/category"
        const val SIZE_CHART = "wishlist"

        const val FORGOT_PASSWORD_NEW: String = "customer/sendotp"
        const val RESEND_OTP: String = "customer/resend_otp"
        const val MAIN_CATEGORY: String = "category/indexadmin"
        const val SUB_CATEGORY: String = "category/getsubcategories"

        const val ABOUT_US: String = "home/getcontent"
        const val ALL_CATEGORY: String = "category/allsubcategories"
        const val MY_ACCOUNT: String = "customer/profile"
        const val NOTIFICATION: String = "dummy" //  TODO (notification constant).
        const val PURCHASE_GIFT_CARD: String = "dummy/purchase/gift/card" //  TODO (purchase gift card constant).
        const val PRODUCT_DETAIL: String = "dummy/product/detail" //  TODO (PRODUCT DETAIL constant).

        const val WISH_LIST: String = "wishlist" //  TODO (WISH LIST).


    }
}