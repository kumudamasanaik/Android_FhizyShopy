package com.fizyshoppy.app.screen.category.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fizyshoppy.app.R
import com.fizyshoppy.app.constants.Constants.Companion.GRID
import com.fizyshoppy.app.listners.IAdapterClickListener
import com.fizyshoppy.app.model.AllCategoryRespData
import com.fizyshoppy.app.util.Validation
import java.util.*

class SectionHeaderRecylerAdapter(var context: Context, private var clickListener: IAdapterClickListener,
                                  private var sectionModelArrayList: ArrayList<AllCategoryRespData>, val recyclerType: String,val adaptertype: String) : RecyclerView.Adapter<SectionHeaderRecylerAdapter.SectionViewHolder>() {

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sectionLabel: TextView = itemView.findViewById(R.id.section_label) as TextView
        val layoutview: View = itemView.findViewById(R.id.layout_view) as View
        val itemRecyclerView: RecyclerView = itemView.findViewById(R.id.item_recycler_view) as RecyclerView

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionHeaderRecylerAdapter.SectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.section_custom_row_layout, parent, false)
        return SectionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sectionModelArrayList.size
    }

    override fun onBindViewHolder(holder: SectionHeaderRecylerAdapter.SectionViewHolder, position: Int) {
        val sectionModel = sectionModelArrayList[position]
        if (Validation.isValidString(sectionModel.catname))
            holder.sectionLabel.text = sectionModel.catname
        when (recyclerType) {
            GRID -> {
                val gridLayoutManager = GridLayoutManager(context, 3)
                holder.itemRecyclerView.layoutManager = gridLayoutManager
            }
        }

        holder.layoutview.setOnClickListener {
            if (holder.itemRecyclerView.visibility == View.VISIBLE)
                holder.itemRecyclerView.visibility = View.GONE
            else
                holder.itemRecyclerView.visibility = View.VISIBLE
        }

        if (Validation.isValidList(sectionModel.subcategory)) {
            val gridLayoutManager = GridLayoutManager(context, 3)
            holder.itemRecyclerView.layoutManager = gridLayoutManager
            val adapter = SectionItemRecyclerAdapter(context, clickListener, sectionModel.subcategory,adaptertype)
            holder.itemRecyclerView.adapter = adapter
        }
    }
}