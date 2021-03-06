package com.fizyshoppy.app.screen.privacypolicy

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.MenuItem
import android.webkit.*
import com.fizyshoppy.app.R
import com.fizyshoppy.app.api.NetworkStatus
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.customview.MultiStateView
import com.fizyshoppy.app.model.ContactRes
import com.fizyshoppy.app.screen.BaseActivity
import com.fizyshoppy.app.screen.home.HomeActivity
import com.fizyshoppy.app.util.CommonUtil
import com.fizyshoppy.app.util.Validation
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.empty_view.*
import kotlinx.android.synthetic.main.error_view.*

class PrivacyPolicyActivity : BaseActivity(), PrivacyPolicyActivityContract.View {

    private val TAG = "PrivacyPolicyActivity"
    private lateinit var mContext: Context
    private lateinit var mResult: ContactRes
    private lateinit var privacyPolicyActivityPresenter: PrivacyPolicyActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_privacy_policy, fragmentLayout)
        title = getString(R.string.privacy_policy)
        mContext = this
        showBack()
        setToolbarScrollFlags(false)
        init()
    }

    override fun callPrivacyPolicyScreenApi() {
        if (NetworkStatus.isOnline2(mContext)) {
            showViewState(MultiStateView.VIEW_STATE_LOADING)
            privacyPolicyActivityPresenter.callPrivacyPolicyAPi(Constants.PRIVACY_POLICY)
        } else {
            showViewState(MultiStateView.VIEW_STATE_ERROR)
        }
    }

    override fun init() {
        privacyPolicyActivityPresenter = PrivacyPolicyActivityPresenter(this)
        callPrivacyPolicyScreenApi()

        empty_button.setOnClickListener { callPrivacyPolicyScreenApi() }
        error_button.setOnClickListener { callPrivacyPolicyScreenApi() }
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

    override fun setPrivacyPolicyApiRes(response: ContactRes) {

        if (Validation.isValidObject(response)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT)
            this.mResult = response
            setupWebView()
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR)
    }

    private fun setupWebView() {
        webview.webViewClient = MyWebViewClient()
        webview.clearCache(true)
        webview.clearHistory()
        webview.settings.javaScriptEnabled = true
        webview.settings.javaScriptCanOpenWindowsAutomatically = false
        webview.settings.setSupportMultipleWindows(false)
        webview.settings.setSupportZoom(false)
        webview.settings.domStorageEnabled = true
        webview.isVerticalScrollBarEnabled = false
        webview.isHorizontalScrollBarEnabled = false
        webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                if (progress >= 95)
                    hideLoader()
                else
                    showLoader()
            }

            override fun onReceivedTitle(view: WebView, title: String) {
                //tvToolbarTitle.setText(title);
            }
        }

        if (Validation.isValidObject(mResult.result) && Validation.isValidString(mResult.result.value)) {
            webview.loadUrl("${Constants.pdfLoadUrl}${mResult.result.value}")
        }

    }

    private inner class MyWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onReceivedError(webView: WebView, reques: WebResourceRequest, error: WebResourceError) {
            super.onReceivedError(webView, reques, error)
            hideLoader()
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            showLoader()
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            hideLoader()
        }
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

    override fun onBackPressed() {
        super.onBackPressed()
        CommonUtil.startActivity(this, HomeActivity::class.java, bundle = Bundle())
        finishAffinity()
    }
}
