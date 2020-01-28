package com.fizyshoppy.app.screen.viewholder

import android.content.Context
import android.view.View
import com.fizyshoppy.app.R
import com.fizyshoppy.app.listners.IAdapterClickListener
import com.fizyshoppy.app.model.Category
import com.fizyshoppy.app.util.ImageUtils
import com.fizyshoppy.app.util.Validation
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.partial_main_product_list_item.*

class HomeSuppliersViewHolder(override val containerView: View, val clickListener: IAdapterClickListener, var adapterType: String) : BaseViewholder(containerView), LayoutContainer {
    lateinit var category: Category
    override fun bind(context: Context, item: Any, pos: Int) {
        if (Validation.isValidObject(item)) {
            category = item as Category

            if (Validation.isValidString(category.pic))
                ImageUtils.setImageWithPicasso(context, image_product, category.pic)
            if (Validation.isValidString(category.name))
                tv_category_name.text = category.name
            if (category.isSelectedCategory) {
                image_product.borderColor = context.resources.getColor(R.color.colorPrimary)
            } else {
                image_product.borderColor = context.resources.getColor(R.color.colorTransparent)
            }


            if (Validation.isValidObject(clickListener)) {
                itemView.setOnClickListener {
                    if (Validation.isValidString(adapterType)) {

                        clickListener.onAdapterClick(category, adapterPosition, containerView, adapterType)

                    }
                }
            }
        }

    }
}