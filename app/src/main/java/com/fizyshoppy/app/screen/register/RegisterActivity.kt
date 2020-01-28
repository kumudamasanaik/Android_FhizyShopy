package com.fizyshoppy.app.screen.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.fizyshoppy.app.R
import com.fizyshoppy.app.constants.Constants
import com.fizyshoppy.app.model.CustomerRes
import com.fizyshoppy.app.model.payloadmodel.RegisterInput
import com.fizyshoppy.app.screen.otp.OtpVerificationActivity
import com.fizyshoppy.app.util.Validation
import com.fizyshoppy.app.util.hideProgressLoader
import com.fizyshoppy.app.util.hideSoftInput
import com.fizyshoppy.app.util.showProgressLoader
import kotlinx.android.synthetic.main.activity_register.*
import showSnackBar


class RegisterActivity : AppCompatActivity(), RegisterContract.View, TextView.OnEditorActionListener {

    lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        init()
    }


    override fun init() {
        presenter = RegisterPresenter(this)
        btn_sign_up.setOnClickListener { doRegister() }
        et_conf_pass.setOnEditorActionListener(this)
        setDummyVal()
    }

    override fun onResume() {
        super.onResume()
//        if (RealTimePermission.checkPermissionSMS(this)) {
//            RealTimePermission.requestSMSPermission(this, RealTimePermission.SMS_PERMISSION_REQUEST_CODE)
//        }
    }

    override fun getContext(): Context = this

    private fun setDummyVal() {
        et_name.setText("test")
        et_email.setText("test@test.com")
        et_mobile.setText("9691705957")
        et_pass.setText("test")
        et_conf_pass.setText("test")
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE
                || actionId == EditorInfo.IME_ACTION_GO) {
            doRegister()
            return true
        }
        return false
    }

    override fun doRegister() {
        hideSoftInput()
        var registerInput = RegisterInput(name = et_name.text.toString(), email = et_email.text.toString(),
                mobile = et_mobile.text.toString(), password = et_pass.text.toString(), confPassword = et_conf_pass.text.toString())
        if (presenter.validate(registerInput)) {
            showLoader()
            presenter.callRegister(registerInput)
        }
    }

    override fun showInValidFiledMsg(msg: String) {
        when (msg) {
            getString(R.string.err_please_enter_valid_name) -> et_name.error = msg
            getString(R.string.err_please_enter_valid_email) -> et_email.error = msg
            getString(R.string.err_please_enter_valid_mobile) -> et_mobile.error = msg
            getString(R.string.err_please_enter_valid_password) -> et_pass.error = msg
            getString(R.string.err_please_enter_valid_conf_pass) -> et_conf_pass.error = msg
            getString(R.string.err_please_enter_mismatch_conf_pass) -> et_conf_pass.error = msg
        }
    }

    override fun setRegsiterRes(res: CustomerRes) {
        if (Validation.isValidStatus(res)) {
            showMsg(Constants.SUCCESS)
            var intent = Intent(this, OtpVerificationActivity::class.java)
            intent.apply {
                putExtra(Constants.SOURCE, Constants.SOURCE_REGSITER)
                putExtra(Constants.OTP, res.result[0].otp)
            }
            startActivity(intent)
        } else if (res?.message != null) {
            showMsg(res?.message!!)
        } else
            showMsg(null)
    }


    override fun showMsg(msg: String?) {
        layout_register.showSnackBar(msg ?: getString(R.string.err_some_went_wrong))
    }

    override fun showLoader() {
        showProgressLoader()
    }

    override fun hideLoader() {
        hideProgressLoader()
    }

    override fun showViewState(state: Int) {

    }
}
