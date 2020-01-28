package com.fizyshoppy.app.screen.viewholder

import android.content.Context
import android.os.Bundle
import android.view.View

import com.fizyshoppy.app.model.Popularproduct
import com.fizyshoppy.app.screen.productdetail.ProductDetailActivity
import com.fizyshoppy.app.util.CommonUtil
import com.fizyshoppy.app.util.ImageUtils
import com.fizyshoppy.app.util.Validation
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.partial_popular_product_list_item.*


/**
 * Created by FuGenX-14 on 06-08-2018.
 */
class PopularProductsViewHolder(override val containerView: View) : BaseViewholder(containerView), LayoutContainer {
    lateinit var popular_products: Popularproduct

    override fun bind(context: Context, item: Any, pos: Int) {

        if (Validation.isValidObject(item)) {
            popular_products = item as Popularproduct

            if (Validation.isValidString(popular_products.pic))
                ImageUtils.setImageWithPicasso(context, image_product, popular_products.pic)

            if (Validation.isValidString(popular_products.name_en))
                tv_product_name.text = popular_products.name_en

            if (Validation.isValidString(popular_products.category_name))
                tv_cat_name.text = popular_products.category_name

            if (Validation.isValidDigit(popular_products.selling_price))
                tv_saved_price.text = CommonUtil.getStrWithRsSym(context, popular_products.selling_price)

            if (Validation.isValidDigit(popular_products.mrp)) {
                tv_original_price.text = CommonUtil.getStrWithRsSym(context, popular_products.mrp)
                CommonUtil.strikeThrText(tv_original_price)
            }



            if (Validation.isValidString(popular_products.product_unit))
                tv_weight.text = popular_products.product_unit

            itemView.setOnClickListener {
                CommonUtil.startActivity(context, ProductDetailActivity::class.java, bundle = Bundle())
            }


        }
    }

}