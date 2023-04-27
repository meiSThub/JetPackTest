package com.plum.pagingtest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.plum.pagingtest.ui.addfooter.AddFooterAndHeaderActivity
import com.plum.pagingtest.ui.baseused.RepositoryListActivity
import com.plum.pagingtest.ui.smartrefresh.SmartRefreshActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun pagingTest(view: View) {
        startActivity(Intent(this, RepositoryListActivity::class.java))
    }

    fun addFooterAndHeader(view: View) {
        startActivity(Intent(this, AddFooterAndHeaderActivity::class.java))
    }

    fun addFooterAndHeaderBySmartRefresh(view: View) {
        startActivity(Intent(this, SmartRefreshActivity::class.java))
    }
}