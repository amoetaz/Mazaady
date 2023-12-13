package com.moetaz.mazaady.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moetaz.mazaady.R
import com.moetaz.mazaady.databinding.ItemPropertyBinding
import com.moetaz.mazaady.domain.models.Property


class MazadRequestPropertiesAdapter(
    val onItemClick: (index: Int, Property) -> Unit,
    val onItemUserInputChangeListener: (Property, userInputText: String?) -> Unit

) : ListAdapter<Property, MazadRequestPropertiesAdapter.ViewHolder>(DiffCallback()) {

    /** This map contains (KEY,VALUE) = (Index , SelectedOptionName) */
    private val selectedOptionNameMap = HashMap<Int, String>()

    /** This map contains (KEY,VALUE) = (Index , is_user_Input_shown ) */
    private val userInputShownSet = HashSet<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_property,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setSelectedOption(index: Int, optionName: String) {
        selectedOptionNameMap[index] = optionName
        notifyItemChanged(index)
    }

    fun clearSelectedOptions() {
        selectedOptionNameMap.clear()
        userInputShownSet.clear()
    }

    fun showUserInput(index: Int) {
        userInputShownSet.add(index)
        notifyItemChanged(index)
    }

    fun hideUserInput(index: Int) {
        userInputShownSet.remove(index)
        notifyItemChanged(index)
    }


    inner class ViewHolder(val binding: ItemPropertyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.etlProp.editText?.setOnClickListener {
                val item = getItem(adapterPosition)
                onItemClick(adapterPosition, item)
            }
            binding.etlInputValue.editText?.addTextChangedListener {
                val item = getItem(adapterPosition)
                onItemUserInputChangeListener(item, it?.toString())
            }
        }

        fun bind(item: Property) = with(binding) {
            binding.item = item
            binding.selectedOption = selectedOptionNameMap[adapterPosition]
            binding.isUserInputShown = userInputShownSet.contains(adapterPosition)
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<Property>() {
        override fun areItemsTheSame(oldItem: Property, newItem: Property): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Property, newItem: Property): Boolean {
            return oldItem == newItem
        }
    }
}