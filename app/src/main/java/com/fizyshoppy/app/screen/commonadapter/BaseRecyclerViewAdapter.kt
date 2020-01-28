package com.fizyshoppy.app.screen.commonadapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fizyshoppy.app.R
import com.fizyshoppy.app.listners.IAdapterClickListener
import com.fizyshoppy.app.screen.viewholder.*
import com.fizyshoppy.app.util.Validation
import inflate
import java.util.*


class BaseRecyclerViewAdapter(context: Context, type: Int, var clickListener: IAdapterClickListener, var adapterType: String = "common") : RecyclerView.Adapter<BaseViewholder>() {
    var context = context
    var type = type
    internal var list = ArrayList<Any>()


    //var list: List<*>? = null
    fun addList(list: List<Any>) {
        this.list = list as ArrayList<Any>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewholder {
        var view = parent.inflate(type)
        lateinit var holder: BaseViewholder
        when (type) {
            R.layout.partial_main_product_list_item -> {
                holder = MaincatViewholder(view, clickListener, adapterType)
            }

            R.layout.adapter_checkout_list_item -> {
                holder = MaincatViewholder(view, clickListener, adapterType)
            }

            R.layout.partrial_home_suppliers_list_item -> {
                holder = HomeSuppliersViewHolder(view, clickListener, adapterType)
            }

            R.layout.activity_wish_list -> {
                holder = MaincatViewholder(view, clickListener, adapterType)
            }


            R.layout.item_notification -> {
                holder = MaincatViewholder(view, clickListener, adapterType)
            }

            R.layout.partial_popular_product_list_item -> {
                holder = PopularProductsViewHolder(view)
            }

            R.layout.adapter_my_cart_list_item -> {
                holder = ProductViewHolder(view)
            }

            R.layout.partial_grid_product_list_item -> {
                holder = ProductViewHolder(view)
            }

            R.layout.partial_product_listview_item -> {
                holder = ProductViewHolder(view)
            }

            R.layout.partial_mens_size_table_header -> {
                holder = SizesChartsViewHolder(view)
            }

            R.layout.partial_stock -> {
                holder = ProductViewHolder(view)
            }
            R.layout.partial_size -> {
                holder = ProductViewHolder(view)
            }
            R.layout.partial_similar_item -> {
                holder = ProductViewHolder(view)
            }


        }
        return holder
    }

    override fun getItemCount(): Int {
        if (list != null && list.size > 0) {
            return list!!.size
        }
        return 20
    }

    override fun onBindViewHolder(holder: BaseViewholder, position: Int) {
        if (Validation.isValidList(list))
            holder.bind(context, list[holder.adapterPosition], holder.adapterPosition)
    }

}