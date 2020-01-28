package com.fizyshoppy.app.model

data class CustomerData(
        val _id: String,
        val name: String,
        val email: String,
        val mobile: String,
        val otp: String,
        val verified: String,
        val pic: String,
        val facebook_id: String,
        val google_id: String,
        val linkedin_id: String,
        val customer_type: String,
        val status: String,
        val gender: String,
        val birthday: Any,
        val newsletter: String,
        val website: Any,
        val email_verified: String,
        val referral_code: String,
        val referred_by: String,
        val referral_used: String,
        val note: String
)