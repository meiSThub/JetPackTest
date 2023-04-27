package com.plum.pagingtest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.plum.pagingtest.ui.baseused.RepositoryListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun pagingTest(view: View) {
        startActivity(Intent(this, RepositoryListActivity::class.java))
    }
}