package com.fizyshoppy.app.screen.cart

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.fizyshoppy.app.R
import com.fizyshoppy.app.api.NetworkStatus
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.constants.Constants.Companion.WISHLIST_CREATE
import com.fizyshoppy.app.customview.MultiStateView
import com.fizyshoppy.app.listners.IAdapterClickListener
import com.fizyshoppy.app.model.CommonRes
import com.fizyshoppy.app.screen.BaseActivity
import com.fizyshoppy.app.screen.commonadapter.BaseRecyclerViewAdapter
import com.fizyshoppy.app.screen.home.HomeActivity
import com.fizyshoppy.app.screen.productdetail.ProductDetailActivity
import com.fizyshoppy.app.util.*
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.partial_botom_navigation.*

class CartActivity : BaseActivity(), CartActivityContract.View, IAdapterClickListener {

    private var mContext: Context? = null
    private lateinit var fragmentView: View
    private lateinit var cartActivityPresenter: CartActivityPresenter
    private lateinit var cartCategoryAdapter: BaseRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_cart, fragmentLayout)
        setTitle(getString(R.string.cart))
        mContext = this@CartActivity
        hideSearchView()
        init()

    }

    override fun onResume() {
        super.onResume()
        my_botom_navigation.selectedItemId = R.id.action_cart
    }

    override fun init() {
        cartActivityPresenter = CartActivityPresenter(this)
        setupCartProductRecyclerData()
        //  callFetchCartdApi()
    }

    override fun navigateDetailScreen() {
        val bundle = Bundle()
        bundle.putString(Constants.SOURCE, Constants.PRODUCT_DETAIL)
        CommonUtil.startActivity(mContext!!, ProductDetailActivity::class.java, bundle)
    }

    override fun callFetchCartdApi() {
        if (NetworkStatus.isOnline2(mContext)) {
            if (CommonUtil.isUserLogin()) {
                cartActivityPresenter.callFetchCartdApi()
            } else {
                error_imageView.setImageResource(R.drawable.usernotlogin)
                showViewState(MultiStateView.VIEW_STATE_ERROR)
            }
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR)
    }

    override fun setFetchCartApiRes(commonRes: CommonRes) {
        if (base_multistateview != null) {
            if (Validation.isValidObject(commonRes)) {
                showViewState(MultiStateView.VIEW_STATE_CONTENT)
                setData()
            } else
                showViewState(MultiStateView.VIEW_STATE_ERROR)
        }

    }

    override fun modifyCartApi() {
        if (NetworkStatus.isOnline2(mContext)) {
            showLoader()
            cartActivityPresenter.callModifyCart("modified")
        }

    }

    override fun setModifyCartApiResp(commonRes: CommonRes) {
        if (commonRes == null)
            showMsg(null)
        if (Validation.isValidObject(commonRes)) {
            TODO("MODIFY CART ") //To change body of created functions use File | Settings | File Templates.
        }


    }

    override fun callModifyWishListApi() {
        if (NetworkStatus.isOnline2(mContext)) {
            showLoader()
            cartActivityPresenter.callModifyWishList(WISHLIST_CREATE, "1")
        }
    }

    override fun setModifyWishListApiResp(commonRes: CommonRes) {
        if (commonRes == null)
            showMsg(null)
        if (Validation.isValidObject(commonRes)) {
            TODO("MODIFY WISH LIST ") //To change body of created functions use File | Settings | File Templates.
        }
    }

    override fun removeFromCart() {

        if (NetworkStatus.isOnline2(mContext)) {
            showLoader()
            cartActivityPresenter.callRemoveFromCart("1")
        }
    }

    override fun setRemoveFromCart(commonRes: CommonRes) {
        if (commonRes == null)
            showMsg(null)
        if (Validation.isValidObject(commonRes)) {
            TODO("UPDATE RESP FROM REMOVE FROM CART ") //To change body of created functions use File | Settings | File Templates.
        }
    }

    override fun showMsg(msg: String?) {
        showToastMsg(this, msg, 1)
    }

    override fun showLoader() {
        showProgressLoader()
    }

    override fun hideLoader() {
        hideProgressLoader()
    }

    override fun showViewState(state: Int) {
        if (base_multistateview != null)
            base_multistateview.viewState = state
    }

    override fun onAdapterClick(data: Any, pos: Int, view: View, adapterType: String) {
        TODO("Adapter cart item clicked need to implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun setupCartProductRecyclerData() {
        cartCategoryAdapter = BaseRecyclerViewAdapter(mContext!!, R.layout.adapter_my_cart_list_item, this)
        rv_cartproduct_list.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        rv_cartproduct_list.adapter = cartCategoryAdapter
        //fragmentView.rv_cartproduct_list.addItemDecoration(InsetDecoration(mContext!!, R.dimen.dimens_2))
        rv_cartproduct_list.isNestedScrollingEnabled = false
    }


    private fun setData() {

    }

    override fun onBackPressed() {
        super.onBackPressed()
        CommonUtil.startActivity(this, HomeActivity::class.java, bundle = Bundle())
        finishAffinity()
    }

}
