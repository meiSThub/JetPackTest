package com.plum.mvisimple.login.flow

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.plum.mvi.core.kt.setEvent
import com.plum.mvi.core.kt.setState
import com.plum.mvi.core.vm.MviViewModel
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

class LoginViewModel : MviViewModel<LoginViewState, LoginViewEvent, LoginViewAction>() {
    companion object {
        private const val TAG = "LoginViewModel"
    }

    override fun initState(): LoginViewState = LoginViewState()

    override fun handleAction(action: LoginViewAction) {
        Log.i(TAG, "handleAction: action=$action")
        when (action) {
            is LoginViewAction.UpdateUserName -> updateUserName(action.userName)
            is LoginViewAction.UpdatePassword -> updatePassword(action.password)
            is LoginViewAction.Login -> login()
        }
    }

    private fun updateUserName(userName: String) {
        _viewState.setState { copy(userName = userName) }
    }

    private fun updatePassword(password: String) {
        _viewState.setState { copy(password = password) }
    }

    private fun login() {
        viewModelScope.launch {
            flow {
                delay(1000)
                emit("登录成功")
            }.onStart {
                _viewEvent.setEvent(LoginViewEvent.ShowLoadingDialog)
            }.onEach {
                _viewEvent.setEvent(
                    LoginViewEvent.DismissLoadingDialog, LoginViewEvent.ShowToast(it)
                )
            }.catch {
                _viewState.setState { copy(password = "") }
                _viewEvent.setEvent(
                    LoginViewEvent.DismissLoadingDialog, LoginViewEvent.ShowToast("登录失败")
                )
            }.collect()
        }
    }

    private suspend fun loginLogic() {
        viewModelScope.launch {
            delay(300)
            "登录"
        }
    }
}