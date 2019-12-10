package com.example.companyproject.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.companyproject.R

class ProgressLoadingClass(context: Context) {

    private var view: View = LayoutInflater.from(context).inflate(R.layout.loading, null, false)

    fun getProgress(): View {
        return view
    }

    fun loadingProgressBar(state: Boolean) {
        view.let {
            it.visibility = if (state) View.VISIBLE else View.GONE
        }
    }
}