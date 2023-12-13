package com.moetaz.mazaady.data.remote

import com.moetaz.mazaady.data.models.main.GetAllCategoriesResponseDto
import com.moetaz.mazaady.data.models.main.GetPropertiesResponseDto

class FakeMainService : MainService {

    lateinit var catRes: GetAllCategoriesResponseDto
    lateinit var propertiesRes: GetPropertiesResponseDto
    lateinit var optionsRes: GetPropertiesResponseDto


    override suspend fun getAllCategories(): GetAllCategoriesResponseDto {
        return catRes
    }

    override suspend fun getProperties(subCatId: Int): GetPropertiesResponseDto {
        return propertiesRes
    }

    override suspend fun getOptions(optionId: Int): GetPropertiesResponseDto {
        return optionsRes
    }

}