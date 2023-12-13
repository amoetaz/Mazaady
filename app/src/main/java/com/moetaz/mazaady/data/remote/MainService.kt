package com.moetaz.mazaady.data.remote

import com.moetaz.mazaady.data.models.main.GetAllCategoriesResponseDto
import com.moetaz.mazaady.data.models.main.GetPropertiesResponseDto
import retrofit2.http.*

interface MainService {

    @GET("get_all_cats")
    suspend fun getAllCategories(): GetAllCategoriesResponseDto

    @GET("properties")
    suspend fun getProperties(@Query("cat") subCatId: Int): GetPropertiesResponseDto

    @GET("get-options-child/{optionId}")
    suspend fun getOptions(@Path("optionId") optionId: Int): GetPropertiesResponseDto

}