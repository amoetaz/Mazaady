package com.moetaz.mazaady.domain.base

sealed class Resource<out T> {

    class Success<out T>(val value: T?, msg: String? = null) : Resource<T>()

    class Failure(
        val status: FailureStatus,
        val code: Int? = null,
        val message: String? = null
    ) : Resource<Nothing>()

    data class Loading(val loadingStatus: Boolean) : Resource<Nothing>()


    fun isLoading() = this is Loading

    fun isError() = this is Failure

    fun retrieveError(): Failure? = if (this is Failure) this else null

    fun toData(): T? = if (this is Success) this.value else null

    fun retrieveFailureStatus(): FailureStatus? = if (this is Failure) this.status else null
}