package com.interview.whoptest.domain.model

data class NetworkError(
    val error: ApiError,
    val t: Throwable? = null
)

enum class ApiError(val message: String) {
    NetworkError("Network error"), UnknownResponse("Unknown response"), UnknownError("Unknown Error")
}