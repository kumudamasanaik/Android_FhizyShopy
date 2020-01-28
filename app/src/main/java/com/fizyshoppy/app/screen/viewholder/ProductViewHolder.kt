package com.fizyshoppy.app.screen.viewholder

import android.content.Context
import android.view.View
import com.fizyshoppy.app.model.Product
import com.fizyshoppy.app.util.Validation
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.partial_grid_product_list_item.*

class ProductViewHolder (override val containerView: View) : BaseViewholder(containerView), LayoutContainer {
    private var isGridView: Boolean? = false

    override fun bind(context: Context, item: Any, pos: Int) {


        if(item is Product && Validation.isValidObject(item))
        {

            if(item.name.isNullOrEmpty())
                tv_product_name.text = item.name






        }

    }

}