package com.fizyshoppy.app.screen.wishlist

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.fizyshoppy.app.R
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.listners.IAdapterClickListener
import com.fizyshoppy.app.screen.BaseActivity
import com.fizyshoppy.app.screen.commonadapter.BaseRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_wish_list.*
import kotlinx.android.synthetic.main.empty_view.*
import kotlinx.android.synthetic.main.error_view.*

class WishListActivity : BaseActivity(), IAdapterClickListener, WishListActivityContract.View {

    private val TAG = "CheckOutActivity"
    private var mContext: Context? = null
    private lateinit var source: String
    private lateinit var wishListAdapter: BaseRecyclerViewAdapter
    private lateinit var wishListActivityPresenter: WishListActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_wish_list, fragmentLayout)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setTitle(getString(R.string.tool_bar_tittle_my_wish_list))
        setToolbarHidden()
        hideBotomNaviagtionView()
        hideSearchView()
        showBack()
        mContext = this@WishListActivity
        init()
    }

    override fun onAdapterClick(data: Any, pos: Int, view: View, adapterType: String) {

    }

    override fun init() {
        initApi()
        initDesign()
    }

    private fun initApi() {
        wishListActivityPresenter = WishListActivityPresenter(this)
//        callGetWishListScreenApi()

        empty_button.setOnClickListener { callGetWishListScreenApi() }
        error_button.setOnClickListener { callGetWishListScreenApi() }
    }

    override fun callGetWishListScreenApi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMsg(msg: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoader() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoader() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showViewState(state: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initDesign() {
        setupCheckOut()
    }

    private fun setupCheckOut() {
        wishListAdapter = BaseRecyclerViewAdapter(this, R.layout.adapter_my_cart_list_item, this, Constants.CHECKOUT)
        rv_wish_list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        //  rv_main_category.addItemDecoration(InsetDecoration(context))
        rv_wish_list.adapter = wishListAdapter
//        rv_wish_list.isNestedScrollingEnabled = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
