package com.moetaz.mazaady.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Property(
    val description: String?,
    val id: Int?,
    val list: Boolean?,
    val name: String?,
    val options: List<Option>?,
    val otherValue: String?,
    val parent: String?,
    val slug: String?,
    val type: String?,
    val value: String?,
): Parcelable
