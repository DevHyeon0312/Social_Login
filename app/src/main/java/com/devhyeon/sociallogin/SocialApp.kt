package com.devhyeon.sociallogin

import android.app.Application
import com.devhyeon.sociallogin.keys.*
import com.kakao.sdk.common.KakaoSdk

class SocialApp: Application() {
    override fun onCreate() {
        super.onCreate()

        // Kakao SDK 초기화
        KakaoSdk.init(this, kakaoAppKey)
    }
}