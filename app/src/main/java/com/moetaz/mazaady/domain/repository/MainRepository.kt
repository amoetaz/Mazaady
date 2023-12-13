package com.moetaz.mazaady.domain.repository

import com.moetaz.mazaady.domain.base.Resource
import com.moetaz.mazaady.domain.models.Categories
import com.moetaz.mazaady.domain.models.Property

interface MainRepository {

    suspend fun getAllCategories(): Resource<List<Categories.Category>?>

    suspend fun getProperties(subCatId: Int): Resource<List<Property>?>

    suspend fun getOptions(optionId: Int): Resource<List<Property>?>


}