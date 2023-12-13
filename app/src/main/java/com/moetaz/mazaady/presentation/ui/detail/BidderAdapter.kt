package com.moetaz.mazaady.presentation.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moetaz.mazaady.R
import com.moetaz.mazaady.databinding.ItemBiddersBinding
import com.moetaz.mazaady.domain.models.Bidder

class BidderAdapter : ListAdapter<Bidder, BidderAdapter.ViewHolder>(DiffCallback()) {

    override fun getItemCount(): Int {
        return 3
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_bidders,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    inner class ViewHolder(val binding: ItemBiddersBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    class DiffCallback : DiffUtil.ItemCallback<Bidder>() {
        override fun areItemsTheSame(oldItem: Bidder, newItem: Bidder): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Bidder, newItem: Bidder): Boolean {
            return oldItem == newItem
        }
    }

}