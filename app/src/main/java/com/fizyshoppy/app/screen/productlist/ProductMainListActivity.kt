package com.fizyshoppy.app.screen.productlist

import android.content.Context
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.fizyshoppy.app.R
import com.fizyshoppy.app.api.NetworkStatus
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.customview.MultiStateView
import com.fizyshoppy.app.model.Category
import com.fizyshoppy.app.model.ProductMainListRes
import com.fizyshoppy.app.screen.BaseActivity
import com.fizyshoppy.app.screen.commonadapter.ViewPagerAdapter
import com.fizyshoppy.app.screen.filter.FilterActivity
import com.fizyshoppy.app.screen.productlist.fragmennt.ProductListFragment
import com.fizyshoppy.app.util.CommonUtil
import com.fizyshoppy.app.util.Validation
import kotlinx.android.synthetic.main.activity_product_main_list.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.empty_view.*
import kotlinx.android.synthetic.main.error_view.*

class ProductMainListActivity : BaseActivity(), ProductListMainActivityContract.View, ViewPager.OnPageChangeListener {

    private val TAG = "ProductMainListActivity"
    private lateinit var mContext: Context
    private lateinit var source: String
    private lateinit var pagerAdapetr: ViewPagerAdapter
    private var first: Boolean? = true
    private lateinit var mResultProductMainList: ProductMainListRes
    private lateinit var productListFragment: ProductListFragment
    private lateinit var productMainListActivityPresenter: ProductMainListActivityPresenter
    private lateinit var mCur_Catgory: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_product_main_list, fragmentLayout)
        mContext = this
        showSearchView()
        hideBotomNaviagtionView()
        mContext = this

        if (Validation.isValidObject(intent.extras)) {
            val bundle = intent.extras
            if (bundle.getParcelable<Category>(Constants.EXTRA_ALL_SUB_CATEGORY_DATA) != null)
                mCur_Catgory = intent.extras.getParcelable(Constants.EXTRA_ALL_SUB_CATEGORY_DATA)

        }
        init()
    }

    override fun init() {
        productMainListActivityPresenter = ProductMainListActivityPresenter(this)
        callProductMainListApi()

        empty_button.setOnClickListener { callProductMainListApi() }
        error_button.setOnClickListener { callProductMainListApi() }
        tv_product_main_list.text = mCur_Catgory.name
    }


    private fun callProductMainListApi() {
        if (NetworkStatus.isOnline2(mContext)) {
            showViewState(MultiStateView.VIEW_STATE_LOADING)
            productMainListActivityPresenter.callProductMainListApi(mCur_Catgory._id /*"1"*/) // TODO (data is not there for some id)
        } else {
            showViewState(MultiStateView.VIEW_STATE_ERROR)
        }
    }

    override fun setProductMainListRes(responce: ProductMainListRes) {
        if (Validation.isValidStatus(responce)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT)
            this.mResultProductMainList = responce
            setupTabHeaders()
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR)

    }

    private fun setupTabHeaders() {

        if (Validation.isValidObject(mResultProductMainList) && Validation.isValidList(mResultProductMainList.result)) {
            pagerAdapetr = ViewPagerAdapter(supportFragmentManager)
            setCategory()
            cat_view_pager.adapter = pagerAdapetr
            tab_layout.setupWithViewPager(cat_view_pager)
            productMainMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
            cat_view_pager.addOnPageChangeListener(this)

        }
    }


    private fun setCategory() {
        for (cat in mResultProductMainList.result)
            pagerAdapetr.addList(ProductListFragment.newInstance(), cat.name)

    }


    override fun onPageScrollStateChanged(state: Int) {


    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (first!! && positionOffset == 0f && positionOffsetPixels == 0) {
            onPageSelected(0)
            first = false
        }
    }

    override fun onPageSelected(position: Int) {
        setupTabLayoutData(position)
    }

    override fun navigateFilterScreen() {
        val bundle = Bundle()
        bundle.putString(Constants.SOURCE, Constants.DASHBOARD)
        CommonUtil.startActivity(this@ProductMainListActivity, FilterActivity::class.java, bundle)

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


    private fun setupTabLayoutData(position: Int) {
        productListFragment = pagerAdapetr.getItem(position) as ProductListFragment
        if (Validation.isValidObject(productListFragment) && Validation.isValidObject(mResultProductMainList)) {
            if (Validation.isValidList(mResultProductMainList.result)) {
                val catid = mResultProductMainList.result[position]._id
                productListFragment.setupDataForProductList(catid)
            }
        }
    }
}
