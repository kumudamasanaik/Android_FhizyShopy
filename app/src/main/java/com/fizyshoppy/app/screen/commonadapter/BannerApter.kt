package com.fizyshoppy.app.screen.commonadapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fizyshoppy.app.R
import com.fizyshoppy.app.listners.IAdapterClickListener
import com.fizyshoppy.app.model.Banner
import com.fizyshoppy.app.util.ImageUtils
import com.fizyshoppy.app.util.Validation
import kotlinx.android.synthetic.main.partial_banner_list_item.view.*
import java.util.*


class BannerApter(context: Context, type: Int, clickListener: IAdapterClickListener? = null) : PagerAdapter() {

    val type = type
    var context = context
    var clickListener = clickListener
    lateinit var itemView: View
    internal var list = ArrayList<Any>()

    fun addList(list: List<Any>) {
        this.list = list as ArrayList<Any>
        notifyDataSetChanged()
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {

        if (list != null && list.size>0) {
            return list!!.size
        }

        return 6
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        when (type) {
            R.layout.partial_banner_list_item -> {
                itemView = LayoutInflater.from(context).inflate(type, container, false)
                if (Validation.isValidList(list)) {
                    val banner: Banner = list[0] as Banner
                    if (Validation.isValidString(banner.pic))
                        ImageUtils.setImageWithPicasso(context, itemView.banner_image, banner.pic)
                }
            }
        }
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}