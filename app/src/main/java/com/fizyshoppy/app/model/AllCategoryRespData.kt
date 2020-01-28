package com.fizyshoppy.app.model

data class AllCategoryRespData(
        val catid: String,
        val catname: String,
        val subcategory: ArrayList<Category>
)