package com.android.dhukan.customview

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View


import com.fizyshoppy.app.R


/**
 * ItemDecoration implementation that applies an inset margin
 * around each child of the RecyclerView. The inset value is controlled
 * by a dimension resource.
 */
class InsetDecoration(context: Context, dimenId: Int = R.dimen.card_insets) : RecyclerView.ItemDecoration() {


    private var mInsets = dimenId

    init {
        mInsets = context.resources.getDimensionPixelSize(dimenId)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        //We can supply forced insets for each item view here in the Rect
        outRect.set(1, mInsets, 1, mInsets)
    }


}
