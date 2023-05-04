package com.plum.pagingtest.ui.smartrefresh

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.elvishew.xlog.XLog
import com.plum.pagingtest.bean.RepositoryItem
import com.plum.pagingtest.databinding.ActivitySmartRefreshPagingBinding
import com.plum.pagingtest.ui.smartrefresh.adapter.RepositoryListAdapter
import com.plum.pagingtest.ui.smartrefresh.viewmodel.RepositoryViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * @Author: meixianbing
 * @Date: 2023/4/27 上午11:41
 * @Description: 仓库列表页面
 */
class SmartRefreshActivity : AppCompatActivity() {

    private val binding: ActivitySmartRefreshPagingBinding by lazy {
        ActivitySmartRefreshPagingBinding.inflate(layoutInflater)
    }
    private val adapter = RepositoryListAdapter()

    private val viewModel by viewModels<RepositoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        // 添加 header 和 footer
        binding.recyclerView.adapter = adapter
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                XLog.i("开始加载数据")
                viewModel.getPagingData().collectLatest { pagingData: PagingData<RepositoryItem> ->
                    adapter.submitData(pagingData)
                }
            }
        }

        adapter.addLoadStateListener { state: CombinedLoadStates ->
            XLog.i("initRecyclerView: state=${state.refresh}")
            when (state.refresh) {
                is LoadState.NotLoading -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.refreshLayout.finishRefresh()
                    binding.refreshLayout.finishLoadMore()
                }
                is LoadState.Loading -> {
                    if (adapter.itemCount > 0) {
                        // 显示刷新动画，不触发事件
                        binding.refreshLayout.autoRefreshAnimationOnly()
                    } else {
                        // 说明是第一次加载数据，显示页面loading
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.INVISIBLE
                        // binding.refreshLayout.isRefreshing = true
                    }
                }
                is LoadState.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    // binding.refreshLayout.isRefreshing = false
                }
            }
        }

        binding.refreshLayout.setOnRefreshListener {
            binding.recyclerView.swapAdapter(adapter, true)
            adapter.refresh()
        }
    }
}