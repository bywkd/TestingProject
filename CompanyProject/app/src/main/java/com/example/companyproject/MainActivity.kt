package com.example.companyproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.companyproject.databinding.ActivityMainBinding
import com.example.companyproject.viewModel.MainActivityVM

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ft = supportFragmentManager.beginTransaction()
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.apply {
            mainActivityVM = MainActivityVM(ft)
        }.mainActivityVM!!.fragmentMainFragment()
    }
}
