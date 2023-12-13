package com.moetaz.mazaady.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class Categories(
    val adsBanners: List<AdsBanner>?,
    val categories: List<Category>?,
    val googleVersion: String?,
    val huaweiVersion: String?,
    val iosLatestVersion: String?,
    val iosVersion: String?,
    val statistics: Statistics?
) {
    data class AdsBanner(
        val duration: Int?,
        val img: String?,
        val mediaType: String?
    )

    @Parcelize
    data class Category(
        val children: List<Children>?,
        val circleIcon: String?,
        val description: String?,
        val disableShipping: Int?,
        val id: Int?,
        val image: String?,
        val name: String?,
        val slug: String?
    ): Parcelable {
        @Parcelize
        data class Children(
            val children: String?,
            val circleIcon: String?,
            val description: String?,
            val disableShipping: Int?,
            val id: Int?,
            val image: String?,
            val name: String?,
            val slug: String?
        ): Parcelable
    }

    data class Statistics(
        val auctions: Int?,
        val products: Int?,
        val users: Int?
    )
}
