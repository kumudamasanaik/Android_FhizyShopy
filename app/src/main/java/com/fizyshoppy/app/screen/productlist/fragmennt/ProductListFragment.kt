package com.fizyshoppy.app.screen.productlist.fragmennt

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fizyshoppy.app.R
import com.fizyshoppy.app.api.NetworkStatus
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.customview.GridDividerDecoration
import com.fizyshoppy.app.customview.MultiStateView
import com.fizyshoppy.app.listners.IAdapterClickListener
import com.fizyshoppy.app.listners.OnFragmentInteractionListener
import com.fizyshoppy.app.model.CommonRes
import com.fizyshoppy.app.model.Home
import com.fizyshoppy.app.model.ProductResponse
import com.fizyshoppy.app.screen.commonadapter.BaseRecyclerViewAdapter
import com.fizyshoppy.app.screen.filter.FilterActivity
import com.fizyshoppy.app.screen.productdetail.ProductDetailActivity
import com.fizyshoppy.app.util.CommonUtil
import com.fizyshoppy.app.util.Validation
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.fragment_product_list.view.*


class ProductListFragment : Fragment(), ProductListFragmentContract.View, IAdapterClickListener, View.OnClickListener {


    private lateinit var mContext: Context
    private lateinit var fragmentView: View
    private var listener: OnFragmentInteractionListener? = null
    lateinit var productListFragmentPresenter: ProductListFragmentPresenter
    private lateinit var catId: String
    private lateinit var productListAdapter: BaseRecyclerViewAdapter
    private var gridLayoutManager: GridLayoutManager?=null
    private var linearLayoutManager: LinearLayoutManager?=null
    private lateinit var mResp: ProductResponse

    private var isGridView: Boolean? = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_product_list, container, false)

        init()
        return fragmentView
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
        /*if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }*/
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    companion object {
        @JvmStatic
        fun newInstance() = ProductListFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }

    override fun init() {
        productListFragmentPresenter = ProductListFragmentPresenter(this)
        setupProductList()
        fragmentView.iv_switch_menu.setOnClickListener(this)
        fragmentView.iv_filter.setOnClickListener(this)

    }


    override fun callProductList() {
        if (NetworkStatus.isOnline2(mContext)) {
            productListFragmentPresenter.callProductListApi(/*catId*/"2276")
        } else {
            showViewState(MultiStateView.VIEW_STATE_ERROR)
        }
    }

    override fun setProductListApiRes(resp: CommonRes) {
        if (multistateview != null) {
            if (Validation.isValidObject(resp)) {
                this.mResp = resp as ProductResponse
                showViewState(MultiStateView.VIEW_STATE_CONTENT)
                setData()
            } else
                showViewState(MultiStateView.VIEW_STATE_ERROR)
        }
    }

    private fun setData() {
        if (Validation.isValidObject(mResp)) {
            productListAdapter.addList(mResp.product!!)
        }
    }

    override fun modifyCartApi() {
        if (NetworkStatus.isOnline2(mContext)) {
            showLoader()
            productListFragmentPresenter.callModifyCart("modified")
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
            productListFragmentPresenter.callModifyWishList(Constants.WISHLIST_CREATE, "1")//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    override fun setModifyWishListApiResp(commonRes: CommonRes) {
        if (commonRes == null)
            showMsg(null)
        if (Validation.isValidObject(commonRes)) {
            TODO("MODIFY WISH LIST ") //To change body of created functions use File | Settings | File Templates.
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
        if (multistateview != null)
            multistateview.viewState = state
    }


    override fun navigateDetailScreen() {
        val bundle = Bundle()
        bundle.putString(Constants.SOURCE, Constants.PRODUCT_DETAIL)
        CommonUtil.startActivity(mContext!!, ProductDetailActivity::class.java, bundle)
    }

    override fun onAdapterClick(data: Any, pos: Int, view: View, adapterType: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_filter -> {
                navigateFilterScreen()
            }

            R.id.iv_switch_menu -> {
                if (isGridView!!) {
                    iv_switch_menu.text = mContext.getString(R.string.grid)
                    iv_switch_menu.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_switch_menu, 0, 0)
                    isGridView = false
                } else {
                    iv_switch_menu.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_grid, 0, 0)
                    iv_switch_menu.text = mContext.getString(R.string.list)
                    isGridView = true
                }
                updateProductRecyclerview()
            }
        }
    }

    private fun updateProductRecyclerview() {
        var decoration: RecyclerView.ItemDecoration=  GridDividerDecoration(context)

        if (isGridView!!) {
            productListAdapter = BaseRecyclerViewAdapter(mContext!!, R.layout.partial_grid_product_list_item, this, Constants.UPDATE_PRODUCT)
            //fragmentView.rv_cartproduct_list.addItemDecoration(decoration)
            fragmentView.rv_cartproduct_list.layoutManager = gridLayoutManager

        } else {
            productListAdapter = BaseRecyclerViewAdapter(mContext!!, R.layout.partial_product_listview_item, this, Constants.PRODUCT_LIST)
            fragmentView.rv_cartproduct_list.layoutManager = linearLayoutManager

        }
        fragmentView.rv_cartproduct_list.adapter = productListAdapter

    }


    private fun setupProductList() {

        gridLayoutManager= GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false)
        linearLayoutManager  = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)



        if (isGridView!!) {
            productListAdapter = BaseRecyclerViewAdapter(mContext!!, R.layout.partial_grid_product_list_item, this, Constants.PRODUCT_LIST)
            fragmentView.rv_cartproduct_list.layoutManager = gridLayoutManager
        } else {
            productListAdapter = BaseRecyclerViewAdapter(mContext!!, R.layout.partial_product_listview_item, this, Constants.PRODUCT_LIST)
            fragmentView.rv_cartproduct_list.layoutManager = linearLayoutManager

        }

        fragmentView.rv_cartproduct_list.adapter = productListAdapter
    }


    fun setupDataForProductList(catId: String) {
        this.catId = catId
        callProductList()

    }

    fun navigateFilterScreen() {
        val bundle = Bundle()
        bundle.putString(Constants.SOURCE, Constants.DASHBOARD)
        CommonUtil.startActivity(mContext, FilterActivity::class.java, bundle)

    }


}
