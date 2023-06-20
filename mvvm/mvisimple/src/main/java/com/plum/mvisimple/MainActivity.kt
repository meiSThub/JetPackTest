package com.plum.mvisimple

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.plum.mvisimple.login.flow.FlowLoginActivity
import com.plum.mvisimple.login.livedata.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun login(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun loginFlow(view: View) {
        startActivity(Intent(this, FlowLoginActivity::class.java))
    }
}