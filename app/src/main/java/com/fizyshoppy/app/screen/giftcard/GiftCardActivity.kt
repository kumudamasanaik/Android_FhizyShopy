package com.fizyshoppy.app.screen.giftcard

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import com.fizyshoppy.app.R
import com.fizyshoppy.app.screen.BaseActivity

class GiftCardActivity : BaseActivity() {
    private val TAG = "GiftCardActivity"
    private lateinit var mContext: Context
    private lateinit var source: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_gift_card, fragmentLayout)
        setTitle(getString(R.string.gift_card))
        mContext = this
        showBack()

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

}