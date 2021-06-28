package com.devhyeon.sociallogin.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devhyeon.sociallogin.R

class LoginActivity : AppCompatActivity() {
    //TAG
    companion object {
        private val TAG = LoginActivity::class.java.name
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}