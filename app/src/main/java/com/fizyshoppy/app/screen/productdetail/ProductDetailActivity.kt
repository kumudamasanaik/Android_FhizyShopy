package com.fizyshoppy.app.screen.productdetail

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.fizyshoppy.app.R
import com.fizyshoppy.app.api.NetworkStatus
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.customview.MultiStateView
import com.fizyshoppy.app.listners.IAdapterClickListener
import com.fizyshoppy.app.model.ProductDetailRes
import com.fizyshoppy.app.screen.BaseActivity
import com.fizyshoppy.app.screen.commonadapter.BannerApter
import com.fizyshoppy.app.screen.commonadapter.BaseRecyclerViewAdapter
import com.fizyshoppy.app.screen.sizechart.SizeChartActivity
import com.fizyshoppy.app.util.CommonUtil
import com.fizyshoppy.app.util.Validation
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.empty_view.*
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.item_banner_layout.*

class ProductDetailActivity : BaseActivity(), IAdapterClickListener, ProductDetailActivityContract.View {

    private lateinit var productDetailActivityPresenter: ProductDetailActivityPresenter
    private lateinit var productDetailRes: ProductDetailRes

    private val TAG = "ProductDetailActivity"
    private lateinit var mContext: Context
    private lateinit var source: String

    private lateinit var similarProductsAdapter: BaseRecyclerViewAdapter
    private lateinit var inStockAdapter: BaseRecyclerViewAdapter
    private lateinit var sizeAdapter: BaseRecyclerViewAdapter

    private lateinit var bannerApter: BannerApter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_product_detail, fragmentLayout)
        setTitle(getString(R.string.detail))
        mContext = this
        showBack()

        init()
        initDesign()

        tv_size_card.setOnClickListener { navigateToSizeCardActivity() }

    }

    override fun onAdapterClick(data: Any, pos: Int, view: View, adapterType: String) {

    }

    private fun navigateToSizeCardActivity() {
        CommonUtil.startActivity(mContext, SizeChartActivity::class.java, bundle = Bundle())
    }

    private fun initDesign() {
        setupBannerViewHolder()
        setupInStock()
        setupSize()
        setupSimilarProducts()
    }


    override fun callProductDetailScreenApi() {
        if (NetworkStatus.isOnline2(mContext)) {
            showViewState(MultiStateView.VIEW_STATE_LOADING)
            productDetailActivityPresenter.callProductDetailApi(Constants.PRODUCT_DETAIL)

        } else {
            showViewState(MultiStateView.VIEW_STATE_ERROR)
        }
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setProductDetailRes(responce: ProductDetailRes) {

        if (Validation.isValidStatus(responce)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT)
            this.productDetailRes = responce
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR)

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun init() {
        productDetailActivityPresenter = ProductDetailActivityPresenter(this)
        // callProductDetailScreenApi()

        empty_button.setOnClickListener { callProductDetailScreenApi() }
        error_button.setOnClickListener { callProductDetailScreenApi() }
    }

    override fun showMsg(msg: String?) {
        CommonUtil.showToastMsg(this, msg, 1)
    }

    override fun showLoader() {
        CommonUtil.showLoading(context, true)
    }

    override fun hideLoader() {
        CommonUtil.hideLoading()
    }

    override fun showViewState(state: Int) {
        if (base_multistateview != null)
            base_multistateview.viewState = state
    }


    private fun setupBannerViewHolder() {
        bannerApter = BannerApter(this, R.layout.partial_banner_list_item)
        main_banner_pager.adapter = bannerApter
        main_banner_indicator.setViewPager(main_banner_pager)

    }

    private fun setupInStock() {
        inStockAdapter = BaseRecyclerViewAdapter(mContext, R.layout.partial_stock, this, Constants.PRODUCT_DETAIL)
        rv_in_stock.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_in_stock.adapter = inStockAdapter
        rv_in_stock.isNestedScrollingEnabled = false
    }

    private fun setupSize() {
        sizeAdapter = BaseRecyclerViewAdapter(mContext, R.layout.partial_size, this, Constants.SIZE_CHART)
        rv_size.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_size.adapter = sizeAdapter
        rv_size.isNestedScrollingEnabled = false
    }


    private fun setupSimilarProducts() {
        var gridLayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        similarProductsAdapter = BaseRecyclerViewAdapter(this, R.layout.partial_similar_item, this, Constants.SIMILAR_PRODUCT)
        rv_similar_products.layoutManager = gridLayoutManager
        // rv_popular_products.addItemDecoration(GridDividerDecoration(context))
        rv_similar_products.adapter = similarProductsAdapter
        rv_similar_products.isNestedScrollingEnabled = false
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
