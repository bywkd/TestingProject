package com.example.companyproject.bindingObject

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.companyproject.util.GlideApp

object ImageViewBinding {

    @JvmStatic
    @BindingAdapter("imgViewGlide")
    fun imgViewGlide(imgView: ImageView, path: String?) {
        path?.let {
            GlideApp.with(imgView.context).asBitmap().load(it).into(imgView)
        }
    }

    @JvmStatic
    @BindingAdapter("imgViewGlide")
    fun imgViewGlide(imgView: ImageView, resId: Int?) {
        Int?.let {
            GlideApp.with(imgView.context).asBitmap().load(it).into(imgView)
        }
    }

    @JvmStatic
    @BindingAdapter("imgViewGlide")
    fun imgViewGlide(imgView: ImageView, imgUrl: Uri?) {
        imgUrl?.let {
            GlideApp.with(imgView.context).asBitmap().load(it).into(imgView)
        }
    }


}