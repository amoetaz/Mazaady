package com.moetaz.mazaady.data.base

import com.google.gson.Gson
import retrofit2.HttpException

inline fun <reified T> HttpException.parseError(): T? {
    return try {
        Gson().getAdapter(T::class.java).fromJson(this.response()?.errorBody()?.string())
    } catch (e: java.lang.Exception) {
        null
    }
}