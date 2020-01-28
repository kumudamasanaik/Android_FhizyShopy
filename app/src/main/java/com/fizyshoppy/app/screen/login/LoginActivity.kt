package com.fizyshoppy.app.screen.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.fizyshoppy.app.R
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.model.CustomerRes
import com.fizyshoppy.app.screen.home.HomeActivity
import com.fizyshoppy.app.screen.otp.OtpVerificationActivity
import com.fizyshoppy.app.screen.register.RegisterActivity
import com.fizyshoppy.app.util.*
import kotlinx.android.synthetic.main.activity_login.*
import showSnackBar

class LoginActivity : AppCompatActivity(), LoginContract.View, TextView.OnEditorActionListener {

    lateinit var presenter: LoginPresenter
    lateinit var customerRes: CustomerRes
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    override fun init() {

        if (!RealTimePermission.checkPermissionSMS(this)) {
            RealTimePermission.requestSMSPermission(this, RealTimePermission.SMS_PERMISSION_REQUEST_CODE)
        }

        presenter = LoginPresenter(this)
        et_pass.setOnEditorActionListener(this)
        btn_sign_in.setOnClickListener { doLogin() }
        btn_sign_up.setOnClickListener {
            startSignUpScreen()
        }
        tv_forgo_pass.setOnClickListener {
            doForgotPass()
        }

    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE
                || actionId == EditorInfo.IME_ACTION_GO) {
            doLogin()
            return true
        }
        return false
    }

    override fun doLogin() {
        hideSoftInput()
        if (presenter.validateLogin(et_email_or_mobile.text.toString(), et_pass.text.toString())) {
            showLoader()
            presenter.doLogin(et_email_or_mobile.text.toString(), et_pass.text.toString())
        } else {

        }

    }

    override fun invalidEmailPhone() {
        et_email_or_mobile.error = getString(R.string.err_please_enter_valid_emial_pass)
    }

    override fun invalidPass() {
        et_pass.error = getString(R.string.err_please_enter_valid_password)
    }

    override fun startSignUpScreen() {
        startActivity(Intent(this, RegisterActivity::class.java).putExtra(Constants.SOURCE, Constants.SOURCE_LOGIN))
    }

    override fun showLoginRes(res: CustomerRes) {
        if (Validation.isValidStatus(res)) {

            var user: CustomerRes = res
            if (Validation.isValidList(user.result)) {
                CommonUtil.setCustomerData(user.result[0])
                CommonUtil.setIsLoggedIn(true)
                navigateToDahboard()
            } else
                showMsg(res.message ?: getString(R.string.err_some_went_wrong))


        } else if (res?.message != null)
            showMsg(res?.message!!)
    }

    override fun showMsg(msg: String?) {
        layout_login.showSnackBar(msg ?: getString(R.string.err_some_went_wrong))
    }

    override fun showLoader() {
        showProgressLoader()
        //CommonUtil.showLoading()
    }

    override fun hideLoader() {
        hideProgressLoader()

    }

    override fun showViewState(state: Int) {

    }

    override fun showForgotpassRes(res: CustomerRes) {
        if (Validation.isValidStatus(res)) {
            customerRes = res
            startOtpScreen()

        } else
            showMsg(res.message ?: getString(R.string.error_something_wrong))


    }

    override fun startOtpScreen() {
        if (Validation.isValidStatus(customerRes)) {
            var intent = Intent(this, OtpVerificationActivity::class.java)
            intent.apply {
                putExtra(Constants.SOURCE, Constants.SOURCE_LOGIN)
                putExtra(Constants.OTP, customerRes.result[0].otp)
            }
            startActivity(intent)
            finish()
        }

    }

    private fun doForgotPass() {
        if (et_email_or_mobile.text.toString().length > 0) {
            presenter.forgotPasswrd(et_email_or_mobile.text.toString(), true)
            showLoader()
        } else
            showMsg(getString(R.string.err_email_id_mobile))
    }

    private fun navigateToDahboard() {
        var intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(Constants.SOURCE, Constants.SOURCE_SIGN)
        startActivity(intent)
        finish()
    }

}
