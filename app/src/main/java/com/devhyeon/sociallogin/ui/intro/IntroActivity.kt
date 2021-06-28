package com.devhyeon.sociallogin.ui.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devhyeon.sociallogin.R
import com.devhyeon.sociallogin.loginSDK.kakao.kakaoLoginRepository
import com.devhyeon.sociallogin.constant.ETC_ERROR
import com.devhyeon.sociallogin.constant.HAS_TOKEN
import com.devhyeon.sociallogin.constant.NEED_TOKEN
import com.devhyeon.sociallogin.ui.login.LoginActivity
import com.devhyeon.sociallogin.ui.main.MainActivity


class IntroActivity : AppCompatActivity() {

    private lateinit var introViewModel: IntroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val kakaoLoginRepository = kakaoLoginRepository()

        introViewModel = ViewModelProvider(this,
            IntroViewModel.IntroViewModelFactory(kakaoLoginRepository)
        ).get(IntroViewModel::class.java)

        addObserver()

    }

    override fun onStart() {
        super.onStart()
        introViewModel.startInit()
    }

    private fun addObserver() {
        introViewModel.loginState.observe(this@IntroActivity, Observer {
            when (it) {
                NEED_TOKEN -> {
                    //Go Login
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                HAS_TOKEN -> {
                    //Go Main
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                ETC_ERROR -> {
                    //Go ErrorPage
                }
            }
        })
    }

}