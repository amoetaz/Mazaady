package com.moetaz.mazaady.data.repository

import com.moetaz.mazaady.data.base.BaseRepository
import com.moetaz.mazaady.data.models.main.mapTo
import com.moetaz.mazaady.data.remote.MainService
import com.moetaz.mazaady.domain.base.Resource
import com.moetaz.mazaady.domain.models.Categories
import com.moetaz.mazaady.domain.models.Property
import com.moetaz.mazaady.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val service: MainService) :
    BaseRepository(), MainRepository {


    override suspend fun getAllCategories(): Resource<List<Categories.Category>?> {
        return safeApiCall(apiCall = {
            service.getAllCategories()
        }, mapData = {
            it.mapTo()
        })
    }

    override suspend fun getProperties(subCatId: Int): Resource<List<Property>?> {
        return safeApiCall(
            apiCall = { service.getProperties(subCatId) }, mapData = {
                it.mapTo()
            })
    }

    override suspend fun getOptions(optionId: Int): Resource<List<Property>?> {
        return safeApiCall(
            apiCall = { service.getOptions(optionId) }, mapData = {
                it.mapTo()
            })
    }

}