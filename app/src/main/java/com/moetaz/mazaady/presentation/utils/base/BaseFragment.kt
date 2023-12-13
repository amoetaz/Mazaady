package com.moetaz.mazaady.presentation.utils.base

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.moetaz.mazaady.presentation.utils.handleLoading
import com.moetaz.mazaady.presentation.utils.showSnackMsg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseFragment : Fragment() {


    protected fun observingData(
        rootView: View,
        flow: SharedFlow<UiEvent>,
        loadingView: View,
        loadingState: StateFlow<Boolean>  ,
        vararg disabledViews: View = emptyArray(),
        onSuccess: (UiEvent) -> Unit,
    ) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED)  {
                observeUiEvent(flow, rootView, onSuccess)
            }
        }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                observeLoading(loadingState, loadingView, *disabledViews)
            }
        }
    }

    private fun CoroutineScope.observeLoading(
        loadingState: StateFlow<Boolean>,
        loadingView: View,
        vararg disabledViews: View = emptyArray(),
    ) {
        launch {
            loadingState.collectLatest {
                Log.d("moetaz", "observeLoading: isLoading= $it")
                handleLoading(
                    it,
                    loadingView,
                    *disabledViews
                )
            }
        }
    }

    private fun CoroutineScope.observeUiEvent(
        flow: SharedFlow<UiEvent>,
        rootView: View,
        onSuccess: (UiEvent) -> Unit,
    ) {
        launch {
            flow.collectLatest {
                Log.d("moetaz", "observeUiEvent: $it")
                when (it) {
                    is UiEvent.ShowSnackbar -> {
                        it.message.showSnackMsg(
                            rootView
                        )
                    }
                    else -> onSuccess(it)
                }
            }
        }
    }


}