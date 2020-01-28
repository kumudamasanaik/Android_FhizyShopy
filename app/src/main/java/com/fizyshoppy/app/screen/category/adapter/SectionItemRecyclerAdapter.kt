package com.fizyshoppy.app.screen.category.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.fizyshoppy.app.R
import com.fizyshoppy.app.listners.IAdapterClickListener
import com.fizyshoppy.app.model.Category
import com.fizyshoppy.app.util.ImageUtils
import com.fizyshoppy.app.util.Validation
import java.util.*

class SectionItemRecyclerAdapter(var context: Context, var clickListener: IAdapterClickListener,
                                 var sectionModelArrayList: ArrayList<Category>,val adaptertype: String) : RecyclerView.Adapter<SectionItemRecyclerAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView = itemView.findViewById(R.id.image_product) as ImageView
        val cat_name: TextView = itemView.findViewById(R.id.tv_category_name) as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionItemRecyclerAdapter.ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.partial_main_product_list_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sectionModelArrayList.size
    }

    override fun onBindViewHolder(holder: SectionItemRecyclerAdapter.ItemViewHolder, position: Int) {
        val sectionModel = sectionModelArrayList[position]
        if (Validation.isValidString(sectionModel.name))
            holder.cat_name.text = sectionModel.name

        if (Validation.isValidString(sectionModel.pic))
            ImageUtils.setImageWithPicasso(context, holder.image, sectionModel.pic)

        holder.itemView.setOnClickListener {
            if(Validation.isValidObject(clickListener))
                clickListener.onAdapterClick(sectionModel,holder.adapterPosition,holder.itemView,adaptertype)

        }
    }
}