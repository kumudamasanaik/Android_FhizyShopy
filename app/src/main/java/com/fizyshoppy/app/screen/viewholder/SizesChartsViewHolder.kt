package com.fizyshoppy.app.screen.viewholder

import android.content.Context
import android.graphics.Typeface
import android.view.View
import com.fizyshoppy.app.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.partial_mens_size_table_header.*


class SizesChartsViewHolder
(override val containerView: View) : BaseViewholder(containerView), LayoutContainer {
    override fun bind(context: Context, item: Any, pos: Int) {

        val typeFace = Typeface.createFromAsset(context.assets, "fonts/roboto_bold.ttf")


        ///   if(adapterPosition==0)   //// if size as a men show this design
        if (pos < 5) {
            tv_size.setBackgroundColor(context.resources.getColor(R.color.btn_bg))
            tv_size.setTextColor(context.resources.getColor(R.color.cardview_light_background))
        } else {
            tv_size.setBackgroundColor(context.resources.getColor(R.color.cardview_light_background))
            tv_size.setTextColor(context.resources.getColor(R.color.app_text_black))

        }
        tv_size.text = "Brand Size"


        ///   if(adapterPosition==0)   //// if size as a Women show this design


        if (pos < 5) {
            tv_size.setBackgroundColor(context.resources.getColor(R.color.cardview_light_background))
            tv_size.setTextColor(context.resources.getColor(R.color.app_text_black))
            tv_size.typeface = typeFace
        } else {
            tv_size.setBackgroundColor(context.resources.getColor(R.color.cardview_light_background))
            tv_size.setTextColor(context.resources.getColor(R.color.app_text_black))

        }


    }

}