package com.moetaz.mazaady.domain.useCase

import com.moetaz.mazaady.domain.base.BaseUseCase
import com.moetaz.mazaady.domain.repository.MainRepository
import javax.inject.Inject

class GetPropertiesUseCase @Inject constructor(
    private val repo: MainRepository,
) : BaseUseCase() {

    @Throws(GetPropertiesExceptions::class)
    operator fun invoke(subCatId: Int) = startFlow({
        repo.getProperties(subCatId)
    }, validation = {
        if (subCatId < 0)
            throw GetPropertiesExceptions.SubCategoryIdException("Sub Category Id is smaller than 0")
    })

}

sealed class GetPropertiesExceptions(msg: String? = null) : Exception() {
    class SubCategoryIdException(msg: String? = null) : GetPropertiesExceptions(msg)
}