package com.january.assignment.global.response

import org.springframework.http.HttpStatus

data class SuccessResponse<T>(
    val code: Int,
    val message: String,
    val data: T?
) {

    companion object {
        fun <T> ok(data: T): SuccessResponse<T> {
            return SuccessResponse(HttpStatus.OK.value(), HttpStatus.OK.name, data)
        }

        fun <T> created(data: T): SuccessResponse<T> {
            return SuccessResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED.name, data)
        }

        fun <T> okWithNoData(): SuccessResponse<T> {
            return SuccessResponse(HttpStatus.OK.value(), HttpStatus.OK.name, null)
        }

        fun <T> createdWithNoData(): SuccessResponse<T> {
            return SuccessResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED.name, null)
        }
    }
}
