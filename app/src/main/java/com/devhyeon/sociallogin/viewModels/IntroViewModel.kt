package com.devhyeon.sociallogin.viewModels

import androidx.lifecycle.*
import com.devhyeon.sociallogin.loginSDK.kakao.kakaoLoginRepository
import com.devhyeon.sociallogin.constant.ETC_ERROR
import com.devhyeon.sociallogin.constant.NEED_TOKEN
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * IntroViewModel
 * 1. Token 여부 판단
 * 2. 딜레이(1000ms) 주기
 * 3. Add Network Check (예정)
 * */
class IntroViewModel(private val kakaoLoginRepository: kakaoLoginRepository) : ViewModel() {
    //TAG
    companion object {
        private val TAG = IntroViewModel::class.java.name
    }

    private val _loginState = MutableLiveData<Int>()
    val loginState: LiveData<Int> get() = _loginState

    private val delayTime = 1000L

    /** Intro 초기화 시작 */
    fun startInit() {
        hasToken()
    }

    /** 토큰이 있는지 검사 */
    private fun hasToken() {
        var nextType = NEED_TOKEN
        //카카오 로그인 토큰이 있는지 검사
        kakaoLoginRepository.hasToken { it, throwable ->
            apply {
                nextType = it
            }
        }
        //다른 토큰이 있는지 검사
        //...

        runDelay(nextType)
    }

    /** 딜레이 진행 후 LiveData 값 변경 */
    private fun runDelay(token: Int) {
        viewModelScope.launch {
            runCatching {
                delay(delayTime)
            }.onSuccess {
                _loginState.value = token
            }.onFailure {
                _loginState.value = ETC_ERROR
            }
        }
    }

    /** ViewModels 구현  */
    class IntroViewModelFactory(private val kakaoLoginRepository: kakaoLoginRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(IntroViewModel::class.java)) {
                return IntroViewModel(kakaoLoginRepository) as T
            }
            throw IllegalAccessException("Unkown Viewmodel Class")
        }
    }
}