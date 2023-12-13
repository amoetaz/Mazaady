package com.moetaz.mazaady.domain.models.requests

import android.os.Parcelable
import com.moetaz.mazaady.domain.models.Categories
import com.moetaz.mazaady.domain.models.Property
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainRequest(
    var category: Categories.Category? = null,
    var subCategory: Categories.Category.Children? = null,
    /** (KEY,VAlUE) = (Property_id, item) */
    var properties: HashMap<Int, PropertyOptionItem> = hashMapOf(),
): Parcelable {
    @Parcelize
    data class PropertyOptionItem(
        val property: Property,
        /** selected option id and in case of null then the user write it's own option. */
        val optionId: Int? = null,
        /** selected option name in case of the user write it's own option else null. */
        val optionName: String? = null,
    ): Parcelable

}
