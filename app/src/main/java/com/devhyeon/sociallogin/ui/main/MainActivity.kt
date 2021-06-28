package com.devhyeon.sociallogin.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devhyeon.sociallogin.R

class MainActivity : AppCompatActivity() {
    //TAG
    companion object {
        private val TAG = MainActivity::class.java.name
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}