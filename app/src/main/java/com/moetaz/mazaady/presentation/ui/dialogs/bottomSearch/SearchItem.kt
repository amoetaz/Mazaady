package com.moetaz.mazaady.presentation.ui.dialogs.bottomSearch

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchItem(
    /** this might be '-1' in case of the user write it's own.*/
    val id: Int,
    val name: String?,
    val nameSec: String?,
): Parcelable
