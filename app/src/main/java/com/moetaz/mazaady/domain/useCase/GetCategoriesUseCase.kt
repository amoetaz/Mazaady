package com.moetaz.mazaady.domain.useCase

import com.moetaz.mazaady.domain.base.BaseUseCase
import com.moetaz.mazaady.domain.repository.MainRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetCategoriesUseCase @Inject constructor(
    private val repo: MainRepository,
) : BaseUseCase() {

    operator fun invoke() = startFlow({
        repo.getAllCategories()
    })

}