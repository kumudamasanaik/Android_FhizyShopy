package com.fizyshoppy.app.screen.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.WindowManager
import com.fizyshoppy.app.R
import com.fizyshoppy.app.api.NetworkStatus
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.customview.MultiStateView
import com.fizyshoppy.app.listners.IAdapterClickListener
import com.fizyshoppy.app.model.Category
import com.fizyshoppy.app.model.Home
import com.fizyshoppy.app.screen.BaseActivity
import com.fizyshoppy.app.screen.category.CategoryActivity
import com.fizyshoppy.app.screen.commonadapter.BannerApter
import com.fizyshoppy.app.screen.commonadapter.BaseRecyclerViewAdapter
import com.fizyshoppy.app.util.CommonUtil
import com.fizyshoppy.app.util.Validation
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.empty_view.*
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.item_banner_layout.*
import kotlinx.android.synthetic.main.partial_botom_navigation.*

class HomeActivity : BaseActivity(), HomeActivityContract.View, IAdapterClickListener {

    private val TAG = "HomeActivity"
    private var mContext: Context? = null
    private lateinit var source: String
    private lateinit var popularProductsAdapter: BaseRecyclerViewAdapter
    private lateinit var mainCatAdapter: BaseRecyclerViewAdapter
    private lateinit var bannerApter: BannerApter
    private lateinit var homeActivityPresenter: HomeActivityPresenter
    private lateinit var home: Home
    private var backPressedToExitOnce = false

    lateinit var mainCategoryData: ArrayList<Category>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_home, fragmentLayout)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setTitle(getString(R.string.tool_bar_tittle))
        mContext = this@HomeActivity
        init()
    }

    override fun onAdapterClick(data: Any, pos: Int, view: View, adapterType: String) {
        if (Validation.isValidObject(data) && Validation.isValidString(adapterType) && adapterType.contentEquals(Constants.MAIN_CATEGORY)) {
            if (Validation.isValidObject(home) && Validation.isValidList(home.categories)) {
                val category = data as Category
                val bundle = Bundle()

                for (item in home.categories) {
                    if (item.isSelectedCategory)
                        item.isSelectedCategory = false
                }
                category.isSelectedCategory = true

                bundle.putParcelable(Constants.EXTRA_MAIN_CATEGORY_, category)
                bundle.putString(Constants.EXTRA_MAIN_CATEGORY_ID, data._id)
                bundle.putInt(Constants.SEL_POS, pos)
                bundle.putParcelableArrayList(Constants.EXTRA_MAIN_CATEGORY_DATA, home.categories as ArrayList<Category>)
                CommonUtil.startActivity(context, CategoryActivity::class.java, bundle)
            }
        }
    }

    override fun init() {
        homeActivityPresenter = HomeActivityPresenter(this)
        callHomeScreenApi()
        initDesign()

    }

    override fun onResume() {
        super.onResume()
        my_botom_navigation.selectedItemId = R.id.action_home
    }


    override fun showMsg(msg: String?) {
        CommonUtil.showToastMsg(context, msg, 1)

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

    override fun callHomeScreenApi() {
        if (NetworkStatus.isOnline2(mContext)) {
            showViewState(MultiStateView.VIEW_STATE_LOADING)
            if (CommonUtil.isUserLogin()) {
                homeActivityPresenter.callHomeAPi(this)
            } else {
                showViewState(MultiStateView.VIEW_STATE_ERROR)
            }

        } else {
            showViewState(MultiStateView.VIEW_STATE_ERROR)
        }

    }

    override fun setDashboardApiRes(homeResp: Home) {
        if (base_multistateview != null) {

            if (Validation.isValidObject(homeResp)) {
                home = homeResp
                showViewState(MultiStateView.VIEW_STATE_CONTENT)
                setData()
            } else
                showViewState(MultiStateView.VIEW_STATE_ERROR)
        }
    }

    private fun setData() {

        if (Validation.isValidList(home.banners))
            bannerApter.addList(home.banners)

        if (Validation.isValidList(home.categories)) {
            mainCatAdapter.addList(home.categories)
            CommonUtil.saveMainCatList(home.categories)
            /** adding main category data to shared preference*/
        }

        if (Validation.isValidList(home.popularproducts))
            popularProductsAdapter.addList(home.popularproducts)
    }

    private fun initDesign() {
        setupBannerViewHolder()
        setupMainCategory()
        setupPopularProducts()
        empty_button.setOnClickListener { callHomeScreenApi() }
        error_button.setOnClickListener { callHomeScreenApi() }
    }

    private fun setupBannerViewHolder() {
        bannerApter = BannerApter(this, R.layout.partial_banner_list_item)
        main_banner_pager.adapter = bannerApter
        main_banner_indicator.setViewPager(main_banner_pager)
    }

    private fun setupMainCategory() {
        mainCatAdapter = BaseRecyclerViewAdapter(this, R.layout.partial_main_product_list_item, this, Constants.MAIN_CATEGORY)
        rv_main_category.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_main_category.adapter = mainCatAdapter
    }


    private fun setupPopularProducts() {
        var gridLayoutManager = GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false)
        popularProductsAdapter = BaseRecyclerViewAdapter(this, R.layout.partial_popular_product_list_item, this)
        rv_popular_products.layoutManager = gridLayoutManager
        rv_popular_products.adapter = popularProductsAdapter
    }

    override fun onBackPressed() {
        if (backPressedToExitOnce) {
            super.onBackPressed()
        } else {
            backPressedToExitOnce = true
            CommonUtil.showDialog(this, getString(R.string.exit_message), Constants.FOR_EXIT)
            Handler().postDelayed({ backPressedToExitOnce = false }, 2000)
        }
    }
}
