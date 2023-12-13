package com.moetaz.mazaady.presentation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.moetaz.mazaady.R
import com.moetaz.mazaady.databinding.FragmentMainBinding
import com.moetaz.mazaady.domain.models.Property
import com.moetaz.mazaady.presentation.ui.adapters.MazadRequestPropertiesAdapter
import com.moetaz.mazaady.presentation.ui.dialogs.SelectedValuesDialog
import com.moetaz.mazaady.presentation.ui.dialogs.bottomSearch.BottomSearchListDialog
import com.moetaz.mazaady.presentation.ui.dialogs.bottomSearch.SearchItem
import com.moetaz.mazaady.presentation.utils.base.BaseFragment
import com.moetaz.mazaady.presentation.utils.setUp
import com.moetaz.mazaady.presentation.utils.showSnackMsg
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var adapter: MazadRequestPropertiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.clicker = MainClicker()
        binding.viewModel = viewModel

        setUi()
        observeData()

        return binding.root
    }

    private fun setUi() {
        adapter = MazadRequestPropertiesAdapter(::onItemPropertyClick, ::onItemUserInputChangeListener)
        binding.rvProperty.setUp(adapter)
        (viewModel.propertiesFlow.value as? SuccessProperties)?.data?.let {
            adapter.submitList(it)
        }
    }

    private fun observeData() {
        onObserveCategories()
        onObserveProperties()
    }


    private fun onObserveCategories() {
        observingData(
            rootView = binding.root,
            flow = viewModel.categoriesFlow,
            loadingView = binding.loadingBar.pb,
            loadingState = viewModel.loadingState,
            binding.btnSubmit
        ) {}
    }

    private fun onObserveProperties() {
        observingData(
            rootView = binding.root,
            flow = viewModel.propertiesFlow,
            loadingView = binding.loadingBar.pb,
            loadingState = viewModel.loadingState,
            binding.btnSubmit
        ) {
            when (it) {
                is SuccessProperties -> {
                    adapter.submitList(it.data)
                }
            }
        }
    }

    private fun onItemPropertyClick(index: Int, item: Property) {
        val list = item.options?.map { SearchItem(it.id!!, it.name, it.slug) }

        showSearchDialog(
            hasOtherValue = true,
            title = item.name,
            list = ArrayList<SearchItem>(list!!.size).apply { addAll(list) }
        ) { searchItem ->
            if (searchItem.id == -1) { // other clicked
                adapter.showUserInput(index)
                adapter.setSelectedOption(index, getString(R.string.other))
            } else {
                adapter.hideUserInput(index)
                viewModel.setSelectedPropertyOption(item, searchItem.id, searchItem.name)
                adapter.setSelectedOption(index, searchItem.name ?: searchItem.nameSec ?: "")
            }
        }
    }

    /** this happens in case of the user select 'other' then he can write its own input value. */
    private fun onItemUserInputChangeListener(property: Property, userInputText: String?) {
        viewModel.setSelectedPropertyOption(property, -1, userInputText)
    }


    private fun showSearchDialog(
        hasOtherValue: Boolean = false,
        title: String?,
        list: ArrayList<SearchItem>,
        onItemSelected: (SearchItem) -> Unit,
    ) {
        BottomSearchListDialog.newInstance(
            hasOtherValue = hasOtherValue, title = title,
            list = list
        ).apply {
            this.onItemSelected = onItemSelected
        }.show(childFragmentManager, "")
    }

    inner class MainClicker {

        fun goToSecPage() {
            findNavController().navigate(R.id.action_mainFragment_to_mazadDetailsFragment)
        }

        fun submit() {
            SelectedValuesDialog.newInstance(viewModel.request)
                .show(childFragmentManager, "")
        }

        fun chooseCategory() {
            val catList = (viewModel.categoriesFlow.value as? SuccessCategoriesData)?.data?.map {
                SearchItem(
                    it.id!!,
                    it.name,
                    it.slug
                )
            }
            if (catList.isNullOrEmpty()) {
                binding.root.showSnackMsg(R.string.wait_for_categories)
                return
            }

            showSearchDialog(title = getString(R.string.main_category),
                list = ArrayList<SearchItem>(catList.size).apply { addAll(catList) })
            { searchItem ->
                viewModel.setSelectedCategory(searchItem.id)
                adapter.clearSelectedOptions()
                binding.invalidateAll()
            }
        }

        fun chooseSubCategory() {
            if (viewModel.request.category == null) {
                binding.root.showSnackMsg(R.string.no_category_was_selected)
                return
            }

            viewModel.getSubCategories(viewModel.request.category!!.id!!) {
                val list = it?.map { SearchItem(it.id!!, it.name, it.slug) }

                showSearchDialog(
                    title = getString(R.string.sub_category),
                    list = ArrayList<SearchItem>(list!!.size).apply { addAll(list) })
                { searchItem ->
                    viewModel.setSelectedSubCategory(searchItem.id)
                    binding.invalidateAll()
                    adapter.clearSelectedOptions()
                }
            }
        }

    }

}