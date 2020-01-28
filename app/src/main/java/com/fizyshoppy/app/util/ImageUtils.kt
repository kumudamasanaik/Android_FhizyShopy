package com.fizyshoppy.app.util

import android.content.Context
import android.widget.ImageView
import com.fizyshoppy.app.R
import com.fizyshoppy.app.api.ApiConstants
import com.fizyshoppy.app.customview.CircleImageView
import com.squareup.picasso.Picasso

/**
 * Created by FuGenX-14 on 18-07-2018.
 */
class ImageUtils {
    companion object {

        fun setImageWithPicasso(activityRef: Context, imageView: ImageView, path: String?) {
            if (path != null && path.length > 0) {
                Picasso.get()
                        .load(ApiConstants.IMAGE_BASE_URL + path)
                        .error(R.drawable.loader)
                        .placeholder(R.drawable.loader)
                        .noFade()
                        .into(imageView)

                MyLogUtils.d("Image URL", (ApiConstants.IMAGE_BASE_URL + path))

            }
        }

        fun setCircleImageWithPicasso(activityRef: Context, imageView: ImageView, path: String?) {
            if (path != null && path.length > 0) {
                Picasso.get()
                        .load(ApiConstants.IMAGE_BASE_URL + path)/// add image Base url
                        .error(R.drawable.loader)
                        .placeholder(R.drawable.loader)
                        .transform(CircleImageView())
                        .noFade()
                        .into(imageView)

            }
        }
    }
}