package com.fizyshoppy.app.screen.myaccount

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.fizyshoppy.app.R
import com.fizyshoppy.app.api.NetworkStatus
import com.fizyshoppy.app.customview.MultiStateView
import com.fizyshoppy.app.model.MyAccountRes
import com.fizyshoppy.app.model.MyAccountResult
import com.fizyshoppy.app.screen.BaseActivity
import com.fizyshoppy.app.screen.home.HomeActivity
import com.fizyshoppy.app.util.CommonUtil
import com.fizyshoppy.app.util.Validation
import kotlinx.android.synthetic.main.activity_my_account.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.empty_view.*
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.partial_botom_navigation.*

class MyAccountActivity : BaseActivity(), MyAccountActivityContract.View {

    private val TAG = "MyAccountActivity"
    private lateinit var mContext: Context
    private lateinit var mMyAccountResult: MyAccountResult
    private lateinit var myAccountActivityPresenter: MyAccountActivityPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_my_account, fragmentLayout)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setTitle(getString(R.string.my_account))
        mContext = this@MyAccountActivity
        hideSearchView()
        hideBotomNaviagtionView()

        setToolbarScrollFlags(false)
        init()
    }

    override fun onResume() {
        super.onResume()
        my_botom_navigation.selectedItemId = R.id.action_account
    }


    override fun callMyAccountScreenApi() {

        if (NetworkStatus.isOnline2(mContext)) {
            showViewState(MultiStateView.VIEW_STATE_LOADING)
            myAccountActivityPresenter.callMyAccountApi("2") // TODO (need to change this hard coded user id)

        } else {
            showViewState(MultiStateView.VIEW_STATE_ERROR)
        }

    }

    override fun setMyAccountRes(responce: MyAccountRes) {

        if (Validation.isValidStatus(responce)) {
            this.mMyAccountResult = responce.userprofile[0]
            showViewState(MultiStateView.VIEW_STATE_CONTENT)
            showData()
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR)

    }

    private fun showData() {

        mMyAccountResult.let {

            if (Validation.isValidString(mMyAccountResult.name))
                et_first_name.setText(mMyAccountResult.name)

            if (Validation.isValidString(mMyAccountResult.mobile))
                tv_mobile_number.setText(mMyAccountResult.mobile)

            if (Validation.isValidString(mMyAccountResult.email))
                tv_email.setText(mMyAccountResult.email)

            if (Validation.isValidString(mMyAccountResult.city))
                et_city.setText(mMyAccountResult.city)

            if (Validation.isValidString(mMyAccountResult.address))
                et_address_line_1.setText(mMyAccountResult.address)

            if (Validation.isValidString(mMyAccountResult.addressLine2))
                et_address_line_2.setText(mMyAccountResult.addressLine2)

            if (Validation.isValidString(mMyAccountResult.landmark))
                et_landmark.setText(mMyAccountResult.landmark)
        }

    }

    override fun init() {

        tv_mobile_number.isEnabled = false
        tv_email.isEnabled = false

        myAccountActivityPresenter = MyAccountActivityPresenter(this)
        callMyAccountScreenApi()

        empty_button.setOnClickListener { callMyAccountScreenApi() }
        error_button.setOnClickListener { callMyAccountScreenApi() }

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

    override fun onBackPressed() {
        super.onBackPressed()
        CommonUtil.startActivity(this, HomeActivity::class.java, bundle = Bundle())
        finishAffinity()
    }
}
