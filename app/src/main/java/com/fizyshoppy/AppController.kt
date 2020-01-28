package com.fizyshoppy

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.StrictMode
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.fizyshoppy.app.BuildConfig
import com.fizyshoppy.app.api.ApiConstants
import com.fizyshoppy.app.api.ApiInterface
import com.fizyshoppy.app.reciever.NetworkReceiver
import com.fizyshoppy.app.util.MyLogUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppController : Application(), Application.ActivityLifecycleCallbacks {


    companion object {
        val TAG = "AppController"
        lateinit var context: Context
        var mActivityRefernce: String = ""
        lateinit var service: ApiInterface
        internal lateinit var mNetworkReceiver: NetworkReceiver
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        registerActivityLifecycleCallbacks(this)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        createRetroFitObject()
    }


    override fun onActivityPaused(activity: Activity?) {

    }

    override fun onActivityResumed(activity: Activity?) {
        mActivityRefernce = activity!!::class.java.simpleName
        MyLogUtils.i(TAG, "onActivityResumed:$activity!!::class.java.simpleName")
    }

    override fun onActivityStarted(activity: Activity?) {
        MyLogUtils.i(TAG, "onActivityStarted:$activity!!::class.java.simpleName")
    }

    override fun onActivityDestroyed(activity: Activity?) {
        MyLogUtils.i(TAG, "onActivityDestroyed:$activity!!::class.java.simpleName")
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        MyLogUtils.i(TAG, "onActivitySaveInstanceState:$activity!!::class.java.simpleName")
    }

    override fun onActivityStopped(activity: Activity?) {
        MyLogUtils.i(TAG, "onActivityStopped:$activity!!::class.java.simpleName")
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        MyLogUtils.i(TAG, "onActivityCreated:$activity!!::class.java.simpleName")
    }

    private fun createRetroFitObject() {
        val retrofit = Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(getRequestHeader())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(ApiInterface::class.java)
    }


    private fun getRequestHeader(): OkHttpClient {
        val okhttpClient = OkHttpClient.Builder()
        okhttpClient.readTimeout(60, TimeUnit.SECONDS)
        okhttpClient.writeTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okhttpClient.addNetworkInterceptor(StethoInterceptor())
            okhttpClient.addInterceptor(logging)
        }
        return okhttpClient.build()
    }

    private fun registerNetworkBroadcastForNougat() {
        registerReceiver(mNetworkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }


}