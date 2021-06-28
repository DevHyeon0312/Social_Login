package com.devhyeon.sociallogin.loginSDK.kakao

import android.content.Context
import android.util.Log
import com.devhyeon.sociallogin.constant.ETC_ERROR
import com.devhyeon.sociallogin.constant.HAS_TOKEN
import com.devhyeon.sociallogin.constant.NEED_TOKEN
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient

/**
 * kakaoLoginRepository
 * 카카오 api 에서 제공하는 카카오로그인을 하기위한 repository.
 * 1. 토큰여부 확인
 * 2. 로그인
 * 3. 로그아웃
 * */
class kakaoLoginRepository {
    //TAG
    companion object {
        private val TAG = kakaoLoginRepository::class.java.name
    }

    fun hasToken(complete: (Int, Throwable?) -> Unit) {
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error != null) {
                    if (error is KakaoSdkError && error.isInvalidTokenError() == true) {
                        //로그인 필요
                        complete(NEED_TOKEN, error)
                    }
                    else {
                        //기타 에러
                        complete(ETC_ERROR, error)
                    }
                }
                else {
                    //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                    complete(HAS_TOKEN, null)
                }
            }
        }
        else {
            //로그인 필요
            complete(NEED_TOKEN, null)
        }
    }

    fun signIn(context: Context, complete: (OAuthToken?, Throwable?) -> Unit) {
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(TAG, "로그인 실패", error)
                }
                else if (token != null) {
                    Log.i(TAG, "로그인 성공 ${token.accessToken}")
                }
                complete(token, error)
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                if (error != null) {
                    Log.e(TAG, "로그인 실패", error)
                }
                else if (token != null) {
                    Log.i(TAG, "로그인 성공 ${token.accessToken}")
                }
                complete(token, error)
            }
        }
    }

    fun signOut(complete: (Throwable?) -> Unit) {
        // 로그아웃
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            }
            else {
                Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
            }
            complete(error)
        }
    }
}