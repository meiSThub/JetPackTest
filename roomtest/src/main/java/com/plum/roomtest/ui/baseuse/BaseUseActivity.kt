package com.plum.roomtest.ui.baseuse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.plum.roomtest.adapter.WordAdapter
import com.plum.roomtest.data.WordEntity
import com.plum.roomtest.databinding.ActivityRoomBaseBinding
import com.plum.roomtest.db.WordDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BaseUseActivity : AppCompatActivity() {

    private val binding: ActivityRoomBaseBinding by lazy {
        ActivityRoomBaseBinding.inflate(layoutInflater)
    }

    private val wordDao by lazy {
        WordDB.getInstance(this).getWordDao()
    }
    private val adapter by lazy {
        WordAdapter(this, mutableListOf())
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
                    wordDao.insert(WordEntity(word = saveWord))
                }
                updateData()
            }
        }

        binding.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    wordDao.deleteAll()
                }
                updateData()
            }
        }
        queryAll()
    }

    private fun queryAll() {
        lifecycleScope.launch {
            val list = withContext(Dispatchers.IO) { wordDao.queryAll() }
            adapter.setNewData(list)
        }
    }


    private fun updateData() {
        lifecycleScope.launch {
            val list = withContext(Dispatchers.IO) {
                wordDao.queryAll()
            }
            adapter.setNewData(list)
        }
    }
}