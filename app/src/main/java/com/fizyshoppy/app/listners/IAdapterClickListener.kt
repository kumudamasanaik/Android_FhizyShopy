package com.fizyshoppy.app.listners

import android.view.View

/**
 * Created by FuGenX-14 on 23-07-2018.
 */
 public interface IAdapterClickListener {
    fun onAdapterClick(data: Any, pos: Int, view: View, adapterType: String)
}