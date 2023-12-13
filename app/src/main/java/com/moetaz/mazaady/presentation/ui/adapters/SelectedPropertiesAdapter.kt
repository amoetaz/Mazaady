package com.moetaz.mazaady.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moetaz.mazaady.R
import com.moetaz.mazaady.databinding.ItemSelectedValuesPropertyBinding
import com.moetaz.mazaady.domain.models.requests.MainRequest.PropertyOptionItem

class SelectedPropertiesAdapter(
) : ListAdapter<PropertyOptionItem, SelectedPropertiesAdapter.ViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_selected_values_property,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: ItemSelectedValuesPropertyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PropertyOptionItem) = with(binding) {
            binding.item = item
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PropertyOptionItem>() {
        override fun areItemsTheSame(oldItem: PropertyOptionItem, newItem: PropertyOptionItem): Boolean {
            return oldItem.property.id == newItem.property.id
        }

        override fun areContentsTheSame(oldItem: PropertyOptionItem, newItem: PropertyOptionItem): Boolean {
            return oldItem == newItem
        }
    }
}
