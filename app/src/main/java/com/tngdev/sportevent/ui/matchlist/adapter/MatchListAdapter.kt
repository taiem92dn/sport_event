package com.tngdev.sportevent.ui.matchlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tngdev.sportevent.databinding.ItemMatchListBinding
import com.tngdev.sportevent.model.MatchItem

class MatchListAdapter: ListAdapter<MatchItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    var onItemClickListener: (movieItem: MatchItem) -> Unit? = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMatchListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchItemViewHolder(binding)
    }

//    override fun getItemViewType(position: Int): Int {
//        return when (getItem(position)) {
//            is MatchItem -> ITEM_VIEW_TYPE
//        }
//    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is MatchItemViewHolder -> {
                holder.bind(getItem(position))
            }
        }
    }


    inner class MatchItemViewHolder(val binding: ItemMatchListBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClickListener(getItem(bindingAdapterPosition))
            }
        }

        fun bind(item: MatchItem) {
            binding.also {
                it.matchItem = item
                it.executePendingBindings()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MatchItem>() {
            override fun areItemsTheSame(
                oldItem: MatchItem,
                newItem: MatchItem
            ): Boolean {
                return oldItem.description == newItem.description
            }

            override fun areContentsTheSame(
                oldItem: MatchItem,
                newItem: MatchItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}