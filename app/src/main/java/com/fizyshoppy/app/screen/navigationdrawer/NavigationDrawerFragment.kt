package com.fizyshoppy.app.screen.navigationdrawer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fizyshoppy.app.R
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.listners.IAdapterClickListener
import com.fizyshoppy.app.listners.OnFragmentInteractionListener
import com.fizyshoppy.app.model.NavigationDrawerModel
import com.fizyshoppy.app.screen.aboutus.AboutUsActivity
import com.fizyshoppy.app.screen.contactus.ContactUsActivity
import com.fizyshoppy.app.screen.myaccount.MyAccountActivity
import com.fizyshoppy.app.screen.privacypolicy.PrivacyPolicyActivity
import com.fizyshoppy.app.screen.purchasegiftcard.PurchaseGiftCardActivity
import com.fizyshoppy.app.screen.wishlist.WishListActivity
import com.fizyshoppy.app.util.CommonUtil
import com.fizyshoppy.app.util.Validation
import kotlinx.android.synthetic.main.fragment_navigation_drawer.view.*
import kotlinx.android.synthetic.main.item_subscription_newsletter.view.*
import kotlinx.android.synthetic.main.navigation_drawer_header.*
import java.util.*

class NavigationDrawerFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    lateinit var mActivity: FragmentActivity
    lateinit var mContext: Context
    lateinit var fragmentView: View
    lateinit var list: ArrayList<NavigationDrawerModel>
    lateinit var navListAdapter: NavigationDrawerListAdapter
    private var mListener: OnFragmentInteractionListener? = null
    private var mDrawerLayout: DrawerLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_navigation_drawer, container, false)

        return fragmentView
    }

    override fun onStart() {
        super.onStart()
        setUpNavigationList()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mActivity = requireActivity()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    companion object {

        @JvmStatic
        fun newInstance() =
                NavigationDrawerFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }

    fun setUpNavigationList() {
        //close_btn.visibility = View.GONE
        list = ArrayList()
        val titles = mActivity.resources.getStringArray(R.array.nav_list)
        val icons = resources.obtainTypedArray(R.array.nav_icon_list)


        for ((index, value) in titles.withIndex()) {
            var navModel = NavigationDrawerModel()
            navModel.title = value
            navModel.id = index

            if (index < icons.length())
                navModel.icon = ContextCompat.getDrawable(mContext, icons.getResourceId(index, -1))

            list.add(navModel)
        }


        navListAdapter = NavigationDrawerListAdapter(list, context = mContext, clickListener = object : IAdapterClickListener {

            override fun onAdapterClick(data: Any, pos: Int, view: View, adapterType: String) {
                val bundle = Bundle()
                bundle.putString(Constants.SOURCE, Constants.DASHBOARD)

                if (Validation.isValidString(list[pos].title)) {
                    val listItem = list[pos].title as String
                    when (listItem) {
                        getString(R.string.my_account) -> {
                            closeDrawer()
                            startActivity(Intent(activity, MyAccountActivity::class.java))
                        }

                        /* getString(R.string.purchage_gift_card) -> {
                             closeDrawer()
                             startActivity(PurchaseGiftCardActivity::class.java, bundle)

                         }*/

                        getString(R.string.subscribe_news_letter) -> {
                            closeDrawer()
                            showSubScriptionDialog()
                        }

                        getString(R.string.contact_us) -> {
                            closeDrawer()
                            startActivity(ContactUsActivity::class.java, bundle)

                        }


                        getString(R.string.gift_card) -> {
                            closeDrawer()
                            startActivity(PurchaseGiftCardActivity::class.java, bundle)
                        }

                        getString(R.string.aboutus) -> {
                            closeDrawer()
                            startActivity(AboutUsActivity::class.java, bundle)

                        }

                        getString(R.string.privacy_policy) -> {
                            closeDrawer()
                            startActivity(PrivacyPolicyActivity::class.java, bundle)

                        }

                        getString(R.string.my_wish_list) -> {
                            closeDrawer()
                            startActivity(WishListActivity::class.java, bundle)

                        }

                        getString(R.string.logout) -> {
                            CommonUtil.showDialog(mContext, getString(R.string.logout_message), Constants.FOR_LOG_OUT)
                        }
                    }
                }
            }

        })

        fragmentView.rv_drawer_item.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        //  rv_main_category.addItemDecoration(InsetDecoration(context))
        fragmentView.rv_drawer_item.adapter = navListAdapter
        fragmentView.rv_drawer_item.isNestedScrollingEnabled = false
        icons.recycle()


        close_btn.setOnClickListener { closeDrawer() }

    }

    private fun showSubScriptionDialog() {
        val builder = AlertDialog.Builder(mContext)
        val dialogView = LayoutInflater.from(mContext).inflate(R.layout.item_subscription_newsletter, null)

        builder.setView(dialogView)
        val dialog = builder.create()

        dialogView.btn_close_ad.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }


    fun setDrawer(drawerLayout: DrawerLayout) {
        mDrawerLayout = drawerLayout
    }


    fun closeDrawer() {
        if (mDrawerLayout != null)
            mDrawerLayout!!.closeDrawer(Gravity.START)
    }


    fun startActivity(activity: Class<*>, bundle: Bundle) {
        val move = Intent(mContext, activity)
        move.putExtras(bundle)
        startActivity(move)
        //    getActivity()!!.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

}
