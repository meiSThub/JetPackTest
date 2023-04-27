package com.plum.pagingtest.ui.addfooter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.plum.pagingtest.databinding.LayoutHeaderViewBinding

/**
 * @Author: meixianbing
 * @Date: 2023/4/27 下午5:00
 * @Description:
 */
class HeaderAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<HeaderAdapter.HeaderViewHolder>() {


    class HeaderViewHolder(
        private val binding: LayoutHeaderViewBinding,
        retry: () -> Unit
    ) : ViewHolder(binding.root) {

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutHeaderViewBinding.inflate(layoutInflater, parent, false)
                return HeaderViewHolder(binding, retry)
            }
        }

        init {
            binding.btnRetry.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.tvErrorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.tvErrorMsg.isVisible = loadState is LoadState.Error
            binding.btnRetry.isVisible = loadState is LoadState.Error
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): HeaderViewHolder {
        return HeaderViewHolder.create(parent, retry)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}