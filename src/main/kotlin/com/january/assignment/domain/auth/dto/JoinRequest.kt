package com.january.assignment.domain.auth.dto

data class JoinRequest(
    val email : String,
    val password : String,
    val name : String
)
