package com.moetaz.mazaady.domain.useCase

import com.moetaz.mazaady.domain.base.BaseUseCase
import com.moetaz.mazaady.domain.repository.MainRepository
import javax.inject.Inject

class GetOptionsUseCase @Inject constructor(
    private val repo: MainRepository,
) : BaseUseCase() {


    operator fun invoke(optionId: Int) = startFlow({
        repo.getOptions(optionId)
    })

}