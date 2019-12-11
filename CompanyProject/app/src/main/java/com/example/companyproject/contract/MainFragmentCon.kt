package com.example.companyproject.contract

import com.example.companyproject.interfaceUtils.ToastUtils
import com.example.companyproject.model.SearchImage

interface MainFragmentCon : ToastUtils {
    fun addListAll(list: ArrayList<SearchImage.Document>)
    fun setListAll(list: ArrayList<SearchImage.Document>)
    fun progressState(state: Boolean)
    override fun showToast(msg: String)
}