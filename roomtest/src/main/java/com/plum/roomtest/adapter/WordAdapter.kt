package com.plum.roomtest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.plum.roomtest.R
import com.plum.roomtest.data.WordEntity

/**
 * @Author: meixianbing
 * @Date: 2023/5/29 下午2:36
 * @Description:
 */
class WordAdapter(
    private val context: Context,
    private val list: MutableList<WordEntity>
) : RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    private lateinit var wordDeleteListener: WordDeleteListener

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(list: MutableList<WordEntity>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.tvWord.text = item.word
        holder.tvContent.text = item.content
        holder.llItem.setOnClickListener {
            wordDeleteListener.delete(item.id)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvWord: TextView = itemView.findViewById(R.id.tv_word)
        var llItem: LinearLayout = itemView.findViewById(R.id.ll_item)
        var tvContent: TextView = itemView.findViewById(R.id.tv_content)
    }

    fun setWordDeleteListener(wordDeleteListener: WordDeleteListener) {
        this.wordDeleteListener = wordDeleteListener
    }

    interface WordDeleteListener {
        fun delete(id: Long)
    }
}
