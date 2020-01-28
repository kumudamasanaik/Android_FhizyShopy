package com.fizyshoppy.app.screen.sizechart

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import android.view.View
import com.fizyshoppy.app.R
import com.fizyshoppy.app.api.NetworkStatus
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.customview.MultiStateView
import com.fizyshoppy.app.listners.IAdapterClickListener
import com.fizyshoppy.app.model.CustomerRes
import com.fizyshoppy.app.screen.BaseActivity
import com.fizyshoppy.app.screen.commonadapter.BaseRecyclerViewAdapter
import com.fizyshoppy.app.util.CommonUtil
import com.fizyshoppy.app.util.Validation
import kotlinx.android.synthetic.main.activity_size.*
import kotlinx.android.synthetic.main.app_bar_main.*

class SizeChartActivity : BaseActivity(), SizeActivityContract.View, IAdapterClickListener {
    override fun onAdapterClick(data: Any, pos: Int, view: View, adapterType: String) {

    }

    private val TAG = "SizeChartActivity"
    private lateinit var mContext: Context
    private lateinit var source: String
    private lateinit var sizeActivityPresenter: SizeActivityPresenter
    private lateinit var sizeChartAdapter: BaseRecyclerViewAdapter
    private var categoryArrayList: Array<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_size, fragmentLayout)
        mContext = this
        setTitle(Constants.SIZE_CHART)
        showBack()

        mContext = this
        /*  if (intent != null) {
              source = intent.getStringExtra(Constants.SOURCE)
              MyLogUtils.d(TAG, source)
          }*/
        init()
    }

    override fun init() {
        sizeActivityPresenter = SizeActivityPresenter(this)
        // callSizeListApi()
        setupSizeRecyclreViewData()
    }

    private fun setupSizeRecyclreViewData() {

        var gridLayoutManager = GridLayoutManager(mContext, 5, GridLayoutManager.VERTICAL, false)
        sizeChartAdapter = BaseRecyclerViewAdapter(mContext, R.layout.partial_mens_size_table_header, this, Constants.SIZE_CHART)
        rv_men_size.setHasFixedSize(true)
        rv_men_size.layoutManager = gridLayoutManager
        rv_men_size.adapter = sizeChartAdapter
        rv_men_size.isNestedScrollingEnabled = false
        //sizeChartAdapter.addList(listData())


    }

    override fun callSizeListApi() {
        if (NetworkStatus.isOnline2(mContext)) {

            if (CommonUtil.isUserLogin()) {
                sizeActivityPresenter.callSizeChartApi()
            } else {
                showViewState(MultiStateView.VIEW_STATE_ERROR)
            }

        } else {
            showViewState(MultiStateView.VIEW_STATE_ERROR)
        }
    }

    override fun setSizeListApiResp(commonRes: CustomerRes) {
        if (base_multistateview != null) {
            if (Validation.isValidObject(commonRes)) {
                showViewState(MultiStateView.VIEW_STATE_CONTENT)
                setData()
            } else
                showViewState(MultiStateView.VIEW_STATE_ERROR)
        }
    }


    override fun showMsg(msg: String?) {
        CommonUtil.showToastMsg(mContext, msg, 1)
    }

    override fun showLoader() {
        CommonUtil.showLoading(mContext, false)
    }

    override fun hideLoader() {
        CommonUtil.hideLoading()
    }

    override fun showViewState(state: Int) {
        if (base_multistateview != null)
            base_multistateview.viewState = state
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


    fun listData(): ArrayList<String> {
        categoryArrayList = resources.getStringArray(R.array.dummy_size_header)
        val brandsList = ArrayList<String>()
        for (cat in categoryArrayList!!)
            brandsList.add(cat)
        return brandsList
    }


    private fun setData() {


    }

}
