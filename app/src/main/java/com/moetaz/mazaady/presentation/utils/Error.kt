package com.moetaz.mazaady.presentation.utils

import androidx.annotation.StringRes

sealed class Error {
    class ErrorStr(val msg: String) : Error()
    class ErrorInt(@StringRes val msg: Int) : Error()
}
