package com.moetaz.mazaady.domain.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

open class BaseUseCase {

    protected fun <T> startFlow(
        apiBlock: suspend () -> Resource<T>,
        validation: (() -> Unit)? = null,
    ) = flow {
        validation?.invoke()

        emit(Resource.Loading(true))
        val result = apiBlock()
        emit(Resource.Loading(false))

        emit(result)
    }.flowOn(Dispatchers.IO)


}