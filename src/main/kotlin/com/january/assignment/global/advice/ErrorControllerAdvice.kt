package com.january.assignment.global.advice

import com.january.assignment.global.response.ErrorResponse
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorControllerAdvice {

    @ExceptionHandler(BindException::class)
    fun handleBindException(ex : BindException) : ResponseEntity<ErrorResponse> {
        val errorMessage = ex.getAllErrors().get(0).getDefaultMessage()
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(errorMessage))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex : IllegalArgumentException) : ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(ex.message))
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        ex.printStackTrace()
        return ResponseEntity.internalServerError().body(ErrorResponse.internalServerError(ex.message))
    }
}
