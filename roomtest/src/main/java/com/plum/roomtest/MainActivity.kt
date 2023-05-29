package com.plum.roomtest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.plum.roomtest.databinding.ActivityMainBinding
import com.plum.roomtest.ui.baseuse.BaseUseActivity
import com.plum.roomtest.ui.order.OrderInfoActivity

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnBaseUse.setOnClickListener(this::onClick)

        binding.btnAddNewTable.setOnClickListener(this::onClick)
    }

    private fun onClick(v: View) {
        when (v.id) {
            R.id.btnBaseUse -> startActivity(Intent(this, BaseUseActivity::class.java))
            R.id.btnAddNewTable -> startActivity(Intent(this, OrderInfoActivity::class.java))
        }
    }
}