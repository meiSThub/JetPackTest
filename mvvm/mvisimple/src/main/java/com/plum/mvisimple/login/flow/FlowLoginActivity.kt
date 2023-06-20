package com.plum.mvisimple.login.flow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.plum.mvi.core.activity.MviActivity
import com.plum.mvi.core.kt.observeEvent
import com.plum.mvi.core.kt.observeState
import com.plum.mvisimple.databinding.ActivityLoginBinding
import com.plum.mvisimple.login.LoginViewAction
import com.plum.mvisimple.login.LoginViewEvent
import com.plum.mvisimple.login.LoginViewState

class FlowLoginActivity :
    MviActivity<ActivityLoginBinding, LoginViewState, LoginViewEvent, LoginViewAction, LoginViewModel>() {
    companion object {
        private const val TAG = "FlowLoginActivity"
    }

    override val bindingInflater: (LayoutInflater) -> ActivityLoginBinding
        get() = ActivityLoginBinding::inflate

    override val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initViewStates()
        initViewEvents()
    }

    private fun initView() {
        binding.editUserName.addTextChangedListener {
            viewModel.handleAction(LoginViewAction.UpdateUserName(it.toString()))
        }
        binding.editPassword.addTextChangedListener {
            viewModel.handleAction(LoginViewAction.UpdatePassword(it.toString()))
        }
        binding.btnLogin.setOnClickListener {
            viewModel.handleAction(LoginViewAction.Login)
        }
    }

    private fun initViewStates() {
        viewModel.viewState.let { liveData ->
            Log.i(TAG, "initViewStates: states=$liveData")
            liveData.observeState(this, LoginViewState::userName) {
                binding.editUserName.setText(it)
                binding.editUserName.setSelection(it.length)
            }
            liveData.observeState(this, LoginViewState::password) {
                binding.editPassword.setText(it)
                binding.editPassword.setSelection(it.length)
            }
            liveData.observeState(this, LoginViewState::isLoginEnable) {
                binding.btnLogin.isEnabled = it
                binding.btnLogin.alpha = if (it) 1f else 0.5f
            }
            liveData.observeState(this, LoginViewState::passwordTipVisible) {
                binding.tvLabel.visibility = if (it) View.VISIBLE else View.INVISIBLE
            }
        }
    }

    private fun initViewEvents() {
        viewModel.viewEvent.observeEvent(this) {
            when (it) {
                is LoginViewEvent.ShowToast -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT)
                    .show()
                is LoginViewEvent.ShowLoadingDialog -> showLoadingDialog()
                is LoginViewEvent.DismissLoadingDialog -> dismissLoadingDialog()
            }
        }
    }
}