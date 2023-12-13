package com.moetaz.mazaady.data.base

import com.google.gson.annotations.SerializedName


open class BaseResponse(
    @SerializedName("msg")
    var msg: String? = null,
    @SerializedName("code")
    var code: Int? = null,
)


