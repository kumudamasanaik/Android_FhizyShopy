package com.fizyshoppy.app.screen.purchasegiftcard

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.fizyshoppy.app.R
import com.fizyshoppy.app.api.NetworkStatus
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.customview.MultiStateView
import com.fizyshoppy.app.model.PurchaseGiftCardRes
import com.fizyshoppy.app.screen.BaseActivity
import com.fizyshoppy.app.screen.commonadapter.BannerApter
import com.fizyshoppy.app.util.CommonUtil
import com.fizyshoppy.app.util.Validation
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.empty_view.*
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.item_banner_layout.*

class PurchaseGiftCardActivity : BaseActivity(), PurchaseGiftCardActivityContract.View {

    private val TAG = "PurchaseGiftCardActivity"
    private lateinit var mContext: Context
    private lateinit var source: String
    private lateinit var bannerApter: BannerApter
    private lateinit var mPurchaseGiftCardRes: PurchaseGiftCardRes
    private lateinit var purchaseGiftCardActivityPresenter: PurchaseGiftCardActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_purchase_gift_card, fragmentLayout)
        setTitle(getString(R.string.purchage_gift_card))
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        mContext = this
        showSearchView()
        hideBotomNaviagtionView()
        init()
    }

    override fun init() {
        initDesign()

        purchaseGiftCardActivityPresenter = PurchaseGiftCardActivityPresenter(this)
        // callPurchaseGiftCardScreenApi()

        empty_button.setOnClickListener { callPurchaseGiftCardScreenApi() }
        error_button.setOnClickListener { callPurchaseGiftCardScreenApi() }

    }

    override fun callPurchaseGiftCardScreenApi() {

        if (NetworkStatus.isOnline2(mContext)) {
            showViewState(MultiStateView.VIEW_STATE_LOADING)
            purchaseGiftCardActivityPresenter.callPurchaseGiftCardApi(Constants.PURCHASE_GIFT_CARD)

        } else {
            showViewState(MultiStateView.VIEW_STATE_ERROR)
        }

    }

    override fun setPurchaseGiftCardRes(responce: PurchaseGiftCardRes) {

        if (Validation.isValidStatus(responce)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT)
            this.mPurchaseGiftCardRes = responce
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR)

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


    private fun initDesign() {
        setupBannerViewHolder()
    }

    private fun setupBannerViewHolder() {
        bannerApter = BannerApter(this, R.layout.partial_banner_list_item)
        main_banner_pager.adapter = bannerApter
        main_banner_indicator.setViewPager(main_banner_pager)
    }
}
