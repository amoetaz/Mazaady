package com.moetaz.mazaady.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Option(
    val child: Boolean?,
    val id: Int?,
    val name: String?,
    val parent: Int?,
    val slug: String?,
): Parcelable