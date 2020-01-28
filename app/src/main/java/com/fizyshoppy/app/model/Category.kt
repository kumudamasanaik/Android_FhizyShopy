package com.fizyshoppy.app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
        var _id: String,
        val name: String,
        val parent: String,
        val pic: String,
        val description: String,
        val block: String,
        val updated: String,
        var isSelectedCategory: Boolean = false
) : Parcelable