package com.fizyshoppy.app.screen.category

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.WindowManager
import com.fizyshoppy.app.R
import com.fizyshoppy.app.api.NetworkStatus
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.customview.MultiStateView
import com.fizyshoppy.app.listners.IAdapterClickListener
import com.fizyshoppy.app.model.AllCategoryResp
import com.fizyshoppy.app.model.Category
import com.fizyshoppy.app.model.SubCategoryResp
import com.fizyshoppy.app.screen.BaseActivity
import com.fizyshoppy.app.screen.category.adapter.SectionHeaderRecylerAdapter
import com.fizyshoppy.app.screen.commonadapter.BaseRecyclerViewAdapter
import com.fizyshoppy.app.screen.home.HomeActivity
import com.fizyshoppy.app.screen.productlist.ProductMainListActivity
import com.fizyshoppy.app.util.CommonUtil
import com.fizyshoppy.app.util.Validation
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.empty_view.*
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.partial_botom_navigation.*
import java.util.*

class CategoryActivity : BaseActivity(), CategoryActivityContract.View, IAdapterClickListener {


    private lateinit var mainCatAdapter: BaseRecyclerViewAdapter
    private lateinit var supplierAdapter: BaseRecyclerViewAdapter
    private lateinit var allCategoryAdapter: SectionHeaderRecylerAdapter
    private lateinit var mContext: Context
    private lateinit var categoryActivityPresenter: CategoryActivityPresenter
    private lateinit var subCategoryResp: SubCategoryResp
    private lateinit var allCategoryResp: AllCategoryResp
    private var selPos: Int = 0
    private lateinit var mainCategoryList: ArrayList<Category>
    private lateinit var mCurCategory: Category
    private lateinit var subCatId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_category, fragmentLayout)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setTitle(getString(R.string.category))
        mContext = this@CategoryActivity

        if (Validation.isValidObject(intent.extras)) {
            val bundle = intent.extras
            if (Validation.isValidObject(bundle.getParcelableArrayList<Category>(Constants.EXTRA_MAIN_CATEGORY_DATA)))
                mainCategoryList = intent.extras.getParcelableArrayList<Category>(Constants.EXTRA_MAIN_CATEGORY_DATA)
            else {
                if (CommonUtil.getMainCatListData().isNotEmpty()) {
                    showViewState(MultiStateView.VIEW_STATE_CONTENT)
                    mainCategoryList = CommonUtil.getMainCatListData() as java.util.ArrayList<Category>
                    mCurCategory = CommonUtil.getMainCatListData()[0]
                    mainCategoryList[0].isSelectedCategory = true
                    selPos = 0

                } else {
                    showViewState(MultiStateView.VIEW_STATE_ERROR)
                }
            }

            if (Validation.isValidObject(bundle.getParcelable<Category>(Constants.EXTRA_MAIN_CATEGORY_)))
                mCurCategory = intent.extras.getParcelable(Constants.EXTRA_MAIN_CATEGORY_)

            if (Validation.isValidObject(bundle.getParcelableArrayList<Category>(Constants.EXTRA_MAIN_CATEGORY_DATA)))
                selPos = intent.extras.getInt(Constants.SEL_POS, 0)
        }
        init()
    }

    override fun init() {
        categoryActivityPresenter = CategoryActivityPresenter(this)
        callSubCategoryApi()
        empty_button.setOnClickListener { callSubCategoryApi() }
        error_button.setOnClickListener { callSubCategoryApi() }
    }

    override fun onResume() {
        super.onResume()
        my_botom_navigation.selectedItemId = R.id.action_category
    }

    override fun showMsg(msg: String?) {
        CommonUtil.showToastMsg(mContext, msg, 1)

    }

    override fun showLoader() {
        CommonUtil.showLoading(mContext, true)

    }

    override fun hideLoader() {
        CommonUtil.hideLoading()
    }

    override fun showViewState(state: Int) {
        if (base_multistateview != null)
            base_multistateview.viewState = state
    }

    override fun callSubCategoryApi() {
        if (NetworkStatus.isOnline2(mContext) && Validation.isValidObject(mCurCategory)) {
            showViewState(MultiStateView.VIEW_STATE_LOADING)
            categoryActivityPresenter.callSubCategoryApi(mCurCategory._id)
        } else {
            showViewState(MultiStateView.VIEW_STATE_ERROR)
        }
    }

    override fun setCategoryApiRes(subCategoryResp: SubCategoryResp) {
        if (Validation.isValidObject(subCategoryResp)) {
            this.subCategoryResp = subCategoryResp
            showViewState(MultiStateView.VIEW_STATE_CONTENT)
            setData()
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR)

    }

    override fun callALLCategoryApi() {
        if (NetworkStatus.isOnline2(mContext) && Validation.isValidString(subCatId)) {
            showViewState(MultiStateView.VIEW_STATE_LOADING)
            categoryActivityPresenter.callAllCategoryApi(subCatId)

        } else {
            showViewState(MultiStateView.VIEW_STATE_ERROR)
        }
    }

    override fun setAllCategoryApiResp(allcatRes: AllCategoryResp) {
        if (Validation.isValidObject(subCategoryResp)) {
            this.allCategoryResp = allcatRes
            showViewState(MultiStateView.VIEW_STATE_CONTENT)
            setupAllCategory()
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR)
    }

    override fun onAdapterClick(data: Any, pos: Int, view: View, adapterType: String) {
        if (Validation.isValidObject(data) && Validation.isValidString(adapterType)) {
            if (adapterType.contentEquals(Constants.MAIN_CATEGORY)) {
                val category = data as Category
                if (Validation.isValidObject(mCurCategory) && !mCurCategory._id.contentEquals(category._id)) {
                    selPos = pos
                    for (item in mainCategoryList!!) {
                        if (item.isSelectedCategory)
                            item.isSelectedCategory = false
                    }
                    category.isSelectedCategory = true
                    this.mCurCategory = category
                    callSubCategoryApi()
                }
            }

            if (adapterType.contentEquals(Constants.HOME_SUPPLIES)) {
                val category = data as Category
                subCatId = category._id
                callALLCategoryApi()
            }

            if (adapterType.contentEquals(Constants.ALL_SUB_CATEGORY)) {
                val category = data as Category
                val bundle = Bundle()
                bundle.putParcelable(Constants.EXTRA_ALL_SUB_CATEGORY_DATA, category)
                CommonUtil.startActivity(context, ProductMainListActivity::class.java, bundle)
            }
        }
    }

    private fun setData() {
        setupMainCategory()
        setupHomeSuppliersData()
    }

    private fun setupMainCategory() {
        mainCatAdapter = BaseRecyclerViewAdapter(this, R.layout.partial_main_product_list_item, this, Constants.MAIN_CATEGORY)
        rv_main_category.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        CommonUtil.moveRecyclerViewItemToCenter(this, selPos, rv_main_category)
        rv_main_category.adapter = mainCatAdapter
        rv_main_category.isNestedScrollingEnabled = false
        mainCatAdapter.addList(mainCategoryList!!)
        if (Validation.isValidObject(mCurCategory) && Validation.isValidString(mCurCategory.name))
            tv_sub_cat_name.text = mCurCategory.name.toUpperCase()
    }

    private fun setupHomeSuppliersData() {
        val gridLayoutManager = GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false)
        supplierAdapter = BaseRecyclerViewAdapter(mContext, R.layout.partrial_home_suppliers_list_item, this, Constants.HOME_SUPPLIES)
        rv_home_suppliers.layoutManager = gridLayoutManager
        rv_home_suppliers.adapter = supplierAdapter
        rv_home_suppliers.isNestedScrollingEnabled = false
        if (Validation.isValidList(subCategoryResp.result))
            supplierAdapter.addList(subCategoryResp.result)
        layout_all_category.visibility = View.GONE
        layout_sub_category.visibility = View.VISIBLE

    }

    private fun setupAllCategory() {

        if (Validation.isValidObject(allCategoryResp.result)) {
            val gridLayoutManager = LinearLayoutManager(this)
            allCategoryAdapter = SectionHeaderRecylerAdapter(mContext, this, allCategoryResp.result, "GRID", Constants.ALL_SUB_CATEGORY)
            rv_all_category.layoutManager = gridLayoutManager
            rv_all_category.adapter = allCategoryAdapter
            rv_all_category.isNestedScrollingEnabled = false
            rv_all_category.setHasFixedSize(true)
            layout_all_category.visibility = View.VISIBLE
            layout_sub_category.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        CommonUtil.startActivity(this, HomeActivity::class.java, bundle = Bundle())
        finishAffinity()
    }
}
