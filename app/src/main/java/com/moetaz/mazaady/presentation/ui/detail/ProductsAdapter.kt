package com.moetaz.mazaady.presentation.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moetaz.mazaady.R
import com.moetaz.mazaady.databinding.ItemSimilarProductsBinding
import com.moetaz.mazaady.domain.models.Product

class ProductsAdapter : ListAdapter<Product, ProductsAdapter.ViewHolder>(DiffCallback()) {

    override fun getItemCount(): Int {
        return 4
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_similar_products,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    inner class ViewHolder(val binding: ItemSimilarProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    class DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

}