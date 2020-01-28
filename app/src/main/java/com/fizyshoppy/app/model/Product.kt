package com.fizyshoppy.app.model

data class Product(
        val _id: String?,
        val name: String?,
        val article_no: String?,
        val description: String?,
        val tags: String?,
        val brand: String?,
        val id_category: String?,
        val express: String?,
        val cat1: String?,
        val cat1_name: String?,
        val cat2: String?,
        val cat2_name: String?,
        val cat3: String?,
        val cat3_name: String?,
        val cat4: String?,
        val cat4_name: String?,
        val pic: List<Pic>?,
        val spec: List<Spec>?,
        val sku: List<Sku>?
)