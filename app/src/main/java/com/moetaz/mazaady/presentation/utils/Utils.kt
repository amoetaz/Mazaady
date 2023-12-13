package com.moetaz.mazaady.presentation.utils

import android.view.View

fun handleLoading(isLoading: Boolean, pb: View?, vararg disabledViews: View) = if (isLoading)
    showProgress(pb, *disabledViews)
else
    hideProgress(pb, *disabledViews)

private fun showProgress(pb: View?, vararg enabledViews: View) {

    pb?.show()
    enabledViews.forEach {
        it.disable()
    }
}

private fun hideProgress(pb: View?, vararg disabledViews: View) {

    pb?.hide()
    disabledViews.forEach {
        it.enable()
    }
}