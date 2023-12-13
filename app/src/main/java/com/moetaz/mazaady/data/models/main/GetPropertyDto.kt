package com.moetaz.mazaady.data.models.main


import com.google.gson.annotations.SerializedName
import com.moetaz.mazaady.data.base.BaseResponse
import com.moetaz.mazaady.domain.models.Option
import com.moetaz.mazaady.domain.models.Property

data class PropertyDto(
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("list")
    val list: Boolean?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("options")
    val options: List<Option>?,
    @SerializedName("other_value")
    val otherValue: String?,
    @SerializedName("parent")
    val parent: String?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("value")
    val value: String?,
) {
    data class Option(
        @SerializedName("child")
        val child: Boolean?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("parent")
        val parent: Int?,
        @SerializedName("slug")
        val slug: String?,
    )
}

data class GetPropertiesResponseDto(
    @SerializedName("data")
    val data: List<PropertyDto>,
) : BaseResponse()

fun GetPropertiesResponseDto.mapTo() = data.map { it.mapTo() }

fun PropertyDto.mapTo() =
    Property(
        description = description,
        id = id,
        list = list,
        name = name,
        options = options?.map { it.mapTo() },
        otherValue = otherValue,
        parent = parent,
        slug = slug,
        type = type,
        value = value)

fun PropertyDto.Option.mapTo() = Option(
    child = child,
    id = id,
    name = name,
    parent = parent,
    slug = slug)