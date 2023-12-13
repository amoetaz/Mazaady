package com.moetaz.mazaady.presentation.ui.main

import androidx.lifecycle.viewModelScope
import com.moetaz.mazaady.domain.models.Categories
import com.moetaz.mazaady.domain.models.Property
import com.moetaz.mazaady.domain.models.requests.MainRequest
import com.moetaz.mazaady.domain.useCase.GetCategoriesUseCase
import com.moetaz.mazaady.domain.useCase.GetOptionsUseCase
import com.moetaz.mazaady.domain.useCase.GetPropertiesUseCase
import com.moetaz.mazaady.presentation.utils.base.BaseViewModel
import com.moetaz.mazaady.presentation.utils.base.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getPropertiesUseCase: GetPropertiesUseCase,
    private val getOptionsUseCase: GetOptionsUseCase,
) : BaseViewModel() {

    private val _categoriesFlow = MutableStateFlow(UiEvent())
    val categoriesFlow = _categoriesFlow.asStateFlow()

    private val _propertiesFlow = MutableStateFlow(UiEvent())
    val propertiesFlow = _propertiesFlow.asStateFlow()

    private var propertiesJob: Job? = null
    private var optionsJob: Job? = null
    var request = MainRequest()

    init {
        getCategories()
    }

    private fun getCategories() {
        getCategoriesUseCase().onEach {
            it.checkResponse(_categoriesFlow) {
                _categoriesFlow.emit(SuccessCategoriesData(it))
            }
        }.launchIn(viewModelScope)
    }

    private fun getProperties(subCatId: Int) {
        propertiesJob?.cancel()
        propertiesJob = getPropertiesUseCase(subCatId).onEach {
            it.checkResponse(_propertiesFlow) {
                _propertiesFlow.emit(SuccessProperties(it))
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Get Properties For Specific selected Option.
     * */
    private fun getPropertiesByOptions(propertyId: Int?, optionId: Int) {
        optionsJob?.cancel()
        optionsJob = getOptionsUseCase(optionId).onEach {
            it.checkResponse(_propertiesFlow) {
                if (it.isNullOrEmpty()) return@checkResponse

                val oldList = (_propertiesFlow.value as? SuccessProperties)?.data
                val indexOfParentProperty =
                    oldList?.indexOfFirst { it.id == propertyId } ?: oldList?.size

                _propertiesFlow.emit(
                    SuccessProperties(oldList?.toMutableList()
                        ?.apply { addAll(indexOfParentProperty?.plus(1) ?: 0, it) })
                )
            }
        }.launchIn(viewModelScope)
    }


    fun getSubCategories(catId: Int, success: (list: List<Categories.Category.Children>?) -> Unit) {
        viewModelScope.launch(IO) {
            success((_categoriesFlow.value as? SuccessCategoriesData)?.data?.find { it.id == catId }?.children)
        }
    }

    fun setSelectedCategory(catId: Int) {
        viewModelScope.launch(IO) {
            request.category =
                (_categoriesFlow.value as? SuccessCategoriesData)?.data?.find { it.id == catId }

            request.subCategory = null
            clearProperties()
        }
    }

    fun setSelectedSubCategory(subCatId: Int) {
        clearProperties()
        getProperties(subCatId)
        viewModelScope.launch(IO) {
            request.subCategory =
                (_categoriesFlow.value as? SuccessCategoriesData)?.data
                    ?.find { it.id == request.category?.id }
                    ?.children?.find { it.id == subCatId }
        }
    }

    fun setSelectedPropertyOption(
        property: Property,
        selectedOptionId: Int,
        optionName: String? = null,
    ) {
        request.properties[property.id ?: 0] = MainRequest.PropertyOptionItem(
            property,
            selectedOptionId,
            optionName
        )

        if (selectedOptionId != -1)
            getPropertiesByOptions(property.id, selectedOptionId)
    }

    private fun clearProperties() {
        viewModelScope.launch {
            request.properties.clear()
            _propertiesFlow.emit(SuccessProperties(null))
        }
    }

}

data class SuccessCategoriesData(val data: List<Categories.Category>?) : UiEvent()

data class SuccessProperties(val data: List<Property>?) : UiEvent()
