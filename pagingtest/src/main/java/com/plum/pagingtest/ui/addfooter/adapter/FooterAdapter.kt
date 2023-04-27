package com.plum.pagingtest.ui.addfooter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.plum.pagingtest.databinding.LayoutFooterViewBinding

/**
 * @Author: meixianbing
 * @Date: 2023/4/27 下午5:00
 * @Description:
 */
class FooterAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<FooterAdapter.FooterViewHolder>() {


    class FooterViewHolder(
        private val binding: LayoutFooterViewBinding,
        retry: () -> Unit
    ) : ViewHolder(binding.root) {

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): FooterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutFooterViewBinding.inflate(layoutInflater, parent, false)
                return FooterViewHolder(binding, retry)
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

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        return FooterViewHolder.create(parent, retry)
    }

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}