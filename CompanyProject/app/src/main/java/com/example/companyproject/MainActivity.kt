package com.example.companyproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.companyproject.fragment.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_main_fragment, MainFragment()).addToBackStack(null).commit()
    }
}
