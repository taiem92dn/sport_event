package com.tngdev.sportevent.ui.teams.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tngdev.sportevent.databinding.ItemMatchListBinding
import com.tngdev.sportevent.databinding.ItemTeamListBinding
import com.tngdev.sportevent.model.TeamItem

class TeamListAdapter : ListAdapter<TeamItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    var onItemClickListener: (movieItem: TeamItem) -> Unit? = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemTeamListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is TeamItemViewHolder -> {
                holder.bind(getItem(position))
            }
        }
    }


    inner class TeamItemViewHolder(val binding: ItemTeamListBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClickListener(getItem(bindingAdapterPosition))
            }
        }

        fun bind(item: TeamItem) {
            binding.also {
                it.teamItem = item
                it.executePendingBindings()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TeamItem>() {
            override fun areItemsTheSame(
                oldItem: TeamItem,
                newItem: TeamItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TeamItem,
                newItem: TeamItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}