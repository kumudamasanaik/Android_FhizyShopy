package com.fizyshoppy.app.screen.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.fizyshoppy.app.R
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.screen.home.HomeActivity
import com.fizyshoppy.app.screen.login.LoginActivity
import com.fizyshoppy.app.util.CommonUtil
import com.fizyshoppy.app.util.MyLogUtils

class SplashActivity : AppCompatActivity() {
    lateinit var runnable: Runnable
    lateinit var handler: Handler
    internal lateinit var mContext: Context
    var TAG: String = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh)
        mContext = this
    }

    override fun onResume() {
        super.onResume()
        navigate()
    }

    private fun navigate() {
        CommonUtil.setPincode("560075")

        Thread(Runnable {
            Thread.sleep(3000)
            this@SplashActivity.runOnUiThread(java.lang.Runnable {
                if (CommonUtil.isUserLogin()) {
                    MyLogUtils.d(TAG, "user is already Login")
                    navigateDashBoardScreen()
                } else {
                    MyLogUtils.d(TAG, "user is Not Login")
                    navigateLoginScreen()
                }

            })
        }).start()
    }

    private fun navigateDashBoardScreen() {
        startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
        finish()

    }

    private fun navigateLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_SPLASH)
        startActivity(intent)
        finish()

    }
}
