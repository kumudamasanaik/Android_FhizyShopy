package com.fizyshoppy.app.screen.checkout

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.WindowManager
import com.fizyshoppy.app.R
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.listners.IAdapterClickListener
import com.fizyshoppy.app.screen.BaseActivity
import com.fizyshoppy.app.screen.commonadapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_check_out.*

class CheckOutActivity : BaseActivity(), IAdapterClickListener {
    override fun onAdapterClick(data: Any, pos: Int, view: View, adapterType: String) {

    }

    private val TAG = "CheckOutActivity"
    private var mContext: Context? = null
    private lateinit var source: String
    private lateinit var checkOutAdapter: BaseRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_check_out, fragmentLayout)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setTitle(getString(R.string.tool_bar_tittle_check_out))
        setToolbarHidden()
        hideBotomNaviagtionView()
        hideSearchView()
        mContext = this@CheckOutActivity
        init()
    }

    private fun init() {
        initDesign()
    }

    private fun initDesign() {
        setupCheckOut()
    }

    private fun setupCheckOut() {
        checkOutAdapter = BaseRecyclerViewAdapter(this, R.layout.activity_wish_list, this, Constants.WISH_LIST)
        rv_product_check_out_list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        //  rv_main_category.addItemDecoration(InsetDecoration(context))
        rv_product_check_out_list.adapter = checkOutAdapter
        // rv_product_check_out_list.isNestedScrollingEnabled = false
    }
}
