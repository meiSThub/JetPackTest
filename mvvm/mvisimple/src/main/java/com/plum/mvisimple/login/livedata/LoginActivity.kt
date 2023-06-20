package com.plum.mvisimple.login.livedata

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.plum.mvi.core.dialog.ILoadingDialogHolder
import com.plum.mvi.core.dialog.UiLoadingDialogHolder
import com.plum.mvi.core.kt.observeEvent
import com.plum.mvi.core.kt.observeState
import com.plum.mvisimple.databinding.ActivityLoginBinding
import com.plum.mvisimple.login.LoginViewAction
import com.plum.mvisimple.login.LoginViewEvent
import com.plum.mvisimple.login.LoginViewState

class LoginActivity : AppCompatActivity(), ILoadingDialogHolder by UiLoadingDialogHolder() {
    companion object {
        private const val TAG = "LoginActivity"
    }

    private val viewModel by viewModels<LoginViewModel>()

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initViewStates()
        initViewEvents()
    }

    private fun initView() {
        binding.editUserName.addTextChangedListener {
            viewModel.dispatch(LoginViewAction.UpdateUserName(it.toString()))
        }
        binding.editPassword.addTextChangedListener {
            viewModel.dispatch(LoginViewAction.UpdatePassword(it.toString()))
        }
        binding.btnLogin.setOnClickListener {
            viewModel.dispatch(LoginViewAction.Login)
        }
    }

    private fun initViewStates() {
        viewModel.viewStates.let { states ->
            states.observeState(this, LoginViewState::userName) {
                binding.editUserName.setText(it)
                binding.editUserName.setSelection(it.length)
            }
            states.observeState(this, LoginViewState::password) {
                binding.editPassword.setText(it)
                binding.editPassword.setSelection(it.length)
            }
            states.observeState(this, LoginViewState::isLoginEnable) {
                binding.btnLogin.isEnabled = it
                binding.btnLogin.alpha = if (it) 1f else 0.5f
            }
            states.observeState(this, LoginViewState::passwordTipVisible) {
                binding.tvLabel.visibility = if (it) View.VISIBLE else View.INVISIBLE
            }
        }
    }

    private fun initViewEvents() {
        viewModel.viewEvents.observeEvent(this) {
            Log.i(TAG, "initViewEvents: event=$it")
            when (it) {
                is LoginViewEvent.ShowToast -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT)
                    .show()
                is LoginViewEvent.ShowLoadingDialog -> showLoadingDialog(this, "")
                is LoginViewEvent.DismissLoadingDialog -> dismissLoadingDialog()
            }
        }
    }
}