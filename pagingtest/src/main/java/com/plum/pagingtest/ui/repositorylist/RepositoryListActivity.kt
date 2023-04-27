package com.plum.pagingtest.ui.repositorylist

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
import com.plum.pagingtest.databinding.ActivityRepositoryListBinding
import com.plum.pagingtest.ui.repositorylist.adapter.RepositoryListAdapter
import com.plum.pagingtest.ui.repositorylist.viewmodel.RepositoryViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * @Author: meixianbing
 * @Date: 2023/4/27 上午11:41
 * @Description: 仓库列表页面
 */
class RepositoryListActivity : AppCompatActivity() {

    private val binding: ActivityRepositoryListBinding by lazy {
        ActivityRepositoryListBinding.inflate(layoutInflater)
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
                    binding.refreshLayout.isRefreshing = false
                }
                is LoadState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.INVISIBLE
                    binding.refreshLayout.isRefreshing = true
                }
                is LoadState.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.refreshLayout.isRefreshing = false
                }
            }
        }

        binding.refreshLayout.setOnRefreshListener {
            binding.recyclerView.swapAdapter(adapter, true)
            adapter.refresh()
        }
    }
}