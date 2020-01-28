/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.R
import android.support.design.widget.Snackbar
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout

var progressBar: ProgressBar? = null

fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, Html.fromHtml("<font color=\"#ffffff\">$message</font>"), duration).show()
}

fun ViewGroup.showLoader() {
    progressBar = ProgressBar(this.context, null, R.attr.progressBarStyleLarge)
    when(this){}
    val params = RelativeLayout.LayoutParams(100, 100)
    params.addRule(RelativeLayout.CENTER_IN_PARENT)
    this.addView(progressBar, params)
    progressBar?.visibility = View.VISIBLE
}

fun ViewGroup.hideProgressbar() {
    progressBar?.visibility = View.GONE
}

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}


