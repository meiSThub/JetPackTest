package com.plum.pagingtest.ui.smartrefresh.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.plum.pagingtest.bean.RepositoryItem
import com.plum.pagingtest.databinding.LayoutRepositoryListItemBinding

/**
 * @Author: meixianbing
 * @Date: 2023/4/27 下午1:53
 * @Description:
 */
class RepositoryListAdapter :
    PagingDataAdapter<RepositoryItem, RepositoryListAdapter.ViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<RepositoryItem>() {
            override fun areItemsTheSame(
                oldItem: RepositoryItem,
                newItem: RepositoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RepositoryItem,
                newItem: RepositoryItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(val binding: LayoutRepositoryListItemBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutRepositoryListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        val binding: LayoutRepositoryListItemBinding = holder.binding
        binding.tvName.text = item.name
        binding.tvDesc.text = item.description
        binding.tvStar.text = item.stargazers_count.toString()
        binding.llItem.setOnClickListener {
            Toast.makeText(it.context, "点击有效", Toast.LENGTH_SHORT).show()
        }
    }
}