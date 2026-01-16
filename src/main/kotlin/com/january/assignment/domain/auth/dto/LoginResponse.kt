package com.january.assignment.domain.auth.dto

data class LoginResponse(
    val userId : Long,
    val accessToken : String,
    val refreshTokenValue : String
)
