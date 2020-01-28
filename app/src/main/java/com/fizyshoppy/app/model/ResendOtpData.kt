package com.fizyshoppy.app.model

import com.google.gson.annotations.SerializedName

class ResendOtpData {

    @SerializedName("code")
    var code: String? = null
    @SerializedName("message")
    var message: String? = null
    @SerializedName("status")
    var status: String? = null

}
