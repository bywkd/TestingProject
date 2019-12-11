package com.example.companyproject.bindingObject

import android.view.View
import androidx.databinding.BindingAdapter

object SizeBinding {

    @JvmStatic
    @BindingAdapter("android:layout_height")
    fun setLayoutHeight(view: View, height: Int) {
        val params = view.layoutParams
        params.height = height
        view.layoutParams = params
    }

    @JvmStatic
    @BindingAdapter("android:layout_width")
    fun setLayoutWidth(view: View, width: Int) {
        val params = view.layoutParams
        params.width = width
        view.layoutParams = params
    }

}