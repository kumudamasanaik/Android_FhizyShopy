package com.fizyshoppy.app.screen.notification

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.android.dhukan.customview.InsetDecoration
import com.fizyshoppy.app.R
import com.fizyshoppy.app.api.NetworkStatus
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.customview.MultiStateView
import com.fizyshoppy.app.listners.IAdapterClickListener
import com.fizyshoppy.app.model.NotificationRes
import com.fizyshoppy.app.screen.BaseActivity
import com.fizyshoppy.app.screen.commonadapter.BaseRecyclerViewAdapter
import com.fizyshoppy.app.screen.home.HomeActivity
import com.fizyshoppy.app.util.CommonUtil
import com.fizyshoppy.app.util.Validation
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.empty_view.*
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.partial_botom_navigation.*

class NotificationActivity : BaseActivity(), NotificationActivityContract.View, IAdapterClickListener {

    private val TAG = "NotificationActivity"
    private lateinit var mContext: Context

    private var mNotificationRes: NotificationRes? = null
    private lateinit var notificationActivityPresenter: NotificationActivityPresenter

    private lateinit var notificationAdapter: BaseRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_notification, fragmentLayout)
        setTitle(getString(R.string.notification))
        mContext = this@NotificationActivity
        hideSearchView()

        init()
    }


    override fun onResume() {
        super.onResume()
        my_botom_navigation.selectedItemId = R.id.notifications
    }

    override fun onAdapterClick(data: Any, pos: Int, view: View, adapterType: String) {

    }

    override fun callNotificationScreenApi() {

        if (NetworkStatus.isOnline2(mContext)) {
            showViewState(MultiStateView.VIEW_STATE_LOADING)
            notificationActivityPresenter.callNotificationApi(Constants.NOTIFICATION)

        } else {
            showViewState(MultiStateView.VIEW_STATE_ERROR)
        }

    }

    override fun setNotificationRes(responce: NotificationRes) {

        if (Validation.isValidStatus(responce)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT)
            mNotificationRes = responce
            setupRecyclerView()
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR)
    }

    override fun init() {
        notificationActivityPresenter = NotificationActivityPresenter(this)
        // callNotificationScreenApi()

        empty_button.setOnClickListener { callNotificationScreenApi() }
        error_button.setOnClickListener { callNotificationScreenApi() }

        setupRecyclerView()
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

    private fun setupRecyclerView() {
        notificationAdapter = BaseRecyclerViewAdapter(this, R.layout.item_notification, this, Constants.NOTIFICATION)
        rv_notification.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_notification.addItemDecoration(InsetDecoration(context))
        rv_notification.adapter = notificationAdapter
        rv_notification.isNestedScrollingEnabled = false

        if (Validation.isValidObject(mNotificationRes) && Validation.isValidList(mNotificationRes!!.result))
            notificationAdapter.addList(mNotificationRes!!.result)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        CommonUtil.startActivity(this, HomeActivity::class.java, bundle = Bundle())
        finishAffinity()
    }
}
