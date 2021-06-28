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
import com.devhyeon.sociallogin.viewModels.IntroViewModel

/**
 * IntroActivity
 * IntroViewModel 로 token 상태 , 네트워크 상태 등 다음 동작을 하기 위한 화면
 * */
class IntroActivity : AppCompatActivity() {

    private lateinit var introViewModel: IntroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val kakaoLoginRepository = kakaoLoginRepository()

        //ViewModel
        introViewModel = ViewModelProvider(this,
            IntroViewModel.IntroViewModelFactory(kakaoLoginRepository)
        ).get(IntroViewModel::class.java)

        //Add Observer
        addObserver()
    }

    /** Start */
    override fun onStart() {
        super.onStart()
        //introViewModel 진행 (token 상태 확인 및 딜레이)
        introViewModel.startInit()
    }

    /** 옵저버 등록 */
    private fun addObserver() {
        //token 상태에 따라 다음 Activity 결정
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