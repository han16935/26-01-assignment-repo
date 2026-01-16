package com.january.assignment.global.response

import org.springframework.http.HttpStatus

data class ErrorResponse (
    val code : Int,
    val message : String?
){
    companion object {
        fun badRequest(message: String?): ErrorResponse {
            return ErrorResponse(HttpStatus.BAD_REQUEST.value(), message)
        }

        fun unAuthorized(message: String?): ErrorResponse {
            return ErrorResponse(HttpStatus.UNAUTHORIZED.value(), message)
        }

        fun forbidden(message: String?): ErrorResponse {
            return ErrorResponse(HttpStatus.FORBIDDEN.value(), message)
        }

        fun internalServerError(message: String?): ErrorResponse {
            return ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), message)
        }
    }
}
