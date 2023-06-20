package com.plum.mvisimple.login.livedata

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plum.mvi.core.kt.LiveEvents
import com.plum.mvi.core.kt.asLiveData
import com.plum.mvi.core.kt.setEvent
import com.plum.mvi.core.kt.setState
import com.plum.mvisimple.login.LoginViewAction
import com.plum.mvisimple.login.LoginViewEvent
import com.plum.mvisimple.login.LoginViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    companion object {
        private const val TAG = "LoginViewModel"
    }

    private val _viewStates = MutableLiveData(LoginViewState())
    val viewStates = _viewStates.asLiveData()
    private val _viewEvents: LiveEvents<LoginViewEvent> = LiveEvents()
    val viewEvents = _viewEvents.asLiveData()

    fun dispatch(viewAction: LoginViewAction) {
        Log.i(TAG, "dispatch: action=$viewAction")
        when (viewAction) {
            is LoginViewAction.UpdateUserName -> updateUserName(viewAction.userName)
            is LoginViewAction.UpdatePassword -> updatePassword(viewAction.password)
            is LoginViewAction.Login -> login()
        }
    }

    private fun updateUserName(userName: String) {
        _viewStates.setState { copy(userName = userName) }
    }

    private fun updatePassword(password: String) {
        _viewStates.setState { copy(password = password) }
    }

    private fun login() {
        viewModelScope.launch {
            flow {
                delay(1000)
                emit("登录成功")
            }.onStart {
                _viewEvents.setEvent(LoginViewEvent.ShowLoadingDialog)
            }.onEach {
                _viewEvents.setEvent(
                    LoginViewEvent.DismissLoadingDialog, LoginViewEvent.ShowToast(it)
                )
            }.catch {
                it.printStackTrace()
                _viewStates.setState { copy(password = "") }
                _viewEvents.setEvent(
                    LoginViewEvent.DismissLoadingDialog, LoginViewEvent.ShowToast("登录失败")
                )
            }.collect()
        }
    }

    private suspend fun loginLogic() {
        viewModelScope.launch {
            delay(1000)
            "登录"
        }
    }
}