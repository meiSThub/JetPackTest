package com.plum.roomtest.ui.order

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.plum.roomtest.data.OrderInfoEntity
import com.plum.roomtest.databinding.ActivityRoomBaseBinding
import com.plum.roomtest.db.WordDB
import com.plum.roomtest.ui.order.adapter.OrderInfoAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderInfoActivity : AppCompatActivity() {

    private val binding: ActivityRoomBaseBinding by lazy {
        ActivityRoomBaseBinding.inflate(layoutInflater)
    }

    private val dao by lazy {
        WordDB.getInstance(this).getOrderInfoDao()
    }
    private val adapter by lazy {
        OrderInfoAdapter(this, mutableListOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter

        binding.btnSave.setOnClickListener {
            lifecycleScope.launch {
                val saveWord = binding.et.text.toString()
                withContext(Dispatchers.IO) {
                    dao.insert(
                        OrderInfoEntity(
                            orderId = "orderId:$saveWord",
                            orderInfo = "orderInfo：$saveWord"
                        )
                    )
                }
                updateData()
            }
        }

        binding.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    dao.deleteAll()
                }
                updateData()
            }
        }
        queryAll()
    }

    private fun queryAll() {
        lifecycleScope.launch {
            val list = withContext(Dispatchers.IO) { dao.queryAll() }
            adapter.setNewData(list)
        }
    }


    private fun updateData() {
        lifecycleScope.launch {
            val list = withContext(Dispatchers.IO) {
                dao.queryAll()
            }
            adapter.setNewData(list)
        }
    }
}