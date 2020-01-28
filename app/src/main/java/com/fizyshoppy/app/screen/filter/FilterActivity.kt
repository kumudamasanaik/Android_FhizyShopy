package com.fizyshoppy.app.screen.filter

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import com.fizyshoppy.app.R
import com.fizyshoppy.app.screen.BaseActivity

class FilterActivity : BaseActivity() {
    private val TAG = "FilterActivity"
    private lateinit var mContext: Context
    private lateinit var source: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_filter, fragmentLayout)
        setTitle(getString(R.string.filter))
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
