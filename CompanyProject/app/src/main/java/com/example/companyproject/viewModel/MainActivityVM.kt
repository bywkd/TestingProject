package com.example.companyproject.viewModel

import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import com.example.companyproject.R
import com.example.companyproject.fragment.MainFragment

class MainActivityVM(ft: FragmentTransaction) : ViewModel() {
    private val ft = ft
    fun fragmentMainFragment() {
        ft.replace(R.id.fl_main_fragment, MainFragment()).addToBackStack(null).commit()
    }
}