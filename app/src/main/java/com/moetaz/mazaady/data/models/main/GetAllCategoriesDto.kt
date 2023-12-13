package com.moetaz.mazaady.data.models.main


import com.google.gson.annotations.SerializedName
import com.moetaz.mazaady.data.base.BaseResponse
import com.moetaz.mazaady.domain.models.Categories

data class GetAllCategoriesDto(
    @SerializedName("ads_banners")
    val adsBanners: List<AdsBanner>?,
    @SerializedName("categories")
    val categories: List<Category>?,
    @SerializedName("google_version")
    val googleVersion: String?,
    @SerializedName("huawei_version")
    val huaweiVersion: String?,
    @SerializedName("ios_latest_version")
    val iosLatestVersion: String?,
    @SerializedName("ios_version")
    val iosVersion: String?,
    @SerializedName("statistics")
    val statistics: Statistics?,
) {
    data class AdsBanner(
        @SerializedName("duration")
        val duration: Int?,
        @SerializedName("img")
        val img: String?,
        @SerializedName("media_type")
        val mediaType: String?,
    )

    data class Category(
        @SerializedName("children")
        val children: List<Children>?,
        @SerializedName("circle_icon")
        val circleIcon: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("disable_shipping")
        val disableShipping: Int?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("slug")
        val slug: String?,
    ) {
        data class Children(
            @SerializedName("children")
            val children: String?,
            @SerializedName("circle_icon")
            val circleIcon: String?,
            @SerializedName("description")
            val description: String?,
            @SerializedName("disable_shipping")
            val disableShipping: Int?,
            @SerializedName("id")
            val id: Int?,
            @SerializedName("image")
            val image: String?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("slug")
            val slug: String?,
        )
    }

    data class Statistics(
        @SerializedName("auctions")
        val auctions: Int?,
        @SerializedName("products")
        val products: Int?,
        @SerializedName("users")
        val users: Int?,
    )
}

class GetAllCategoriesResponseDto(
    @SerializedName("data")
    val data: GetAllCategoriesDto,
) : BaseResponse()

fun GetAllCategoriesResponseDto.mapTo() = data.categories?.map {
    Categories.Category(children = it.children?.map { it.mapTo() },
        circleIcon = it.circleIcon,
        description = it.description,
        disableShipping = it.disableShipping,
        id = it.id,
        image = it.image,
        name = it.name,
        slug = it.slug
    )
}

fun GetAllCategoriesDto.Category.Children.mapTo() =
    Categories.Category.Children(children = children,
        circleIcon = circleIcon,
        description = description,
        disableShipping = disableShipping,
        id = id,
        image = image,
        name = name,
        slug = slug)