package com.moetaz.mazaady.presentation.utils.base

import android.util.Log
import androidx.lifecycle.ViewModel
import com.moetaz.mazaady.R
import com.moetaz.mazaady.domain.base.FailureStatus
import com.moetaz.mazaady.domain.base.Resource
import com.moetaz.mazaady.presentation.utils.Error
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel : ViewModel() {

    protected val _loadingState by lazy { MutableStateFlow(false) }
    val loadingState: StateFlow<Boolean> by lazy { _loadingState }

    protected suspend fun <T> Resource<T>.checkResponse(
        uiEvent: MutableSharedFlow<UiEvent>,
        loadingState: MutableSharedFlow<Boolean> = _loadingState,
        success: suspend (T?) -> Unit
    ) {
        Log.d("moetaz", "checkResponse: $this")
        when (this) {
            is Resource.Failure -> {
                handleFailedResource(this, uiEvent)
            }
            is Resource.Success -> {
                success(this.value)
            }
            is Resource.Loading ->
                loadingState.emit(this.loadingStatus)
            else -> {}
        }
    }

    private suspend fun handleFailedResource(
        resource: Resource.Failure,
        uiEventFlowFailure: MutableSharedFlow<UiEvent>
    ) {
        val error: Error =
            when (resource.status) {
                FailureStatus.API_FAIL -> Error.ErrorStr(resource.message ?: "")
                FailureStatus.UNAUTHENTICATED -> Error.ErrorInt(R.string.user_not_logged_in_msg)
                FailureStatus.NO_INTERNET -> Error.ErrorInt(R.string.no_internet_connection)
                FailureStatus.OTHER -> Error.ErrorInt(R.string.try_again_something_went_wrong)
            }

        uiEventFlowFailure.emit(UiEvent.ShowSnackbar(error))
    }


}


open class UiEvent {
    class ShowSnackbar(val message: Error) : UiEvent()
}