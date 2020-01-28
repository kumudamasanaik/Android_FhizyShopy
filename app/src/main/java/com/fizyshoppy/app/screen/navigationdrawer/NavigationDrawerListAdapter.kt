package com.fizyshoppy.app.screen.navigationdrawer

import android.content.Context
import android.provider.SyncStateContract
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fizyshoppy.app.R
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.listners.IAdapterClickListener
import com.fizyshoppy.app.model.NavigationDrawerModel
import com.fizyshoppy.app.util.Validation

class NavigationDrawerListAdapter(var itemList: ArrayList<NavigationDrawerModel> = ArrayList(), var context: Context,var clickListener: IAdapterClickListener) : RecyclerView.Adapter<NavigationDrawerListAdapter.ViewHolder>() {


    fun addArrayList(itemList: ArrayList<NavigationDrawerModel>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drawer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = itemList[position]

        if (Validation.isValidObject(model)) {
            if (Validation.isValidString(model.title))
                holder.nav_title?.text = model.title
            if (model.icon != null)
                holder.nav_icon.setImageDrawable(model.icon)
        }

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                clickListener.onAdapterClick(itemList[position], position, view, Constants.NAVIGATION_CAT)
            }
        })

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nav_title = itemView.findViewById<TextView>(R.id.nav_title)
        val nav_icon = itemView.findViewById<AppCompatImageView>(R.id.nav_icon)
    }


}