package com.moetaz.mazaady.data.base

import android.util.Log
import com.moetaz.mazaady.domain.base.FailureStatus
import com.moetaz.mazaady.domain.base.Resource
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

open class BaseRepository @Inject constructor() {

    protected suspend fun <T,N> safeApiCall(apiCall: suspend () -> T, mapData: (T)->N): Resource<N> {
        try {
            Log.d("moetaz", "safeApiCall: start")
            val apiResponse = apiCall.invoke()

            return when ((apiResponse as? BaseResponse)) {
                null -> {
                    Log.d("moetaz", "safeApiCall null: $apiResponse")
                    Resource.Failure(FailureStatus.API_FAIL)
                }
                else -> {
                    Log.d("moetaz", "safeApiCall: else")
                    val statusCode = apiResponse.code
                    if (statusCode in 200..299) {
                        Log.d("moetaz", "safeApiCall: 200 ${apiResponse}")
                        Resource.Success(mapData(apiResponse))
                    } else {
                        Log.d("moetaz", "safeApiCall: failure")
                        Resource.Failure(
                            FailureStatus.API_FAIL,
                            apiResponse.code,
                            apiResponse.msg
                        )
                    }

                }
            }
        } catch (throwable: Throwable) {
            Log.d("moetaz", "safeApiCall: start catch")
            when (throwable) {
                is HttpException -> {
                    return when (throwable.code()) {
                        401 -> {
                            Resource.Failure(FailureStatus.UNAUTHENTICATED, throwable.code(), null)
                        }
                        else -> {
                            Log.d("moetaz", "safeApiCall: HttpException")
                            val error = throwable.parseError<BaseResponse>()
                            if (error != null)
                                return Resource.Failure(
                                    FailureStatus.API_FAIL,
                                    throwable.code(),
                                    error.msg
                                )
                            return Resource.Failure(
                                FailureStatus.API_FAIL,
                                throwable.code(),
                                error?.msg
                            )

                        }
                    }

                }

                is UnknownHostException -> {
                    Log.d("moetaz", "safeApiCall: UnknownHostException")
                    return Resource.Failure(FailureStatus.NO_INTERNET)
                }

                is ConnectException -> {
                    Log.d("moetaz", "safeApiCall: ConnectException")

                    return Resource.Failure(FailureStatus.NO_INTERNET)
                }

                else -> {
                    Log.d("moetaz", "safeApiCall: else Exception ${throwable}")

                    return Resource.Failure(FailureStatus.OTHER)
                }
            }
        }
    }
}