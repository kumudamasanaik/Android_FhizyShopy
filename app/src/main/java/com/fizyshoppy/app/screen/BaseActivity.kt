package com.fizyshoppy.app.screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.fizyshoppy.AppController
import com.fizyshoppy.app.R
import com.fizyshoppy.app.customview.progressbar.BottomNavigationViewHelper
import com.fizyshoppy.app.screen.cart.CartActivity
import com.fizyshoppy.app.screen.category.CategoryActivity
import com.fizyshoppy.app.screen.home.HomeActivity
import com.fizyshoppy.app.screen.myaccount.MyAccountActivity
import com.fizyshoppy.app.screen.navigationdrawer.NavigationDrawerFragment
import com.fizyshoppy.app.screen.notification.NotificationActivity
import com.fizyshoppy.app.util.CommonUtil
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.layout_header.*
import kotlinx.android.synthetic.main.men_cart.*
import kotlinx.android.synthetic.main.partial_botom_navigation.*


open class BaseActivity : AppCompatActivity(), View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    var appbar: AppBarLayout? = null
    lateinit var context: Context
    var fragmentLayout: LinearLayout? = null
    var drawerLayout: DrawerLayout? = null
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        initBaseActivityData()

    }

    fun showBack() {
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        my_botom_navigation!!.visibility = View.GONE
        search_view!!.visibility = View.GONE


    }

    fun showMenu() {
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu_)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        //  bottomNavigation!!.visibility = View.GONE  ///based on visibility control botom navigation
        //  searchView!!.visibility = View.VISIBLE


    }

    fun showSearchView() {
        search_view!!.visibility = View.VISIBLE
    }


    fun hideSearchView() {
        search_view!!.visibility = View.GONE
    }


    fun hideBotomNaviagtionView() {
        my_botom_navigation!!.visibility = View.GONE
    }


    fun showBotomNavigation() {
        my_botom_navigation!!.visibility = View.GONE
    }


    fun initBaseActivityData() {


        context = this
        appbar = findViewById<View>(R.id.appbar) as AppBarLayout
        fragmentLayout = findViewById<View>(R.id.fragment_layout) as LinearLayout
        drawerLayout = findViewById<View>(R.id.my_drawer_layout) as DrawerLayout
        toolbar = findViewById<View>(R.id.my_tool_bar) as Toolbar

        BottomNavigationViewHelper.removeShiftMode(my_botom_navigation)
        my_botom_navigation!!.setOnNavigationItemSelectedListener(this)
        drawerLayout!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        setUpNavigationDrawer()
        setSupportActionBar(toolbar)
        showMenu()
        setTitle(getString(R.string.app_name))

        menu_cart_layout!!.setOnClickListener(this)
        notification_view!!.setOnClickListener(this)

    }

    private fun setUpNavigationDrawer() {
        lateinit var newFragment: Fragment
        val ft = supportFragmentManager.beginTransaction()
        newFragment = NavigationDrawerFragment.newInstance()
        ft.replace(R.id.navigation_drawer, newFragment).commit()
        newFragment.setDrawer(drawerLayout!!)

    }


    fun setTitle(title: String?) {
        if (title != null && !TextUtils.isEmpty(title) && title.isNotEmpty()) {
            menu_toolbar_title!!.visibility = View.VISIBLE
            menu_toolbar_title!!.text = title
        } else {
            menu_toolbar_title!!.visibility = View.GONE
        }
    }

    fun closeDrawer() {
        drawerLayout!!.closeDrawer(Gravity.START)
    }


    fun setToolbarHidden() {
        val params = toolbar!!.layoutParams as AppBarLayout.LayoutParams
        params.scrollFlags = 0
    }

    fun setToolbarScrollFlags(setFlags: Boolean = false) {
        val params: AppBarLayout.LayoutParams = toolbar!!.layoutParams as AppBarLayout.LayoutParams
        params.scrollFlags = if (setFlags) AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS else 0
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.menu_cart_layout -> {
                Toast.makeText(this, "Need to cart implement ", Toast.LENGTH_SHORT).show()
            }

            R.id.notification_view -> {
                Toast.makeText(this, "Need to notification implement ", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout!!.openDrawer(GravityCompat.START)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_home -> {
                if (!AppController.mActivityRefernce.contentEquals(HomeActivity::class.java.simpleName))
                    startActivity(Intent(this, HomeActivity::class.java))
            }
            R.id.action_category -> {
                if (!AppController.mActivityRefernce.contentEquals(CategoryActivity::class.java.simpleName))
                    CommonUtil.startActivity(this, CategoryActivity::class.java, bundle = Bundle())
            }
            R.id.action_cart -> {
                if (!AppController.mActivityRefernce.contentEquals(CartActivity::class.java.simpleName))
                    startActivity(Intent(this, CartActivity::class.java))
            }
            R.id.notifications -> {
                if (!AppController.mActivityRefernce.contentEquals(NotificationActivity::class.java.simpleName))
                    startActivity(Intent(this, NotificationActivity::class.java))

            }

            R.id.action_account -> {
                if (!AppController.mActivityRefernce.contentEquals(MyAccountActivity::class.java.simpleName))
                    startActivity(Intent(this, MyAccountActivity::class.java))
            }
        }
        return true
    }

    override fun finish() {
        super.finish()
        overridePendingTransitionExit()
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        overridePendingTransitionEnter()
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected fun overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected fun overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }
}
