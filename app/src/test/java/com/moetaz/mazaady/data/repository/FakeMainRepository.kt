package com.moetaz.mazaady.data.repository

import com.moetaz.mazaady.domain.base.Resource
import com.moetaz.mazaady.domain.models.Categories
import com.moetaz.mazaady.domain.models.Property
import com.moetaz.mazaady.domain.repository.MainRepository

class FakeMainRepository : MainRepository {

    val catList = mutableListOf<Categories.Category>()
    val propertiesList = mutableListOf<Property>()
    val optionsList = mutableListOf<Property>()

    override suspend fun getAllCategories(): Resource<List<Categories.Category>?> {
        return Resource.Success(catList)
    }

    override suspend fun getProperties(subCatId: Int): Resource<List<Property>?> {
        return Resource.Success(propertiesList)
    }

    override suspend fun getOptions(optionId: Int): Resource<List<Property>?> {
        return Resource.Success(optionsList)
    }

}