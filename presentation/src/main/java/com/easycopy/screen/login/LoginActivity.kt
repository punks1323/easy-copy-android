package com.easycopy.screen.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.easycopy.BR
import com.easycopy.R
import com.easycopy.databinding.ActivityMainBinding
import com.easycopy.screen.base.BaseActivity
import com.easycopy.screen.qrcode.QrCodeActivity
import javax.inject.Inject


class LoginActivity : BaseActivity<ActivityMainBinding?, LoginViewModel?>(), LoginNavigator {

    @Inject
    lateinit var loginViewModel: LoginViewModel

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_login

    override val viewModel: ViewModel
        get() = loginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel.setNavigator(this)
        init()
    }

    private fun init() {
        loginViewModel.init()
        startActivity(Intent(this, QrCodeActivity::class.java))
    }

}